package com.example.networkingassignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NetworkingAssignment extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDecimalFormater=new DecimalFormat("##.##");
		//Request the progress bar to be shown in the title
		requestWindowFeature(Window.FEATURE_PROGRESS);
		logFile = new StringBuffer();
		setContentView(R.layout.main);
		bindListeners();
	}

	/**
	 * Setup event handlers and bind variables to values from xml
	 */
	private void bindListeners() {
		mBtnStart = (Button) findViewById(R.id.btnStart);
		mTxtSpeed = (TextView) findViewById(R.id.speed);
		mTxtConnectionSpeed = (TextView) findViewById(R.id.connectionspeeed);
		mTxtProgress = (TextView) findViewById(R.id.progress);
		mTxtNetwork = (TextView) findViewById(R.id.networktype);
		locationInfo = (EditText) findViewById(R.id.location_info);
		networkInfo = (EditText) findViewById(R.id.network_info);
		

		mBtnStart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(final View view) {
				if (locationInfo.getText().toString().length() > 0 && networkInfo.getText().toString().length() > 0){
					String loc = locationInfo.getText().toString();
					String net = networkInfo.getText().toString();
					logFile.append("Location: " + loc + "\n");
					logFile.append("Network: " + net + "\n");
				
					setProgressBarVisibility(true);
					mTxtSpeed.setText("Test started");	
					mBtnStart.setEnabled(false);
					mTxtNetwork.setText(R.string.network_detecting);
					new Thread(mWorker).start();
				}
				else{
					Toast.makeText(view.getContext(), "Please fill in Location and Network Info", Toast.LENGTH_LONG).show();
				}
			}
		});
	}



	private final Handler mHandler=new Handler(){
		@Override
		public void handleMessage(final Message msg) {
			switch(msg.what){
			case MSG_UPDATE_STATUS:				
				final SpeedInfo info1=(SpeedInfo) msg.obj;
				mTxtSpeed.setText(String.format(getResources().getString(R.string.update_speed), mDecimalFormater.format(info1.downspeed)));
				// Title progress is in range 0..10000
				logFile.append("Through put: " + info1.downspeed + " BytePerSec" +"\n");
				setProgress(100 * msg.arg1);
				mTxtProgress.setText(String.format(getResources().getString(R.string.update_downloaded), msg.arg2, EXPECTED_SIZE_IN_BYTES));
				break;
			case MSG_UPDATE_CONNECTION_TIME:
				logFile.append("Latency: " + msg.arg1 + " millisec"+ "\n");
				mTxtConnectionSpeed.setText(String.format(getResources().getString(R.string.update_connectionspeed), msg.arg1));
				break;				
			case MSG_COMPLETE_STATUS:
				final  SpeedInfo info2=(SpeedInfo) msg.obj;
				logFile.append("Average Throughput: " + EXPECTED_SIZE_IN_BYTES/(info2.downTime/1000.0) + " BytePerSec" +"\n");
				mTxtSpeed.setText(String.format(getResources().getString(R.string.update_downloaded_complete), msg.arg1, info2.downspeed));
				
				mTxtProgress.setText(String.format(getResources().getString(R.string.update_downloaded), msg.arg1, EXPECTED_SIZE_IN_BYTES));
				
				if(networkType(info2.kilobits)==1){
					mTxtNetwork.setText(R.string.network_3g);
				}else{
					mTxtNetwork.setText(R.string.network_edge);
				}
				
				mBtnStart.setEnabled(true);
				setProgressBarVisibility(false);
				appendFile(logFile.toString());
				break;	
			default:
				super.handleMessage(msg);		
			}
		}
	};

	/**
	 * Our Slave worker that does actually all the work
	 */
	private final Runnable mWorker=new Runnable(){
		
		@Override
		public void run() {
			InputStream stream=null;
			try {
				int bytesIn=0;
				String downloadFileUrl="http://web.mit.edu/21w.789/www/papers/griswold2004.pdf";	
				long startCon=System.currentTimeMillis(); 
				URL url=new URL(downloadFileUrl);
				URLConnection con=url.openConnection();
				con.setUseCaches(false);
				long connectionLatency=System.currentTimeMillis()- startCon;
				stream=con.getInputStream();

				Message msgUpdateConnection=Message.obtain(mHandler, MSG_UPDATE_CONNECTION_TIME);
				msgUpdateConnection.arg1=(int) connectionLatency;
				mHandler.sendMessage(msgUpdateConnection);

				long start=System.currentTimeMillis();
				int currentByte=0;
				long updateStart=System.currentTimeMillis();
				long updateDelta=0;
				int  bytesInThreshold=0;

				while((currentByte=stream.read())!=-1){	
					bytesIn++;
					bytesInThreshold++;
					if(updateDelta>=UPDATE_THRESHOLD){
						int progress=(int)((bytesIn/(double)EXPECTED_SIZE_IN_BYTES)*100);
						Message msg=Message.obtain(mHandler, MSG_UPDATE_STATUS, calculate(updateDelta, bytesInThreshold));
						msg.arg1=progress;
						msg.arg2=bytesIn;
						mHandler.sendMessage(msg);
						//Reset
						updateStart=System.currentTimeMillis();
						bytesInThreshold=0;
					}
					updateDelta = System.currentTimeMillis()- updateStart;
				}

				long downloadTime=(System.currentTimeMillis()-start);
				//Prevent AritchmeticException
				if(downloadTime==0){
					downloadTime=1;
				}

				Message msg=Message.obtain(mHandler, MSG_COMPLETE_STATUS, calculate(downloadTime, bytesIn));
				msg.arg1=bytesIn;
				mHandler.sendMessage(msg);
			} 
			catch (MalformedURLException e) {
				Log.e(TAG, e.getMessage());
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}finally{
				try {
					if(stream!=null){
						stream.close();
					}
				} catch (IOException e) {
					//Suppressed
				}
			}

		}
	};

	/**
	 * Get Network type from download rate
	 * @return 0 for Edge and 1 for 3G
	 */
	private int networkType(final double kbps){
		int type=1;//3G
		//Check if its EDGE
		if(kbps<EDGE_THRESHOLD){
			type=0;
		}
		return type;
	}

	/**
	 * 	
	 * 1 byte = 0.0078125 kilobits
	 * 1 kilobits = 0.0009765625 megabit
	 * 
	 * @param downloadTime in miliseconds
	 * @param bytesIn number of bytes downloaded
	 * @return SpeedInfo containing current speed
	 */
	private SpeedInfo calculate(final long downloadTime, final long bytesIn){
		SpeedInfo info=new SpeedInfo();
		//from mil to sec
		long bytespersecond   = (bytesIn / downloadTime) * 1000;
		double kilobits=bytespersecond * BYTE_TO_KILOBIT;
		double megabits=kilobits  * KILOBIT_TO_MEGABIT;
		info.downspeed=bytespersecond;
		info.kilobits=kilobits;
		info.megabits=megabits;
		info.downTime=downloadTime;
		return info;
	}

	/**
	 * Transfer Object
	 * @author devil
	 *
	 */
	private static class SpeedInfo{
		public double kilobits=0;
		public double megabits=0;
		public double downspeed=0;		
		public double downTime=0;
	}
	
	private void appendFile(String file) {
		File log = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				"Network" + ".txt");
		OutputStream os;
		try {
			os = new FileOutputStream(log);
			os.write(file.getBytes());
			os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sendFile(log);
	}
	
	private void sendFile(File file) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { "nhvtgd@gmail.com" });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Test Subject");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
				"go on read the emails");
		Log.v(getClass().getSimpleName(),
				"sPhotoUri=" + Uri.parse("file:/" + file.getAbsolutePath()));
		emailIntent.putExtra(Intent.EXTRA_STREAM,
				Uri.fromFile(file));
		startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}


	//Private fields	
	private static final String TAG = NetworkingAssignment.class.getSimpleName();
	private static final int EXPECTED_SIZE_IN_BYTES = 650924;//1MB 1024*1024

	private static final double EDGE_THRESHOLD = 176.0;
	private static final double BYTE_TO_KILOBIT = 0.0078125;
	private static final double KILOBIT_TO_MEGABIT = 0.0009765625;

	private Button mBtnStart;
	private TextView mTxtSpeed;
	private TextView mTxtConnectionSpeed;
	private TextView mTxtProgress;
	private TextView mTxtNetwork;
	private EditText locationInfo;
	private EditText networkInfo;

	private final int MSG_UPDATE_STATUS=0;
	private final int MSG_UPDATE_CONNECTION_TIME=1;
	private final int MSG_COMPLETE_STATUS=2;

	private final static int UPDATE_THRESHOLD=1000;


	private DecimalFormat mDecimalFormater;
	
	private StringBuffer logFile;
	
	
	
	
	

}

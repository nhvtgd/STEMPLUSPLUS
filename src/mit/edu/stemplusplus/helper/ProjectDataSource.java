package mit.edu.stemplusplus.helper;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProjectDataSource {

	  /*// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_DESCRIPTION, MySQLiteHelper.COLUMN_STEPS };

	  public ProjectDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Project createProject(Project project) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_NAME, project.getName());
	    values.put(MySQLiteHelper.COLUMN_DESCRIPTION, project.getDescription());
	    
	    
	    try {
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = null;
	        oos = new ObjectOutputStream(bos);

	        // mArrayList is the ArrayList you want to store
	        oos.writeObject(project.getSteps());
	        byte[] buff = bos.toByteArray();
	       
	        // You can store the blob in the database
	        
	    } catch (Exception e){
	    	
	    }
	    
	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_PROJECT, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PROJECT,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Project newProject = cursorToProject(cursor);
	    cursor.close();
	    return newProject;
	  }

	  public void deleteProject(Project project) {
	    long id = project.getId();
	    System.out.println("Project deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_PROJECT, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Project> getAllProjects() {
	    List<Project> projects = new ArrayList<Project>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PROJECT,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Project project = cursorToProject(cursor);
	      projects.add(project);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return projects;
	  }

	  private Project cursorToProject(Cursor cursor) {
	    Project project = new Project();
	    project.setId(cursor.getLong(0));
	    project.setName(cursor.getString(1));
	    project.setDescription(cursor.getString(2));
	    
	    return project;
	  }*/
	} 

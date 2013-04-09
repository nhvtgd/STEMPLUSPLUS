package mit.edu.stemplusplus.helper;

import mit.edu.stemplusplus.StemPlusPlus;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class NavigateActionBar extends StemPlusPlus{

    public boolean navigate(MenuItem item, Context context, boolean isLoggedIn) {
        
        switch (item.getItemId()) {
            
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask((Activity)context);
                break;
            
            case mit.edu.stemplusplus.R.id.menu_screen:
                startActivity(new Intent(context, mit.edu.stemplusplus.MenuScreen.class));
                break;
            
            case mit.edu.stemplusplus.R.id.user_profile:
                if (isLoggedIn) {
                    startActivity(new Intent(context, mit.edu.stemplusplus.UserProfileActivity.class));
                }
                else {
                    AlertDialogManager.showAlertDialog(context, "Oops!", "You must log in first!", false);
                    startActivity(new Intent(context, mit.edu.stemplusplus.LoginActivity.class));
                }
                break;
                
//            case mit.edu.stemplusplus.R.id.hot_projects:
//                startActivity(new Intent(context, mit.edu.stemplusplus.HotProjects.class));
//                break;
            
            case mit.edu.stemplusplus.R.id.post_project:
                if (isLoggedIn) {
                    startActivity(new Intent(context, mit.edu.stemplusplus.ProjectActivity.class));
                }
                else {
                    AlertDialogManager.showAlertDialog(context, "Oops!", "You must log in first!", false);
                    startActivity(new Intent(context, mit.edu.stemplusplus.LoginActivity.class));
                }
                break;  
            
            default:
                return super.onOptionsItemSelected(item);
        }      
        return true;
    }
}

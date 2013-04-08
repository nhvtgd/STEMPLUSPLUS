package mit.edu.stemplusplus;

import java.util.Date;
import java.util.List;

import mit.edu.stemplusplus.helper.Comment;
import mit.edu.stemplusplus.helper.Ranking;
import mit.edu.stemplusplus.helper.Step;
import mit.edu.stemplusplus.helper.User;
import android.os.Parcel;
import android.os.Parcelable;
// an example for implementing Parcelable:
public class ProjectParcel implements Parcelable {
    private String name;
    private Ranking rank;
    private User user;
    private List<Step> steps;
    private List<Comment> comments;
    private String description;
    private Date date;
    // should this hold the original date or the date of the most recent change?
    private String category;
  

        /* everything below here is for implementing Parcelable */

        // 99.9% of the time you can just ignore this
        public int describeContents() {
            return 0;
        }

        // write your object's data to the passed-in Parcel
        public void writeToParcel(Parcel out, int flags) {
           ///TODO
        }

        // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
        public static final Parcelable.Creator<ProjectParcel> CREATOR = new Parcelable.Creator<ProjectParcel>() {
            public ProjectParcel createFromParcel(Parcel in) {
                return new ProjectParcel(in);
            }

            public ProjectParcel[] newArray(int size) {
                return new ProjectParcel[size];
            }
        };

        // example constructor that takes a Parcel and gives you an object populated with it's values
        private ProjectParcel(Parcel in) {
            //TODO
        }
    }
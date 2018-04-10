import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jason on 4/9/2018.
 */

public class Roome extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}

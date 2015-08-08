package rocks.gdgmaceio.firedroid;

import com.firebase.client.Firebase;

/**
 * Created by bruno on 09/07/15.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

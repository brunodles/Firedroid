package rocks.gdgmaceio.firedroid.libs;

import com.firebase.client.Firebase;

import rocks.gdgmaceio.firedroid.BuildConfig;
import rocks.gdgmaceio.firedroid.model.Message;

/**
 * Created by bruno on 09/07/15.
 */
public final class FirebaseHelper {

    public FirebaseHelper() {
    }

    public Firebase get() {
        return new Firebase(BuildConfig.FIREBASE_URL);
    }

    public Firebase messages() {
        return get().child("messages");
    }

    public void addMessage(String message) {
        messages().push().setValue(new Message(message, uid()));
    }

    public String uid() {
        return get().getAuth().getUid();
    }
}

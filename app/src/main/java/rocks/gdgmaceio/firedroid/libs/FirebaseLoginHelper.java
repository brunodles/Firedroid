package rocks.gdgmaceio.firedroid.libs;

import android.text.TextUtils;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.security.InvalidParameterException;

/**
 * Created by bruno on 07/08/15.
 */
public class FirebaseLoginHelper {

    private String email;
    private String password;
    private Firebase firebase;
    private Listener listener;

    private FirebaseLoginHelper() {
    }

    public void login() {
        if (isValid())
            firebase.authWithPassword(email, password, new AuthResultHandler(listener));
    }

    public void signUp() {
        if (isValid())
            firebase.createUser(email, password, new SignUpResultHandler(listener));
    }

    private boolean isValid() {
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password))
            return false;

        if (TextUtils.isEmpty(email) || !isEmailValid(email))
            return false;

        return true;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private static class AuthResultHandler implements Firebase.AuthResultHandler {

        private final Listener listener;

        public AuthResultHandler(Listener listener) {
            this.listener = listener;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            listener.onSuccess();
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            listener.onError();
        }
    }

    private static class SignUpResultHandler implements Firebase.ResultHandler {

        private final Listener listener;

        public SignUpResultHandler(Listener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess() {
            listener.onSuccess();
        }

        @Override
        public void onError(FirebaseError firebaseError) {
            listener.onError();
        }
    }

    public interface Listener {

        void onSuccess();

        void onError();
    }

    public static class Builder {

        FirebaseLoginHelper loginHelper;

        public Builder() {
            loginHelper = new FirebaseLoginHelper();
        }

        public Builder firebase(Firebase firebase){
            loginHelper.firebase = firebase;
            return this;
        }

        public Builder listener(Listener listener){
            loginHelper.listener = listener;
            return this;
        }

        public Builder with(String email, String password){
            loginHelper.email = email;
            loginHelper.password = password;
            return this;
        }

        public FirebaseLoginHelper build(){
            if (!isValid())
                throw new IllegalArgumentException();
            return loginHelper;
        }

        private boolean isValid(){
            if (TextUtils.isEmpty(loginHelper.email))
                return false;
            if (TextUtils.isEmpty(loginHelper.password))
                return false;
            if (loginHelper.listener == null)
                return false;
            if (loginHelper.firebase == null)
                return false;
            return true;
        }
    }

}
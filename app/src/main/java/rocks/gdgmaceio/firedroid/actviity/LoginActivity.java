package rocks.gdgmaceio.firedroid.actviity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rocks.gdgmaceio.firedroid.R;
import rocks.gdgmaceio.firedroid.libs.FirebaseHelper;
import rocks.gdgmaceio.firedroid.libs.FirebaseLoginHelper;

public class LoginActivity extends Activity {

    public static final String PREF_KEY_USER_EMAIL = "email";
    public static final String PREF_KEY_USER_PASSWORD = "password";

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mSnackbarRoot;

    private FirebaseHelper firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebase = new FirebaseHelper();

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        mSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        mSnackbarRoot = findViewById(R.id.snackbar_root);
    }

    public void attemptLogin() {
        buildLoginHelper().login();
    }

    public void registerUser() {
        buildLoginHelper().signUp();
    }

    private FirebaseLoginHelper buildLoginHelper() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        return new FirebaseLoginHelper.Builder().
                firebase(firebase.get()).
                listener(listener).
                with(email, password).
                build();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userEmail = preferences.getString(PREF_KEY_USER_EMAIL, "");
        mEmailView.setText(userEmail);
        String userPassword = preferences.getString(PREF_KEY_USER_PASSWORD, "");
        mPasswordView.setText(userPassword);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(PREF_KEY_USER_EMAIL, mEmailView.getText().toString())
                .putString(PREF_KEY_USER_PASSWORD, mPasswordView.getText().toString())
                .apply();
    }

    private FirebaseLoginHelper.Listener listener = new FirebaseLoginHelper.Listener() {
        @Override
        public void onSuccess() {
            startMainActivity();
        }

        @Override
        public void onError() {
            Snackbar.make(mSnackbarRoot, getString(R.string.login_error), Snackbar.LENGTH_LONG)
                    .show();
        }
    };
}
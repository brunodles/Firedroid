package rocks.gdgmaceio.firedroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import rocks.gdgmaceio.firedroid.R;
import rocks.gdgmaceio.firedroid.adapter.MessageAdapter;
import rocks.gdgmaceio.firedroid.libs.FirebaseHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = "ChatFragment";

    private ListView messageList;
    private EditText message;
    private Button button;

    private FirebaseHelper firebaseHelper;

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseHelper = new FirebaseHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageList = (ListView) view.findViewById(R.id.messageList);
        message = (EditText) view.findViewById(R.id.message);
        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(this);

        message.setOnEditorActionListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        messageList.setAdapter(new MessageAdapter(firebaseHelper.messages().limitToLast(50)));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        sendMessage();
    }

    private void sendMessage() {
        String messageText = message.getText().toString();
        firebaseHelper.addMessage(messageText);
        this.message.setText("");
    }
}

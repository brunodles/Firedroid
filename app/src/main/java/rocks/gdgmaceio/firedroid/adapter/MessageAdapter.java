package rocks.gdgmaceio.firedroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import rocks.gdgmaceio.firedroid.R;
import rocks.gdgmaceio.firedroid.model.Message;

/**
 * Created by bruno on 07/08/15.
 */
public class MessageAdapter extends FirebaseListAdapter<Message> {

    public MessageAdapter(Query firebase) {
        super(firebase);
    }

    @Override
    protected View getView(int i, View view, ViewGroup viewGroup, Message message) {
        ViewHolder holder;
        if (view == null)
            holder = new ViewHolder(viewGroup.getContext());
        else
            holder = (ViewHolder) view.getTag();
        holder.update(message);
        return holder.getRootView();
    }

    private static class ViewHolder {

        private View rootView;
        TextView messageView;
        TextView authorView;

        public ViewHolder(Context context) {
            rootView = View.inflate(context, R.layout.item_message, null);
            rootView.setTag(this);
            authorView = (TextView) rootView.findViewById(android.R.id.text1);
            messageView = (TextView) rootView.findViewById(android.R.id.text2);
        }

        public void update(Message message) {
            messageView.setText(message.message);
            authorView.setText(message.author);
        }

        public View getRootView() {
            return rootView;
        }
    }
}

package pmc.lab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.List;

/**
 * Created by pmc on 9/23/14.
 */

public class ChatAdapter extends ArrayAdapter<ChatObject>{
    Context context;
    List<ChatObject> objects;

    int resourse;

    public ChatAdapter(Context context, int resourse, List<ChatObject> objects) {
        super(context, resourse, objects);
        Firebase myFirebase = new Firebase("https://chatlab2.firebaseio.com/");
        this.context = context;
        this.resourse = resourse;
        this.objects = objects;
    }

    public class ViewHolder {
        TextView listMessage, listTimestamp, listUsername;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {//item is each view in the list view
        ViewHolder viewHolder;

        if (itemView == null) {
            //if we don't have a viewholder
            viewHolder = new ViewHolder();
            itemView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chat_item, parent, false);
            viewHolder.listMessage = (TextView) itemView.findViewById(R.id.messageItem);
            viewHolder.listTimestamp = (TextView) itemView.findViewById(R.id.timestampItem);
            viewHolder.listUsername = (TextView) itemView.findViewById(R.id.usernameItem);
            itemView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) itemView.getTag(); //cast to the view holder
        }

        viewHolder.listUsername.setText(objects.get(position).getSender());
        viewHolder.listMessage.setText(objects.get(position).getMessage());
        viewHolder.listTimestamp.setText(objects.get(position).getTimestamp());

        return itemView;
   }
}

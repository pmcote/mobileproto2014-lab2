package pmc.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pmc on 9/23/14.
 */

public class ChatAdapter extends ArrayAdapter<ChatObject>{
    Context context;
    List<ChatObject> objects;
    public ChatAdapter(Context context, List<ChatObject> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    public class ViewHolder {
        TextView listMessage;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {//item is each view in the list view
        ViewHolder viewHolder;
        if (itemView == null) {
            //if we don't have a viewholder
            viewHolder = new ViewHolder();
            itemView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item, parent, false);
            viewHolder.listMessage = (TextView) itemView.findViewById(R.id.listItem);
            itemView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) itemView.getTag(); //cast to the view holder
        }
        ChatObject currentObject = getItem(position);
        //content for the chats
        viewHolder.listMessage.setText(currentObject.getMessage());
        return itemView;
   }

    //override the normal content for ArrayAdapter
    @Override
    public int getCount() {
        return objects.size();//how many times
    }

    @Override
    public ChatObject getItem(int position) {
        return objects.get(position);//what position is the item at
    }
}

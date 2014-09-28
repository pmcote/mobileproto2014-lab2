package pmc.lab1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_fragment);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ChatFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ChatFragment extends Fragment {

        public ChatFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);


            //get edit text find view by id
            final EditText chatMessage = (EditText) rootView.findViewById(R.id.editText);
            Log.d("chatMessage", chatMessage.getText().toString());
            //send

            final ListView chatView = (ListView) rootView.findViewById((R.id.listView));

            final List<ChatObject> fakeChats = new ArrayList<ChatObject>();
            fakeChats.add(new ChatObject("yo", "yoyo", ""));

            final ChatAdapter adapter = new ChatAdapter(getActivity() ,fakeChats);

            Button sendMessage = (Button) rootView.findViewById(R.id.button);
            sendMessage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String chatText = chatMessage.getText().toString();
                    fakeChats.add(new ChatObject("Robot", chatText, ""));
                    adapter.notifyDataSetChanged();
                }
            });

            chatView.setAdapter(adapter);

            return rootView;
        }
    }
}

//Inside onClick, use adapter.notify.datasetchanged pass the message to chats

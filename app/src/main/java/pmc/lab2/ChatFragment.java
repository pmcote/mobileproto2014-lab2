package pmc.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */

public class ChatFragment extends Fragment {

    Context context;
    String username = "username";

    public ChatFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final AlertDialog.Builder nameAlert = new AlertDialog.Builder(context);
        nameAlert.setTitle("ChatName");
        nameAlert.setMessage("What is your name?");

        final EditText name = new EditText(context);
        nameAlert.setView(name);

        nameAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String setName = name.getText().toString();
                username = setName;
            }
        });

        nameAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        nameAlert.show();

        Firebase.setAndroidContext(context);
        final Firebase myFirebase = new Firebase("https://chatlab2.firebaseio.com/chatroom/0");

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);


        ListView chatView = (ListView) rootView.findViewById((R.id.listView));

        final ArrayList<ChatObject> chatList = new ArrayList<ChatObject>();

//        final List<ChatObject> fakeChats = new ArrayList<ChatObject>();
//        fakeChats.add(new ChatObject("yo", "yoyo", ""));

        final ChatAdapter adapter = new ChatAdapter(rootView.getContext(), R.layout.chat_item, chatList);
        chatView.setAdapter(adapter);

        //get edit text find view by id
        final EditText chatMessage = (EditText) rootView.findViewById(R.id.editText);
        Log.d("chatMessage", chatMessage.getText().toString());
        final String chatText = chatMessage.getText().toString();

        Button sendMessage = (Button) rootView.findViewById(R.id.button);
        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //grab chat text
                String chatText = chatMessage.getText().toString();
                //get date
                String date = DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
                //Pass to chat object
                ChatObject chat = new ChatObject(chatText, username, date);
                chatList.add(chat);

                //adding chats to local database
                ChatActivity.databaseHandler.addData(chat);

                Firebase postRef = myFirebase;

                Map<String, String> post1 = new HashMap<String, String>();
                post1.put("username", chat.sender);
                post1.put("message", chat.message);
                post1.put("timestamp", chat.timestamp);
                postRef.push().setValue(post1);

                //clear message
                chatMessage.setText("");


                adapter.notifyDataSetChanged();
            }
        });

        myFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Log.d("Firebase chats", dataSnapshot.getValue().toString());
                    ChatObject chat = new ChatObject(dataSnapshot.child("username").getValue().toString(), dataSnapshot.child("message").getValue().toString(), dataSnapshot.child("time").getValue().toString());
                    chatList.add(chat);
                    ChatActivity.databaseHandler.addData(chat);
                    adapter.notifyDataSetChanged();
                } catch (Exception E){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return rootView;
    }
}

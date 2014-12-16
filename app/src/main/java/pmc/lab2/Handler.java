package pmc.lab2;

/**
 * Created by pmc on 12/15/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Handler {
    private LocalDatabase model;

    private SQLiteDatabase database;

    //Set up fields
    private String[] Columns = {
      LocalDatabase.USERNAME,
      LocalDatabase.MESSAGE,
      LocalDatabase.TIMESTAMP
    };


    //connect your database
    public Handler(Context context) {
        model = new LocalDatabase(context);
    }

    public void addData(ChatObject chat) {
        ContentValues values = new ContentValues();
        values.put(LocalDatabase.USERNAME, chat.getSender());
        values.put(LocalDatabase.MESSAGE, chat.getMessage());
        values.put(LocalDatabase.TIMESTAMP, chat.getTimestamp());

        database.insert(LocalDatabase.TABLE_NAME, null, values);
    }

    public ArrayList<ChatObject> getAllChats(){
        return sweepCursor(database.query(LocalDatabase.TABLE_NAME, Columns, null, null, null, null, null));
    }

    /**
     * Additional Helpers
     */
    //Sweep Through Cursor and return a List of Kitties
    private ArrayList<ChatObject> sweepCursor(Cursor cursor){
        ArrayList<ChatObject> kitties = new ArrayList<ChatObject>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the Kitty
            ChatObject chatObject = new ChatObject(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            //Add the Kitty
            kitties.add(chatObject);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return kitties;
    }

    //open your database
    public void open(){
        database = model.getWritableDatabase();
    }
}

package pmc.lab2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pmc on 12/15/14.
 */
public final class LocalDatabase extends SQLiteOpenHelper {
    Context context;

    public static final String TABLE_NAME = "Messages";

    private static final String TEXT_TYPE = "TEXT";

    //Add fields
    public static final String USERNAME = "Username";
    public static final String MESSAGE = "Message";
    public static final String TIMESTAMP = "Time";

    private static final String DATABASE_NAME = "chatMessages";
    private static final int DATABASE_VERSION = 1;

    //set up
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + USERNAME + " TEXT NOT NULL, "
            + MESSAGE + " TEXT NOT NULL, "
            + TIMESTAMP + " TEXT NOT NULL)";

    private static final String DATABASE_DELETE = "DROP TABLE IF EXISTS";

    public LocalDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(LocalDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME +  USERNAME +  MESSAGE + TIMESTAMP);
        database.execSQL(DATABASE_DELETE);
        onCreate(database);
    }
}

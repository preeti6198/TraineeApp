package consultancy.com.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "User";

    private static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    private static final String COLUMN_USER_LAST_NAME = "user_last_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_PHONENO = "user_phoneno";
    private static final String COLUMN_USER_GENDER = "user_gender";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER
            + "("
            + COLUMN_USER_FIRST_NAME + " TEXT, "
            + COLUMN_USER_LAST_NAME + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_USER_PASSWORD + " TEXT, "
            + COLUMN_USER_PHONENO + " TEXT,"
            + COLUMN_USER_GENDER + " TEXT "
            + ")";

    // drop table sql query
    private String DROP_USER_TABLE = " DROP TABLE IF EXISTS " + TABLE_USER;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }


    public boolean checkUser(String email, String password) {
        String[] columns = {
                COLUMN_USER_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "= ?" + " AND " + COLUMN_USER_PASSWORD + " = ? ";
        String[] selectionArgs = {email, password};


        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean updateUser(User user) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_FIRST_NAME, user.getFirst_name());
            values.put(COLUMN_USER_LAST_NAME, user.getLast_name());
            values.put(COLUMN_USER_PHONENO, user.getPhoneno());

            db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?", new String[]{user.getEmail()});
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("updateUser", e.toString());
        }
        return false;
    }

    public boolean addUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_FIRST_NAME, user.getFirst_name());
            values.put(COLUMN_USER_LAST_NAME, user.getLast_name());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PASSWORD, user.getPassword());
            values.put(COLUMN_USER_PHONENO, user.getPhoneno());
            values.put(COLUMN_USER_GENDER, user.getGender());

            db.insert(TABLE_USER, null, values);
            db.close();

            return true;
        } catch (Exception e) {
            Log.e("addUser", e.toString());
        }
        return false;
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> currentUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_FIRST_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_PHONENO,
                COLUMN_USER_GENDER,

        };
        // sorting orders
        String sortOrder = COLUMN_USER_EMAIL + " ASC";
        List<User> userList = new ArrayList<User>();
        String selection = COLUMN_USER_EMAIL + "= ?" ;
        String[] selectionArgs = {email};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setFirst_name(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
                user.setLast_name(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
                user.setPhoneno(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONENO)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }

    public Collection<? extends User> getAllUser() {
        return null;
    }
}

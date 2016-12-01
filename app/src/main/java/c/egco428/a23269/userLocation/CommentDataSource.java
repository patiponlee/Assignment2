package c.egco428.a23269.userLocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 6/11/2559.
 */
public class CommentDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper mySQLiteHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERS, MySQLiteHelper.COLUMN_PASSWORD, MySQLiteHelper.COLUMN_Latitude,MySQLiteHelper.COLUMN_Longtitude};

    public CommentDataSource(Context context){
        mySQLiteHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteAbortException {
        database = mySQLiteHelper.getWritableDatabase();
    }

    public void close(){
        mySQLiteHelper.close();
    }

    public Comment createmap(String user, String password, String latitude,String longtitude){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USERS, user);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        values.put(MySQLiteHelper.COLUMN_Latitude, latitude);
        values.put(MySQLiteHelper.COLUMN_Longtitude,longtitude);


        long insertID = database.insert(MySQLiteHelper.TABLE_RESULTS,null,values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESULTS,allColumns,MySQLiteHelper.COLUMN_ID+" = "+insertID,null,null,null,null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();

        return newComment;

    }

    public void deleteFortuneResult(Comment results){
        long id = results.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_RESULTS, MySQLiteHelper.COLUMN_ID + "=" + id,null);
    }
    public String findpass(String un){

        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_RESULTS
                + " WHERE " + MySQLiteHelper.COLUMN_USERS + "=" + "'"+un+"'", null);
        cursor.moveToFirst();

        if(cursor.getCount() <= 0){
            cursor.close();
            return "";
        }
        else {
            Comment newComment = cursorToComment(cursor);
            String p = newComment.getPassword();
            cursor.close();
            return p;
        }

    }
    public List<Comment> getAllComments(){
        List<Comment> results = new ArrayList<Comment>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESULTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Comment newresult = cursorToComment(cursor);
            results.add(newresult);
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    public Comment cursorToComment(Cursor cursor){
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setUser(cursor.getString(1));
        comment.setPassword(cursor.getString(2));
        comment.setLatitude(cursor.getString(3));
        comment.setLongtitude(cursor.getString(4));
        return comment;
    }
}

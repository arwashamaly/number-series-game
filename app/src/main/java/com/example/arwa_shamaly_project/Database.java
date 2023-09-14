package com.example.arwa_shamaly_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String DB_NAME = "game";
    public static final int DB_VERSION = 26;
    public static final String USERS_TBL_NAME = "users_table";
    public static final String USERS_CLN_Id = "id";
    public static final String USERS_CLN_full_NAME = "full_name";
    public static final String USERS_CLN_IMAGE = "image_uri";
    public static final String USERS_CLN_EMAIL = "email";
    public static final String USERS_CLN_USERNAME = "username";
    public static final String USERS_CLN_PASSWORD = "password";
    public static final String USERS_CLN_COUNTRY = "country";
    public static final String USERS_CLN_BIRTHDATE = "birthdate";
    public static final String USERS_CLN_GENDER = "gender";
    public static final String USERS_CLN_YEAR_OF_BIRTH = "year_of_birth";


    public static final String GAME_TBL_NAME = "game";
    public static final String GAME_CLN_Id = "id";
    public static final String GAME_CLN_USERFULLNAME = "userfullname";
    public static final String GAME_CLN_SCORE = "score";
    public static final String GAME_CLN_DATE = "date";
    public static final String GAME_CLN_USERSId = "id_user";

    public Database(Context context) {
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL("create table "+USERS_TBL_NAME+" ("+USERS_CLN_Id+" Integer primary key " +
                "autoincrement, "+USERS_CLN_full_NAME+" text, "+USERS_CLN_EMAIL+" text," +
                USERS_CLN_IMAGE+" text, "+USERS_CLN_USERNAME+" text, "+USERS_CLN_PASSWORD+" text," +
                USERS_CLN_COUNTRY+" text, "+USERS_CLN_BIRTHDATE+" text, "+  USERS_CLN_GENDER+" Integer," +
              USERS_CLN_YEAR_OF_BIRTH+" Integer)");

       sqLiteDatabase.execSQL("create table "+GAME_TBL_NAME+" ("+GAME_CLN_Id+" Integer primary key "+
                "autoincrement, "+GAME_CLN_USERFULLNAME+" text ,"
               +GAME_CLN_SCORE+" Integer,"+GAME_CLN_DATE+" text ,"
               +GAME_CLN_USERSId+" Integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+USERS_TBL_NAME);
        sqLiteDatabase.execSQL("drop table if exists "+GAME_TBL_NAME);
        onCreate(sqLiteDatabase);
    }

   public boolean insertUsers(User user){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_CLN_full_NAME,user.getFull_name());
        values.put(USERS_CLN_IMAGE,user.getUri());
        values.put(USERS_CLN_EMAIL,user.getEmail());
        values.put(USERS_CLN_USERNAME,user.getUsername());
        values.put(USERS_CLN_PASSWORD,user.getPassword());
        values.put(USERS_CLN_COUNTRY,user.getCountry());
        values.put(USERS_CLN_BIRTHDATE,user.getBirthdate());
        values.put(USERS_CLN_GENDER,user.getGender());
        values.put(USERS_CLN_YEAR_OF_BIRTH,user.getYearOfBirth());
        Long result = db.insert(USERS_TBL_NAME,null,values);
        return result != -1;
    }

    public boolean checkusername(String username) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {username};
        Cursor cursor = db.rawQuery("select * from "+USERS_TBL_NAME+ " where "
                +USERS_CLN_USERNAME+" =? ", args);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false; } }

    public boolean checkusernamepassword(String username , String password){
        SQLiteDatabase db = getWritableDatabase();
        String args []={username,password};
        Cursor cursor = db.rawQuery("select * from "+USERS_TBL_NAME+" where "
                +USERS_CLN_USERNAME+" =? "+" and "+USERS_CLN_PASSWORD+"=?",args);
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public User getuser(String username){
        User user = new User();
        SQLiteDatabase db=getWritableDatabase();
        String args []={username};
       Cursor cursor =db.rawQuery("select * from " +USERS_TBL_NAME+" where "+
               USERS_CLN_USERNAME+" =? ",args);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex(USERS_CLN_Id));
            String full_name = cursor.getString(cursor.getColumnIndex(USERS_CLN_full_NAME));
            String user_name = cursor.getString(cursor.getColumnIndex(USERS_CLN_USERNAME));
            int yearOfBirth = cursor.getInt(cursor.getColumnIndex(USERS_CLN_YEAR_OF_BIRTH));
             user = new User(id,full_name,user_name,yearOfBirth);
        }
        return user;
    }

    public String getPasswordByID(int userId){
        String password = "";
        SQLiteDatabase db=getReadableDatabase();
        String args []={String.valueOf(userId)};
        Cursor cursor =db.rawQuery("select "+USERS_CLN_PASSWORD+" from " +USERS_TBL_NAME+" where "+
                USERS_CLN_Id+" =? ",args);

        if (cursor.getCount()>0&&cursor.moveToFirst()){
            password= cursor.getString(cursor.getColumnIndex(USERS_CLN_PASSWORD));
            return password;
        }
        return password;
    }

    public boolean updatePassword(String newPassword,int id){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_CLN_PASSWORD,newPassword);
        String args[]={String.valueOf(id)};
        int result = db.update(USERS_TBL_NAME,values,USERS_CLN_Id+"=?",args);
        return result >0;
    }

    //game

    public boolean insertGame(Game game){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GAME_CLN_SCORE,game.getScore());
        values.put(GAME_CLN_DATE,game.getDate());
        values.put(GAME_CLN_USERSId,game.getId_user());
        values.put(GAME_CLN_USERFULLNAME,game.getUserFullName());
        Long result = db.insert(GAME_TBL_NAME,null,values);
        return result != -1;
    }

    public ArrayList<Game> getGames(int userId){
        ArrayList<Game> games = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String args [] ={String.valueOf(userId)};
        Cursor cursor = db.rawQuery("select * from "+GAME_TBL_NAME+" where "+ GAME_CLN_USERSId +" =?"
                ,args);

        if (cursor.moveToFirst()){
            do{
                //قراءة البيانات من الكيرسر و اضافتها على المصفوفة
                int score = cursor.getInt(cursor.getColumnIndex(GAME_CLN_SCORE));
                String date = cursor.getString(cursor.getColumnIndex(GAME_CLN_DATE));
                String fullName = cursor.getString(cursor.getColumnIndex(GAME_CLN_USERFULLNAME));
                Game game = new Game(score,date,fullName);
                games.add(game);
            }while (cursor.moveToNext());
            cursor.close();
        }

        return games;
    }

    public String getDate(int userId){
        String date = "play first";
        SQLiteDatabase db=getReadableDatabase();
        String args []={String.valueOf(userId)};
        Cursor cursor =db.rawQuery("select "+GAME_CLN_DATE+" from " +GAME_TBL_NAME+" where "+
                GAME_CLN_USERSId+" =? ",args);

        if (cursor.getCount()>0){
            cursor.moveToLast();
            date= cursor.getString(cursor.getColumnIndex(GAME_CLN_DATE));
            return date;
        }
        return date;
    }

    public boolean deleteAllGameByID(int userId){
        SQLiteDatabase db = getWritableDatabase();
        String args[] ={String.valueOf(userId)};
        int result =db.delete(GAME_TBL_NAME,GAME_CLN_USERSId+" =?",args);
       return result >0 ;
    }

    public int getFinalScore(int userId){
        int finalScore = 0;
        SQLiteDatabase db=getReadableDatabase();
        String args []={String.valueOf(userId)};
        Cursor cursor =db.rawQuery("select "+GAME_CLN_SCORE+" from " +GAME_TBL_NAME+" where "+
                GAME_CLN_USERSId+" =? ",args);

        if (cursor.getCount()>0){
            cursor.moveToLast();
            finalScore= cursor.getInt(cursor.getColumnIndex(GAME_CLN_SCORE));
            return finalScore;
        }
        return finalScore;
    }

}

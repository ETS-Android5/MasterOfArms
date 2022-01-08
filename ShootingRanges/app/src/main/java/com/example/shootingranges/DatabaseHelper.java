package com.example.shootingranges;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="ShootingRanges.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table playerDetail(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PASSWORD TEXT,DOB TEXT,PHONE TEXT,GENDER TEXT,COURSE TEXT,ADV TEXT,SLOT TEXT)");
        sqLiteDatabase.execSQL("create table slotBooking(TIME TEXT,COUNTER INTEGER)");
        sqLiteDatabase.execSQL("insert into slotBooking(TIME,COUNTER) values('7am-9am',9)");
        sqLiteDatabase.execSQL("insert into slotBooking(TIME,COUNTER) values('9am-11am',9)");
        sqLiteDatabase.execSQL("insert into slotBooking(TIME,COUNTER) values('11am-1pm',9)");
        sqLiteDatabase.execSQL("insert into slotBooking(TIME,COUNTER) values('1pm-3pm',9)");
        sqLiteDatabase.execSQL("insert into slotBooking(TIME,COUNTER) values('3pm-5pm',9)");
        sqLiteDatabase.execSQL("insert into slotBooking(TIME,COUNTER) values('5pm-7pm',9)");
        sqLiteDatabase.execSQL("insert into slotBooking(TIME,COUNTER) values('7pm-9pm',1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists playerDetail");
        sqLiteDatabase.execSQL("drop table if exists slotBooking");
        onCreate(sqLiteDatabase);
    }


    //inserting in database
    public boolean insertData(String name,String email,String password,String dob,String phone,String gender)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("EMAIL",email);
        contentValues.put("PASSWORD",password);
        contentValues.put("DOB",dob);
        contentValues.put("PHONE",phone);
        contentValues.put("GENDER",gender);

        long result=sqLiteDatabase.insert("playerDetail",null,contentValues);
        if(result==-1) {
            return false;
        }
        else {
            return true;
        }
    }


    //checking if email exists
    public boolean chkemail(String email)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from playerDetail where EMAIL = ? ",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }


    //checking if email and password are in database
    public boolean chkemailpassword(String email,String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from playerDetail where EMAIL = ? and PASSWORD =? ",new String[]{email,password});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else return false;
    }


    //retrieve data from database
    public Cursor getData(String email)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from playerDetail where EMAIL = ?",new String[]{email});
        return res;
    }


    //insert course data
    public boolean updateData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE","Basic");
        db.update("playerDetail", contentValues, "ID = ?",new String[] { id });
        return true;
    }


    //retrieve course from database
    public Cursor getCourse(String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from playerDetail where ID = ?",new String[]{id});
        return res;
    }


    //insert adv course data
    public boolean updateAdvData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ADV","Advance");
        db.update("playerDetail", contentValues, "ID = ?",new String[] { id });
        return true;
    }

    //retrieve advance course from database
    public Cursor getAdvCourse(String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from playerDetail where ID = ?",new String[]{id});
        return res;
    }


    //insert slot in database
    public boolean insertSlot(String id,String slot)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("SLOT",slot);
        sqLiteDatabase.update("playerDetail", contentValues, "ID = ?",new String[] { id });
        return true;
    }

    //retrieve slot from playerDetail
    public Cursor getPlayerSlot(String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from playerDetail where ID = ?",new String[]{id});
        return res;
    }


    //retrieve slot from slotBooking
    public Cursor getSlot(String time)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from slotBooking where TIME = ?",new String[]{time});
        return res;
    }

    //decrease counter in slotBooking
    public boolean updateSlot(String time,int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COUNTER",count);
        db.update("slotBooking", contentValues, "TIME = ?",new String[] { time });
        return true;
    }

    //increase counter in slotBooking
    public boolean updateIncreaseSlot(String time,int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COUNTER",count);
        db.update("slotBooking", contentValues, "TIME = ?",new String[] { time });
        return true;
    }

    //delete slot in playerDetail
    public boolean deleteSlot(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.putNull("SLOT");
        db.update("playerDetail", contentValues, "ID = ?",new String[] { id });
        return true;
    }
}

package com.example.test.dbapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Organisation.db";
    public static final String TABLE_NAME="Employee_Details";
    public static final String COL_ID="Employee_Id";
    public static final String COL_NAME="Employee_Name";
    public static final String COL_DESIG="Designation";

    private static DbHelper dbHelper=null;

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DbHelper getInstance(Context context){
        if(dbHelper==null){
            dbHelper=new DbHelper(context);
        }
        return  dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(Employee_Id INTEGER PRIMARY KEY," + COL_NAME + " TEXT,"+ COL_DESIG +" TEXT )";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE="DROP TABLE IF EXISTS "+ TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insertData(int empId,String empName,String designation){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(COL_NAME,empName);
        values.put(COL_ID,empId);
        values.put(COL_DESIG,designation);

        long res=db.insert(TABLE_NAME,null,values);
        return res;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return  res;
    }

    public Cursor getSpecificData(String query){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery(query,null);
        return  res;
    }

    public int updateEntry(int oldId,int newId,String newName,String newDesignation){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(COL_NAME,newName);
        values.put(COL_ID,newId);
        values.put(COL_DESIG,newDesignation);

        //db.execSQL("UPDATE "+TABLE_NAME+" SET "+COL_NAME+" = '"+newName+"', "+COL_ID+" = "+newId+" , "+COL_DESIG+" = '"+newDesignation+"' WHERE "+COL_ID+" = "+oldId);
        return db.update(DbHelper.TABLE_NAME,values,DbHelper.COL_ID+" = "+oldId,null);
    }

    public int deleteEntry(int id){
        SQLiteDatabase db=this.getWritableDatabase();

        return db.delete(TABLE_NAME,COL_ID+" = "+id,null);
    }

    public boolean isIdExists(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT "+COL_ID+" FROM "+ TABLE_NAME +" WHERE "+COL_ID+" = "+id,null);
        if(res.getCount()>0){
            return true;
        }
        else{
            return  false;
        }
    }
}

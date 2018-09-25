package com.example.test.dbapplication;

import android.provider.BaseColumns;

public final class DbContract {
    private DbContract(){}

    public static class DbEntry implements BaseColumns{
        public static final String TABLE_NAME="Employee_Details";
        public static final String COL_ID="Employee_Id";
        public static final String COL_NAME="Employee_Name";
        public static final String COL_DESIG="Designation";
    }
}

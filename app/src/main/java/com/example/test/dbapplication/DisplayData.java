package com.example.test.dbapplication;

import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayData extends AppCompatActivity {
    private TextInputEditText empId,empName,empDesig;
    private TextView empIdText,empNameText,empDesigText;
    private LinearLayout editButton,deleteButton;
    private android.support.v7.widget.Toolbar toolbar;
    DbHelper dbHelper;
    Cursor cursor;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        empId=findViewById(R.id.dis_id_edit_text);
        empName=findViewById(R.id.dis_name_edit_text);
        empDesig=findViewById(R.id.dis_desig_edit_text);

        empIdText=findViewById(R.id.dis_id_text);
        empNameText=findViewById(R.id.dis_name_text);
        empDesigText=findViewById(R.id.dis_desig_text);

        editButton=findViewById(R.id.editButton);
        deleteButton=findViewById(R.id.deleteButton);

        toolbar=findViewById(R.id.toolbar);

        id=getIntent().getIntExtra("id",0);

        dbHelper=DbHelper.getInstance(this);
        String query="SELECT * FROM "+DbHelper.TABLE_NAME+" WHERE "+DbHelper.COL_ID+" = "+id;
        cursor=dbHelper.getSpecificData(query);

        cursor.moveToNext();
        empIdText.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(DbHelper.COL_ID))));
        empNameText.setText(cursor.getString(cursor.getColumnIndex(DbHelper.COL_NAME)));
        empDesigText.setText(cursor.getString(cursor.getColumnIndex(DbHelper.COL_DESIG)));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empId.setVisibility(View.VISIBLE);
                empName.setVisibility(View.VISIBLE);
                empDesig.setVisibility(View.VISIBLE);

                empIdText.setVisibility(View.GONE);
                empNameText.setVisibility(View.GONE);
                empDesigText.setVisibility(View.GONE);

                editData();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    public void editData(){
        toolbar.setVisibility(View.GONE);
        View saveButton= findViewById(R.id.save);
        saveButton.setVisibility(View.VISIBLE);

        empId.setText(empIdText.getText().toString());
        empName.setText(empNameText.getText().toString());
        empDesig.setText(empDesigText.getText().toString());

        ConstraintLayout rootLayout=findViewById(R.id.rootLayout);

        ConstraintSet constraintSet=new ConstraintSet();
        constraintSet.clone(rootLayout);

        constraintSet.connect(R.id.textViewId,ConstraintSet.BASELINE,empId.getId(),ConstraintSet.BASELINE);
        constraintSet.connect(R.id.textViewId,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START);
        constraintSet.connect(R.id.textViewId,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);

        constraintSet.connect(R.id.textViewName,ConstraintSet.BASELINE,empName.getId(),ConstraintSet.BASELINE);
        constraintSet.connect(R.id.textViewName,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START);
        constraintSet.connect(R.id.textViewName,ConstraintSet.TOP,R.id.textViewId,ConstraintSet.BOTTOM);

        constraintSet.connect(R.id.textViewDesig,ConstraintSet.BASELINE,empDesig.getId(),ConstraintSet.BASELINE);
        constraintSet.connect(R.id.textViewDesig,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START);
        constraintSet.connect(R.id.textViewDesig,ConstraintSet.TOP,R.id.textViewName,ConstraintSet.BOTTOM);

        constraintSet.applyTo(rootLayout);

        empId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                empId.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    Integer.parseInt(empId.getText().toString());
                }
                catch (NumberFormatException e){
                    empId.setError("Enter a valid Id Number");
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    public void updateData(){
        if(empId.getText().toString().trim().length()==0){
            empId.setError("This field cannot be empty");
        }
        else {
            //String query="UPDATE "+DbHelper.TABLE_NAME+" SET "+DbHelper.COL_NAME+" = 'MONALISA' WHERE "+DbHelper.COL_ID+" = "+id;
            int res= dbHelper.updateEntry(id,Integer.parseInt(empId.getText().toString()),empName.getText().toString(),empDesig.getText().toString());
            Toast.makeText(this,res+" rows affected",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void deleteData(){
        int res= dbHelper.deleteEntry(Integer.parseInt(empIdText.getText().toString()));
        Toast.makeText(this,res+" rows affected",Toast.LENGTH_LONG).show();
        finish();
    }


}

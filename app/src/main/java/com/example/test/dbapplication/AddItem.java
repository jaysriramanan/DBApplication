package com.example.test.dbapplication;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {
    private TextInputEditText empId,empName,designation;
    Button addButton,cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        empId=findViewById(R.id.dis_id_edit_text);
        empName=findViewById(R.id.dis_name_edit_text);
        designation=findViewById(R.id.dis_desig_edit_text);
        addButton=findViewById(R.id.addButton);
        cancelButton=findViewById(R.id.cancelButton);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void addData(){
        if(empId.getText().toString().trim().length()==0){
            empId.setError("This field cannot be empty");
        }
        else{
            DbHelper dbHelper=DbHelper.getInstance(this);
            long res=dbHelper.insertData(Integer.parseInt(empId.getText().toString().trim()),empName.getText().toString(),designation.getText().toString());
            if(res==-1){
                if(dbHelper.isIdExists(Integer.parseInt(empId.getText().toString()))){
                    empId.setError("Employee Id already exists");
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Failed to Add...!!");
                    builder.setMessage("Please check each field and retry")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
            else {
                Toast.makeText(this,"Added successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

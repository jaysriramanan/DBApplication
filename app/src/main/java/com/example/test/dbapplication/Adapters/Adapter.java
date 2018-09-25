package com.example.test.dbapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.dbapplication.DbHelper;
import com.example.test.dbapplication.DisplayData;
import com.example.test.dbapplication.R;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private Cursor cursor;

    public Adapter(Context context,Cursor cursor) {
        this.context = context;
        this.cursor=cursor;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        if(cursor.moveToPosition(i)){
            holder.empId.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(DbHelper.COL_ID))));
            holder.empName.setText(cursor.getString(cursor.getColumnIndex(DbHelper.COL_NAME)));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),DisplayData.class);
                    intent.putExtra("id",Integer.parseInt(holder.empId.getText().toString()));
                    view.getContext().startActivity(intent);
                }
            });
        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView empId,empName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            empId=itemView.findViewById(R.id.empId_recycler);
            empName=itemView.findViewById(R.id.empName_recycler);
        }
    }

    public void swapCursor(Cursor updatedCursor){
        if(cursor!=null){
            cursor.close();
        }
        cursor=updatedCursor;

        if(updatedCursor!=null){
            notifyDataSetChanged();
        }
    }
}

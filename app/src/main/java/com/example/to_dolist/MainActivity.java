package com.example.to_dolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ListView listViewDS;
    ImageButton btnThem;
    JobDB myDB = new JobDB(MainActivity.this);
    ArrayList<Job> arrJob;
    JobAdapter adapter;
    int jobPosition;

    @Override
    protected void onStart() {
        super.onStart();
        myDB.onOpen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDB.closeDB();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitWidget();

        //adapter
        arrJob = new ArrayList<>();
        adapter = new JobAdapter(MainActivity.this, R.layout.job_adapter, arrJob);
        listViewDS.setAdapter(adapter);
        dsLoad(null);
        //btnThem
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });
        //listView
        listViewDS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                jobPosition = position;
                return false;
            }
        });
        registerForContextMenu(listViewDS);

    }

    private void showDialogAdd(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_them);
        EditText edtMaCV = (EditText) dialog.findViewById(R.id.edtMaCV);
        EditText edtTenCV = (EditText) dialog.findViewById(R.id.edtTenCV);
        EditText edtTenNV = (EditText) dialog.findViewById(R.id.edtTenNV);
        EditText edtSDT = (EditText) dialog.findViewById(R.id.edtSDT);
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        Button btnBack = (Button) dialog.findViewById(R.id.btnBack);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maCV = edtMaCV.getText().toString();
                String tenCV = edtTenCV.getText().toString();
                String tenNV = edtTenNV.getText().toString();
                String SDT = edtSDT.getText().toString();
                long insertResult = myDB.Insert(maCV, tenCV, tenNV, SDT);
                if (insertResult > 0) {
                    Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    dsLoad(null);
                    autoSetAfterAddSuccess(edtMaCV, edtTenCV, edtTenNV, edtSDT);
                } else {
                    Toast.makeText(MainActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();

    }

    //context menu để sửa xóa
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Sửa");
        menu.add(0, v.getId(), 0, "Xóa");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle() == "Sửa"){
            Intent intent = new Intent(MainActivity.this, JobDetails.class);
            intent.putExtra("cv", arrJob.get(jobPosition));
            startActivity(intent);
        }
        else if(item.getTitle() == "Xóa") {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("Bạn có chắc chắn xóa \""+arrJob.get(jobPosition).getMaCV()+"\" ?").setTitle("Delete this?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long deleteResult= myDB.Delete(arrJob.get(jobPosition).getMaCV());
                    String maCV = arrJob.get(jobPosition).getMaCV();
                    if(deleteResult==0){
                        Toast.makeText(MainActivity.this, "Xóa "+maCV+" thất bại", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, maCV+" đã được xóa thành công", Toast.LENGTH_SHORT).show();
                        dsLoad(null);
                    }
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }
        return super.onContextItemSelected(item);
    }

    public void autoSetAfterAddSuccess(EditText edtMaCV, EditText edtTenCV, EditText edtTenNV, EditText edtSDT){
        edtTenCV.setText("");
        edtTenNV.setText("");
        edtSDT.setText("");
        edtTenCV.requestFocus();
    }

    private void dsLoad(String searchName) {
        arrJob.clear();
        Cursor cursor = null;
        if(searchName==null) {
            cursor = myDB.DisplayAll();
        }
        else{
            cursor = myDB.DisplayByName(searchName);
        }
        while (cursor.moveToNext()) {
            String maCV = cursor.getString(cursor.getColumnIndexOrThrow(JobDB.MACONGVIEC));
            String tenCV = cursor.getString(cursor.getColumnIndexOrThrow(JobDB.TENCONGVIEC));
            String tenNV = cursor.getString(cursor.getColumnIndexOrThrow(JobDB.TENNHANVIEN));
            String SDT = cursor.getString(cursor.getColumnIndexOrThrow(JobDB.SODIENTHOAI));
            arrJob.add(new Job(maCV, tenCV, tenNV, SDT));
        }
        adapter.notifyDataSetChanged();

    }

    private void InitWidget() {
        listViewDS = findViewById(R.id.listViewDS);
        btnThem = findViewById(R.id.btnThem);
    }
}
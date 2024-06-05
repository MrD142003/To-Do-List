package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JobDetails extends AppCompatActivity {
    EditText edtMaCVDetails, edtTenCVDetails, edtTenNVDetails, edtSDTDetails;
    Button btnSua, btnTroLai;
    JobDB mydb =new JobDB(this);

    @Override
    protected void onStart() {
        super.onStart();
        mydb.onOpen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mydb.closeDB();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        InitWidget();
        Intent intent = getIntent();
        Job job = (Job) intent.getSerializableExtra("cv");
        edtMaCVDetails.setText(job.getMaCV());
        edtTenCVDetails.setText(job.getTenCV());
        edtTenNVDetails.setText(job.getTenNV());
        edtSDTDetails.setText(job.getSdt());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maCV = edtMaCVDetails.getText().toString();
                String tenCV = edtTenCVDetails.getText().toString();
                String tenNV = edtTenNVDetails.getText().toString();
                String SDT = edtSDTDetails.getText().toString();
                long editResult = mydb.Update(maCV, tenCV, tenNV, SDT);
                if(editResult>0){
                    Toast.makeText(JobDetails.this, "Sửa \""+maCV+"\" thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(JobDetails.this, "Sửa \""+maCV+"\" thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(JobDetails.this,MainActivity.class);
                startActivity(intent1)  ;
            }
        });
    }

    private void InitWidget() {
        edtMaCVDetails = (EditText) findViewById(R.id.edtMaCVDetails);
        edtTenCVDetails = (EditText) findViewById(R.id.edtTenCVDetails);
        edtTenNVDetails = (EditText) findViewById(R.id.edtTenNVDetails);
        edtSDTDetails = (EditText) findViewById(R.id.edtSDTDetails);
        btnSua = (Button) findViewById(R.id.btnSua);
        btnTroLai = (Button) findViewById(R.id.btnTrolai);
    }
}
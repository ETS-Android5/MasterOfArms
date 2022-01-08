package com.example.shootingranges;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;



public class BasicActivity2 extends AppCompatActivity {

    public String id,name,phone;

    //menu activity data
    public String email;

    TextView txtid,txtname,txtcourse,txtfees;
    private static final String TAG = "PdfCreatorActivity";

    private Button mCreateButton;
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic2);
        setTitle("Basic Course Application Form");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        Intent intent=getIntent();
        id=intent.getStringExtra("Id");
        name=intent.getStringExtra("Name");
        phone=intent.getStringExtra("Phone");


        txtid=(TextView)findViewById(R.id.txtVwId);
        txtname=(TextView)findViewById(R.id.ttxtVwName);
        txtcourse=(TextView)findViewById(R.id.txtVwCourse);
        txtfees=(TextView)findViewById(R.id.txtVwFees);


        txtid.setText(id);
        txtname.setText(name);

        mCreateButton = (Button) findViewById(R.id.btnPdf);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void createPdfWrapper() throws FileNotFoundException,DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"Basic Application Form.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();


        PdfWriter.getInstance(document, output);
        document.open();
        TextView form=(TextView)findViewById(R.id.textView1);
        TextView txt_id=(TextView)findViewById(R.id.textView2);
        TextView txt_name=(TextView)findViewById(R.id.textView3);
        TextView txt_course=(TextView)findViewById(R.id.textView4);
        TextView txt_fees=(TextView)findViewById(R.id.textView5);

        document.add(new Paragraph(form.getText().toString()));


        document.add(new Paragraph("\n"));
        String id1=txt_id.getText().toString();
        String id2="  ";
        String id3=txtid.getText().toString();
        String id4=id1+"\t\t"+id3;
        document.add(new Paragraph(id4));
        //document.add(new Paragraph(txt_id.getText().toString()));
        //document.add(new Paragraph(txtid.getText().toString()));

        document.add(new Paragraph("\n"));
        String name1=txt_name.getText().toString();
        String name2=txtname.getText().toString();
        String name3=name1+name2;
        document.add(new Paragraph(name3));
        //document.add(new Paragraph(txt_name.getText().toString()));
        //document.add(new Paragraph(txtname.getText().toString()));


        document.add(new Paragraph("\n"));
        String course1=txt_course.getText().toString();
        String course2=txtcourse.getText().toString();
        String course3=course1+course2;
        document.add(new Paragraph(course3));
        //document.add(new Paragraph(txt_course.getText().toString()));
        //document.add(new Paragraph(txtcourse.getText().toString()));


        document.add(new Paragraph("\n"));
        String fees1=txt_fees.getText().toString();
        String fees2=txtfees.getText().toString();
        String fees3=fees1+fees2;
       // document.add(new Paragraph(txt_fees.getText().toString()));
       // document.add(new Paragraph(txtfees.getText().toString()));

        document.add(new Paragraph(fees3));

        document.close();
        previewPdf();

    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(this,"PDF Downloaded",Toast.LENGTH_SHORT).show();
        }
    }


    public void Ok(View view)
    {
           Intent intent=new Intent(BasicActivity2.this,MenuActivity.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           // startActivity(intent);
            finish();
    }
   }

package com.example.statusnewwatsaap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class Onclike extends AppCompatActivity {
    Button button,button2;
    //TextView mTitletv,mDecription ;
    private ImageView mImageView;
    private Uri mImageuri;
    Bitmap bitmap;
    private static final int WRITE_EXTIRNAL_STORAGE_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclike);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("pig photo");
        // set backbutton in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //initilaise view
        button=findViewById(R.id.botton1);
        button2=findViewById(R.id.botton2);

        //  mTitletv=findViewById(R.id.titletv);
        //    mDecription=findViewById(R.id.descritiontv);
        mImageView=findViewById(R.id.imageview);
        //get data from inten
        byte[] bytes = getIntent().getByteArrayExtra("image");
        //  String title=getIntent().getStringExtra("title");
        //  String desc=getIntent().getStringExtra("description");
        Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //set data to views
        //  mTitletv.setText(title);
        //  mDecription.setText(desc);
        mImageView.setImageBitmap(bmp);

        //get image frome imageview  as bitmap
        bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, WRITE_EXTIRNAL_STORAGE_CODE);
                    } else {
                        saveImage();
                    }
                }
                else{
                    saveImage();
                }
            }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });

    }

    private void saveImage() {
        //time stamp ,for image name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //path to external storag
        File path = Environment.getExternalStorageDirectory();
        // create folder named "firebace"
        File dir = new File(path + "/Firebase/");
        dir.mkdirs();
        String imagename = timeStamp + ".PNG";
        File file = new File(dir, imagename);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this, imagename + "saved to" + dir, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //failed saving image
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void shareImage() {
        //File path = Environment.getExternalStorageDirectory();
        // create folder named "firebace"
        try {
            File file = new File(getExternalCacheDir(),"sample.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true,false);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.putExtra(intent.EXTRA_TEXT,s);
            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent,"Share via"));
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    // handel onbackpress prevuse activety

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case WRITE_EXTIRNAL_STORAGE_CODE:{
                //if request code is cancelled the result arrays are empty
                if(grantResults.length > 0 && grantResults[0]==
                        PackageManager.PERMISSION_GRANTED){
                    //permision is granded,saveimage()
                    saveImage();
                }
                else {
                    //permission denide
                    Toast.makeText(this,"enable permission to save image",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
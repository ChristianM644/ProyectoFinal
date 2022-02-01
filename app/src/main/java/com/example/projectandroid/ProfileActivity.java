package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageProfile;
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST=100;
    private static final int IMAGE_PICK_CAMERA_REQUEST=400;

    String cameraPermision[];
    Uri imageUri;
    String profileOrCoverImage;
    MaterialButton editImage;
    //Botones del Formulario
    EditText editTextName;
    EditText editLastName;
    EditText editPhoneNumber;
    EditText editEmail;

    MaterialButton btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editImage=findViewById(R.id.edit_image);
        imageProfile =findViewById(R.id.profile_image);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        cameraPermision=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //SETEAMOS EL EDIT TEXT Y EL BOTON DE EDITAR
        editTextName=(EditText) findViewById(R.id.editTextName);
        editLastName=(EditText) findViewById(R.id.edit_lastName);
        editPhoneNumber=(EditText) findViewById(R.id.edit_phone);
        editEmail=(EditText) findViewById(R.id.edit_email);

        btnEdit=(MaterialButton) findViewById(R.id.edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnEdit.getText()==getResources().getString(R.string.edit))
                {
                    btnEdit.setText(R.string.save);
                    editTextName.setEnabled(true);
                    editLastName.setEnabled(true);
                    editPhoneNumber.setEnabled(true);
                    editEmail.setEnabled(true);
                }else
                {
                    editTextName.setEnabled(false);
                    editLastName.setEnabled(false);
                    editPhoneNumber.setEnabled(false);
                    editEmail.setEnabled(false);
                    editTextName.requestFocus();

                    btnEdit.setText(R.string.edit);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressDialog.setMessage("Updating Profile Picture");
                profileOrCoverImage="image";
                showImagePicDialog();
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Glide.with(this).load(imageUri).into(imageProfile);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);
        switch (requestCode)
        {
            case CAMERA_REQUEST:{
                if(grantResult.length>0)
                {
                    boolean cameraAccepted=grantResult[0]== PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted=grantResult[1]== PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeStorageAccepted)
                    {
                        pickFromCamera();
                    }
                    else
                    {
                        Toast.makeText(this,"Please enable camera and Storage permission",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this,"Something went wrong! try again...",Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    private void showImagePicDialog()
    {
        String options[]={"Camera","Gallery"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick image from");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0)
                {
                    if(!checkCameraPermission())
                    {
                        requestCameraPermision();
                    }
                    else
                    {
                        pickFromCamera();
                    }
                }
            }
        });
        builder.create().show();
    }

    private Boolean checkCameraPermission()
    {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermision()
    {
        requestPermissions(cameraPermision,CAMERA_REQUEST);
    }

    private void pickFromCamera()
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_pic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Temp description");
        imageUri=this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_REQUEST);
    }
}
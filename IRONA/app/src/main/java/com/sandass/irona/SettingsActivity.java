package com.sandass.irona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;



public class SettingsActivity extends AppCompatActivity {

    private CircleImageView cimg;
    private TextView name;
    private TextView status;
    private Button chngImg;
    private Button chngStatus;
    private DatabaseReference mDatabaseref;
    private static final int GALLARY_PICK=1;
    private StorageReference mStorageRef;
    private  ProgressDialog mprogbar;

    String Curr_Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name=findViewById(R.id.settings_Dname);
        status=findViewById(R.id.settings_status);
        cimg =findViewById(R.id.setting_img);
        chngImg=findViewById(R.id.settings_cdp);
        chngStatus=findViewById(R.id.settings_cs);

        mprogbar =  new ProgressDialog(this);



        mStorageRef = FirebaseStorage.getInstance().getReference();

        mDatabaseref= FirebaseDatabase.getInstance().getReference().child("Users").child(Curr_Uid);


        chngStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent mIntent =new Intent(SettingsActivity.this, StatusActivity.class);
                startActivity(mIntent);
                finish();
            }
        });

        chngImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallaryIntent= new Intent();
                gallaryIntent.setType("image/*");
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallaryIntent, "SELECT IMAGE"), GALLARY_PICK);

            }
        });

        //Setting datas to the firebase.

        mDatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String dName=dataSnapshot.child("Name").getValue().toString();
                String mStatus=dataSnapshot.child("Status").getValue().toString();
                String mpic=dataSnapshot.child("image").getValue().toString();
                String mBpic=dataSnapshot.child("thumb_img").getValue().toString();

                name.setText(dName);
                status.setText(mStatus);
                Picasso.get().load(mpic).into(cimg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SettingsActivity.this, "Database error", Toast.LENGTH_LONG).show();

            }
        });


    }

    //Image Picker and cropper
     @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode== GALLARY_PICK && resultCode== RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                //Progress Dialoge
                mprogbar.setTitle("Uploading Image");
                mprogbar.setMessage("Wait untill the image has been updated successfully");
                mprogbar.setCanceledOnTouchOutside(false);
                mprogbar.show();

                Uri resultUri = result.getUri();

                StorageReference filepath= mStorageRef.child("profile_pic").child(Curr_Uid + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){

                            String download_uri=task.getResult().getDownloadUrl().toString();
                            mDatabaseref.child("image").setValue(download_uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        mprogbar.dismiss();
                                        Toast.makeText(SettingsActivity.this,"Success",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                        else{
                            mprogbar.hide();
                            Toast.makeText(SettingsActivity.this,"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }


}

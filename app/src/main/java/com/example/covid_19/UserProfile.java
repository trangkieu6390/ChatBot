package com.example.covid_19;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UserProfile extends AppCompatActivity {

    ImageView imgview;
    Uri FilePathUri;

    String _PASSWORD, _NAME , _EMAIL, _PHONENO;
    TextInputLayout fullName, email, phoneNo, password,username;
    TextView fullNameLabel, usernameLabel;

    DatabaseReference reference;
    StorageReference storageReference;
    int Image_Request_Code = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        reference= FirebaseDatabase.getInstance().getReference("user");
        storageReference = FirebaseStorage.getInstance().getReference("user");

        fullName = findViewById(R.id.FullNameProfile);
        email = findViewById(R.id.EmailProfile);
        phoneNo = findViewById(R.id.PhoneProfile);
        password = findViewById(R.id.PasswordProfile);
        fullNameLabel = findViewById(R.id.fullname_field);
        imgview = findViewById(R.id.imgProfile);

        showAllUserData();
    }

    public void showAllUserData() {
        Intent intent = getIntent();
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNo");
        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(_NAME);
        fullName.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phoneNo.getEditText().setText(_PHONENO);
        password.getEditText().setText(_PASSWORD);

    }

    public void updateProfile(View view){
        UpdateProfile();
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }

    public void UpdateProfile() {

        if (FilePathUri != null) {

            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String _fullname = fullName.getEditText().getText().toString().trim();
                            String _email = email.getEditText().getText().toString().trim();
                            String _phoneNo = phoneNo.getEditText().getText().toString().trim();
                            String _password = password.getEditText().getText().toString().trim();
                            String _image = taskSnapshot.getUploadSessionUri().toString();

                            Toast.makeText(getApplicationContext(), "Update Information Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            UserHelperClass imageUploadInfo = new UserHelperClass(_fullname,_email,_password,_phoneNo,_image);

                            reference.child(_phoneNo).setValue(imageUploadInfo);
                        }
                    });
        }
        else {
            Toast.makeText(UserProfile.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }
}
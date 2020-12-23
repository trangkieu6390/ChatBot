package com.example.covid_19;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_19.DashBoard.DashBoardActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;

    ImageView backBtn;
    Button login, nextfoget;
    TextView titleText;
    TextInputLayout password, phoneNumber;

    TextInputEditText phoneNumberEditText, passwordEditText;

    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backBtn = findViewById(R.id.btnLogin);
        login = findViewById(R.id.btnSignIn);
        titleText = findViewById(R.id.signup_title_text);

        phoneNumber = findViewById(R.id.LoginPhoneNumber);
        password = findViewById(R.id.LoginPassword);

        phoneNumberEditText = findViewById(R.id.edtLoginPhoneNumber);
        passwordEditText = findViewById(R.id.edtLoginPassword);
        rememberMe = findViewById(R.id.cbxRememberMe);

        nextfoget = findViewById(R.id.nextfoget);
        nextfoget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
            }
        });



        //check weather phone number and password is already saved in Shared preferences or not
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();
            phoneNumberEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

    }

    // check password
    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d^a-zA-Z0-9].{8,50}$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;

        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 8 characters including uppercase letters and numbers!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
    // check phone number
    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if(val.length()>10||val.length()<10){
            phoneNumber.setError("PhoneNo is too large or too short");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
    public void callScreen(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    // login account
    public void Login(View view){

        if(!isConnected(this)){
            //showCustomDialog();
            final ProgressDialog mDialog = new ProgressDialog(Login.this);
            mDialog.setMessage("Please connect internet ... ");
            mDialog.show();
        }else
        if (!validatePassword() | !validatePhoneNumber()) {
            return;
        }
        else {
            isUser();


        }
    }

    // Check connect internet
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifiConn!=null&&wifiConn.isConnected()||(mobileConn!=null&&mobileConn.isConnected())){
            return true;
        }
        else return false;
    }

    // Check user
    public void isUser(){
        String phoneNo = phoneNumber.getEditText().getText().toString().trim();
        final String password1 = password.getEditText().getText().toString().trim();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("user");

        final ProgressDialog mDialog = new ProgressDialog(Login.this);
        mDialog.setMessage("Please waiting... ");
        mDialog.show();

        if(rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(phoneNo,password1);
        }
        Query checkUser = table_user.orderByChild("phoneNo").equalTo(phoneNo);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDialog.dismiss();
                if (dataSnapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(phoneNo).child("password").getValue(String.class);

                    if (passwordFromDB.equals(password1)) {

                        phoneNumber.setError(null);
                        phoneNumber.setErrorEnabled(false);

                            String nameFromDB = dataSnapshot.child(phoneNo).child("name").getValue(String.class);
                            String usernameFromDB = dataSnapshot.child(phoneNo).child("username").getValue(String.class);
                            String phoneNoFromDB = dataSnapshot.child(phoneNo).child("phoneNo").getValue(String.class);
                            String emailFromDB = dataSnapshot.child(phoneNo).child("email").getValue(String.class);
                            String imageView = dataSnapshot.child(phoneNo).child("imageURL").getValue(String.class);

                            Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("username", usernameFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("phoneNo", phoneNoFromDB);
                            intent.putExtra("password", passwordFromDB);
                            intent.putExtra("imageURL", imageView);

                            //startActivity(intent);
                        Intent intent1 =new Intent(getApplicationContext(), DashBoardActivity.class);
                        startActivity(intent1);
                        finish();

                    } else {
                        mDialog.dismiss();
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    phoneNumber.setError("No such User exist");
                    phoneNumber.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
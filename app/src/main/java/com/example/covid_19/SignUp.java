package com.example.covid_19;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button register, login;
    TextView titleText;

    TextInputLayout fullName, username, email, password, phoneNumber;

    CountryCodePicker countryCodePicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backBtn = findViewById(R.id.btnBackSignUp);
        register = findViewById(R.id.btnSignUp);
        titleText = findViewById(R.id.signup_title_text);

        fullName = findViewById(R.id.SignupFullname);
        email = findViewById(R.id.SignupEmail);
        password = findViewById(R.id.SignupPassword);
        phoneNumber = findViewById(R.id.SignupPhoneNo);

    }
    public void callScreen(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else if(val.length()>50||val.length()<2){
            fullName.setError("FullName is too large or too short");
            return false;
        }
            else {
                fullName.setError(null);
                fullName.setErrorEnabled(false);
                return true;
            }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        //String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        String checkEmail = "[a-zA-Z0-9._-]+@+gmail+.+com+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
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
    public void callLoginScreen(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user= database.getReference("user");;
        ProgressDialog mDialog = new ProgressDialog(SignUp.this);

        final String name = fullName.getEditText().getText().toString();
        final String email1 = email.getEditText().getText().toString();
        final String phoneNo = phoneNumber.getEditText().getText().toString();
        final String password1 = password.getEditText().getText().toString();

        if (!validateFullName() | !validateEmail() | !validatePassword() | !validatePhoneNumber()) {
            return;
        }
        else {
            mDialog.setMessage("Please waiting... ");
            mDialog.show();
        }

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(phoneNo).exists()){
                    mDialog.dismiss();
                    Toast.makeText(SignUp.this,"Phone Number already register",Toast.LENGTH_SHORT).show();
                }
                else {
                    mDialog.dismiss();

                    UserHelperClass user = new UserHelperClass(name,email1,password1,phoneNo);
                    table_user.child(phoneNo).setValue(user);

                    Toast.makeText(SignUp.this,"Sign up successfully !! ",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent1);
                    //finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

//        Intent intent1 = new Intent(getApplicationContext(), Login.class);
//        //Add Shared Animation
//        Pair[] pairs = new Pair[4];
//        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
//        pairs[1] = new Pair<View, String>(register, "transition_register_btn");
//        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
//        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
//            startActivity(intent1, options.toBundle());
//        } else {
//            startActivity(intent1);
//        }
    }

}
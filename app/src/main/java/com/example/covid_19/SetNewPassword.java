package com.example.covid_19;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {


    Button next_btn;
    TextInputLayout password, confirm;
    ProgressBar progressBar;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        progressBar = findViewById(R.id.Progressbar);
        password = findViewById(R.id.NewPassword);
        confirm = findViewById(R.id.ConfirmPassword);
        next_btn = findViewById(R.id.next_btn);


    }

    public void setNewPass(View view){
        if (!validatePassword() | !validateConfirmPassword()) {
            return;
        }
        else if(password.getEditText().getText().toString().trim()==confirm.getEditText().getText().toString().trim()){
           // Toast.makeText(this, "password do not match",Toast.LENGTH_SHORT).show();
            confirm.setError("password do not match");
        }
        else {
            Log.d("123123", "onCreate: ");
            String _newPassword = password.getEditText().getText().toString().trim();
            String _phoneNumber = getIntent().getStringExtra("phoneNo");
            Log.d("123123", "setNewPass: "+_newPassword+" "+_phoneNumber);
            progressBar.setVisibility(View.VISIBLE);
            reference = FirebaseDatabase.getInstance().getReference("user");
            reference.child(_phoneNumber).child("password").setValue(_newPassword);
            startActivity(new Intent(getApplicationContext(), Login.class));
            Toast.makeText(this, "Change password done!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // check password
    private boolean validatePassword() {
        final String val = password.getEditText().getText().toString().trim();
        final String checkPassword ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d^a-zA-Z0-9].{8,50}$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;

        } else if (!val.matches(checkPassword)) {
            password.setError("Wrong Password!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String val = confirm.getEditText().getText().toString().trim();
        String checkPassword ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d^a-zA-Z0-9].{8,50}$";

        if (val.isEmpty()) {
            confirm.setError("Field can not be empty");
            return false;

        } else if (!val.matches(checkPassword)) {
            confirm.setError("Wrong Password!");
            return false;
        } else {
            confirm.setError(null);
            confirm.setErrorEnabled(false);
            return true;
        }
    }
    //check internet
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifiConn!=null&&wifiConn.isConnected()||(mobileConn!=null&&mobileConn.isConnected())){
            return true;
        }
        else return false;
    }


}
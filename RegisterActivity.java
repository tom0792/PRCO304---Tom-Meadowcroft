package com.example.parkhappy3;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends MainActivity {
    EditText UsernameEt, PasswordEt, PasswordEt2, AgeEt, EmailEt;
    private DrawerLayout drawer;


    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String password2 = PasswordEt2.getText().toString();
        String age = AgeEt.getText().toString();
        String email = EmailEt.getText().toString();
        String type = "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password, password2, age, email);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UsernameEt = (EditText) findViewById(R.id.etUserName);
        EmailEt = (EditText) findViewById(R.id.etEmail);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
        PasswordEt2 = (EditText) findViewById(R.id.etPassword2);
        AgeEt = (EditText) findViewById(R.id.etAge);

        final TextView txtVwBackPassword = (TextView) findViewById(R.id.txtVwBackPassword);

        txtVwBackPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(myIntent);
            }

        });

    }

}
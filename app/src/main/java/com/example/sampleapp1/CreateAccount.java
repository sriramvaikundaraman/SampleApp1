package com.example.sampleapp1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.support.v4.os.LocaleListCompat.create;

public class CreateAccount extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    EditText email,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
         email = (EditText) findViewById(R.id.email);
         pw = (EditText) findViewById(R.id.pw);
        Button button = (Button) findViewById(R.id.button);
        TextView tv = (TextView) findViewById(R.id.textView2);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createaccount();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


    }
    private void createaccount(){
        String emails=email.getText().toString();
        String pass=pw.getText().toString();
        if(TextUtils.isEmpty(emails))
        {
            Toast.makeText(getApplicationContext(),"Enter  a email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(getApplicationContext(),"Enter  a password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Creating account..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(emails,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Cannot create account",Toast.LENGTH_LONG).show();
                        return;
                    }
            }
        });
    }
}


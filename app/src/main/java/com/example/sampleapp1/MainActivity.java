package com.example.sampleapp1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //EditText editText;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuth;
    EditText email,password;
    TextView fp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         email=(EditText)findViewById(R.id.email);
         password=(EditText)findViewById(R.id.pw);
        Button button=(Button)findViewById(R.id.button);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        TextView tv=(TextView)findViewById(R.id.textView2);
        fp=(TextView)findViewById(R.id.fp);
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=email.getText().toString();
                if(TextUtils.isEmpty(em)){
                    Toast.makeText(getApplicationContext(),"Enter a mail",Toast.LENGTH_LONG).show();
                    return ;
                }
                progressDialog.setMessage("Sending reset mail...");
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Check mail...",Toast.LENGTH_LONG).show();

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"cannot proceed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),CreateAccount.class);
                startActivity(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        }

        public void login()
        {
            String emails=email.getText().toString();
            String pass=password.getText().toString();
            if(TextUtils.isEmpty(emails))
            {
                Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
                return;

            }
            if(TextUtils.isEmpty(pass))
            {
                Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
                return;
            }
            progressDialog.setMessage("Logging in..");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(emails,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        finish();
                        Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Welcome.class);
                        startActivity(i);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Cannot signin",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            });
        }

    }


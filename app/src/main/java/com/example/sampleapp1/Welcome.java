package com.example.sampleapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity {
    TextView username;
    EditText name,age;
    DatabaseReference databaseReference,user;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        username = (TextView)findViewById(R.id.username);
        name = (EditText)findViewById(R.id.editText);
        age = (EditText)findViewById(R.id.age);
        button =(Button)findViewById(R.id.submit);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("UserDetails");
        username.setText(user.getEmail());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=name.getText().toString();
                String ages=age.getText().toString();
                String id;
                if(!TextUtils.isEmpty(names)&&!TextUtils.isEmpty(ages))
                {
                    FirebaseUser currentFirebaseUser =FirebaseAuth.getInstance().getCurrentUser();
                    id=currentFirebaseUser.getUid();
                    AddDb id2=new AddDb(names,ages);
                    databaseReference.child(id).setValue(id2);
                    Toast.makeText(getApplicationContext(),"Data inserted ",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Fill all Fields",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
}

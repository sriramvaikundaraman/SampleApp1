package com.example.sampleapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity {
    TextView username;
    EditText name,age;
    DatabaseReference databaseReference,user;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    TextView n,a;
    @Override
    protected void onResume()
    {
        super.onResume();
        String id;
        n=(TextView)findViewById(R.id.textView5);
        a=(TextView)findViewById(R.id.textView6);
      //  Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
        FirebaseUser currentFirebaseUser =FirebaseAuth.getInstance().getCurrentUser();
        id=currentFirebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails/"+id+"/name");
         DatabaseReference myRef1=database.getReference("UserDetails/"+id+"/age");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               String value = dataSnapshot.getValue(String.class);
                n.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("SAMPLEAPP","error: "+error);
            }
        });

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                a.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("SAMPLEAPP","error: "+error);
            }
        });
    }

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

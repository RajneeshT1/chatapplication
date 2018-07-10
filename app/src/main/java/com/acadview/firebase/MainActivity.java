package com.acadview.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    Button senbtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference messageReference;

    ArrayList<String> messagedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        senbtn = findViewById(R.id.sendbtn);

        messagedata = new ArrayList<>();

        //1
        firebaseDatabase = FirebaseDatabase.getInstance();
       //2
        messageReference = firebaseDatabase.getReference("message");

        senbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userMessage = editText.getText().toString().trim();

                if(userMessage.length()==0){
                    return;
                }
                messageReference.setValue(userMessage);
                editText.setText("");
            }
        });
        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String receivedMessage = dataSnapshot.getValue(String.class);

                // step to set adapter
                messagedata.add(receivedMessage);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,messagedata);
                listView.setAdapter(adapter);


                Toast.makeText(MainActivity.this,receivedMessage,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

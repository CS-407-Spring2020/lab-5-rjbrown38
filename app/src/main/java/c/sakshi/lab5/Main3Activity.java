package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {

    int noteid=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = (EditText) findViewById(R.id.mytextfield);
        Intent intent = getIntent();
        int num = intent.getIntExtra("noteid", -1);
        noteid = num;

        if(noteid != -1){
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
    }

    public void saveMethod(View view){


    }


    public void onButtonClick(View view){
        EditText editText = (EditText) findViewById(R.id.mytextfield);
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1){
            title = "NOTE_" + (Main2Activity.notes.size() + 1);
            String content = editText.getText().toString();
            System.out.println(username + title + content + date);
            helper.saveNotes(username, title, content, date);
        } else{
            title = "NOTE_" + (noteid +1);
            Note note = Main2Activity.notes.get(noteid);
            String content = editText.getText().toString();
            helper.updateNotes(username, title, content, date);
        }

        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("message", username);
        startActivity(intent);
    }
}

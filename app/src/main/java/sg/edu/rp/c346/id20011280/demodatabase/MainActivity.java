package sg.edu.rp.c346.id20011280.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    Button btnInsert , btnGetTasks;

    TextView tvResults;

    EditText Desc;
    EditText Date;

    ListView LV;

    boolean asc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResult);
        Desc = findViewById(R.id.NameEdit);
        Date = findViewById(R.id.editDate);
        LV = findViewById(R.id.lv);

        ArrayList<Task> alTasks = new ArrayList<>();

        ArrayAdapter aaTasks = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,alTasks);
        LV.setAdapter(aaTasks);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DBHelper db = new DBHelper(MainActivity.this); // calling the database

                if(!Date.getText().toString().isEmpty() || !Desc.getText().toString().isEmpty())
                {
                    db.insertTask(Desc.getText().toString(),Date.getText().toString());
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please enter the fields",Toast.LENGTH_SHORT).show();;
                }


            }
        });

        asc = true;

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                ArrayList<String> data = db.getTaskContent();
                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i +". "+data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }

                tvResults.setText(txt);

                if(asc)
                {
                    asc = false;
                }
                else
                {
                    asc = true;
                }
                alTasks.clear(); // prevents stacking in one section

                alTasks.addAll(db.getTasks(asc));
                aaTasks.notifyDataSetChanged();

                db.close();

            }
        });
    }
}
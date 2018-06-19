package cs246.scripturememorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public Scripture scripture;
    public TextView scriptureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        scriptureView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scripture s = new Scripture();
                s.dateMemorized = new Date();
                s.lastReviewed = new Date();
                /*
                Start activity for result, packs up scripture and sends it off
                 */
                Intent intent = new Intent(MainActivity.this, testActivity.class);
                intent.putExtra("Scripture", s);
                startActivityForResult(intent, 2);
            }
        });
    }

    /*
    Handles the returned data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            scripture = data.getParcelableExtra("Scripture");
            scriptureView.setText(scripture.toString());
        }
        else
            Log.d("icecream", "result code not okay");
    }
}

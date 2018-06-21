package cs246.scripturememorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReciteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        Intent scripture = getIntent();

        //
//        String ref = scripture.getStringExtra(MainActivity.REFERENCE);
//        String verse = scripture.getStringExtra(MainActivity.VERSE);

        //Set the textView to the reference.
        TextView thisReference = findViewById(R.id.scriptureReference);
//        thisReference.setText(verse);
    }



}

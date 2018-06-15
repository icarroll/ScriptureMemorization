package cs246.scripturememorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //It works well!!



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final String REFERENCE = "";
    public static final String VERSE = "";


    public void sendScripture(View view) {
        //just a temporary method for testing the Intent to opening the recitation view
        //send the text to match, and the verse reference.
        Log.d("TEMP", "Sending reference and verse");

        String reference = "2 Nephi 2:24";
        String verse = "But behold, all things have been done in the wisdom of him who knoweth all things.";

        Intent reciteIntent = new Intent(this,ReciteActivity.class);
        reciteIntent.putExtra(REFERENCE,reference);
        reciteIntent.putExtra(VERSE,verse);

        startActivity(reciteIntent);
    }
    //commenting Look at my comment a thing.
}

package cs246.scripturememorization;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ReciteActivity extends AppCompatActivity {

    public boolean isRecording = false;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private final String tag = "ReciteActivity";

//    ArrayList<String> recitedWords;

    Scripture thisScripture; // = new Scripture();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        Intent scripture = getIntent();

        //This is here until we set the scripture coming in via the intent.
        //Note that this scripture (Genesis 2:24) has been cleaned of punctuation.
//        thisScripture.text = "therefore shall a man leave his father and his mother and shall cleave unto his wife and they shall be one flesh";

        //Set the textView to the reference.
        TextView thisReference = findViewById(R.id.scriptureReference);
//        thisReference.setText(verse);
    }

    public void microphoneButtonPress(View view) {
        Log.d("ReciteActivity","button pressed");
        startVoiceInput();
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the scripture from memory, slowly. Please enunciate.");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    public String[] splitSentence(String sentence) {

        String[] wordSet;
        wordSet = sentence.split(" ");
        return wordSet;
    }

    /**
     * compareRecitation Compares the recitation to the correct version. Attempts to do a little
     * fuzzy logic to favor the recitation.
     * @param scripture The actual contents of the scripture.
     * @param recited The recited version of the scripture.
     * @return Percentage of correct words.
     */
    private int compareRecitation(String scripture, String recited) {
        String[] scriptureArray = scripture.split(" ");
        String[] recitedArray = recited.split(" ");

        int accurateCount = 0;

        if(scriptureArray.length == recitedArray.length) {
            //Simple word comparison
            for(int s = 0; s < scriptureArray.length; s++) {
                if(scriptureArray[s].equals(recitedArray[s])) {
                    accurateCount++;
                }
            }
        }else {
            Log.d(tag,scriptureArray.length + " " + recitedArray.length);
        }

        float dec = (float)accurateCount/(float)scriptureArray.length;

//        Log.d(tag,Float.toString(dec));

        return Math.round(accurateCount/scriptureArray.length * 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d(tag,"success rate: " + compareRecitation(thisScripture.text, result.get(0)) + "% ");
                }
                break;
            }
        }
    }
}

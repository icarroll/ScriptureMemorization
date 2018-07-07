package cs246.scripturememorization;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class ReciteActivity extends AppCompatActivity {

    public boolean isRecording = false;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private final String tag = "ReciteActivity";

    Scripture thisScripture;
    public boolean hasRecited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        Intent intent = getIntent();

        thisScripture = intent.getExtras().getParcelable("Scripture");

        //Set the textView to the reference.
        TextView thisReference = findViewById(R.id.scriptureReference);
        thisReference.setText(thisScripture.book + " " + thisScripture.chapter + ":" + thisScripture.verse);
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

    public void setPostFieldVisibility(){
        TextView score = findViewById(R.id.postReciteScore);
        TextView heardStatic = findViewById(R.id.heardStatic);
        TextView heard = findViewById(R.id.postReciteText);
        Button saveScore = findViewById(R.id.saveScore);
        if(hasRecited) {
            score.setVisibility(View.VISIBLE);
            heardStatic.setVisibility(View.VISIBLE);
            heard.setVisibility(View.VISIBLE);
            saveScore.setVisibility(View.VISIBLE);
        }else{
            score.setVisibility(View.INVISIBLE);
            heardStatic.setVisibility(View.VISIBLE);
            heard.setVisibility(View.INVISIBLE);
            saveScore.setVisibility(View.INVISIBLE);
        }
    }

    public void updateAndReturn(View view) {
        Intent intent = new Intent ();
        intent.putExtra("Scripture", thisScripture);
        setResult(testActivity.RESULT_OK, intent);
        finish();
    }

    public void setRecitationText(String text) {
        TextView postReciteText = findViewById(R.id.postReciteText);
        postReciteText.setText(text);
    }

    public void setRecitationScore(String text) {
        TextView postReciteText = findViewById(R.id.postReciteScore);
        postReciteText.setText(text + "%");
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
//        Integer percentage = FuzzySearch.weightedRatio(scripture,recited);
        Integer percentage = FuzzySearch.ratio(scripture,recited);

        thisScripture.percentCorrect = percentage;
        setRecitationScore(Integer.toString(percentage));
        Log.d(tag,"Returned Simple Ratio of Accuracy:" + Integer.toString(percentage));

        return percentage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    compareRecitation(thisScripture.text, result.get(0));
                    setRecitationText(result.get(0));
                    hasRecited = true;
                    setPostFieldVisibility();
                }
                break;
            }
        }
    }
}

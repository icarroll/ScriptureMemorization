package cs246.scripturememorization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Scripture scripture;
    private List<Scripture> scriptureList;
    private TextView scriptureReference;
    private TextView scriptureText;
    private TextView scriptureLastReviewed;
    private TextView scriptureMemorized;
    private ImageView scriptureMemorizedSticker;
    private ArrayAdapter<Scripture> scriptureAdapter;
    private Gson _gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scriptureList = new ArrayList<>();
        scriptureReference = findViewById(R.id.text_scriptureName);
        scriptureText = findViewById(R.id.text_ScriptureText);
        scriptureLastReviewed = findViewById(R.id.text_lastReviewed);
        scriptureMemorized = findViewById(R.id.text_memorized);
        scriptureMemorizedSticker = findViewById(R.id.image_Memorized);
        scriptureAdapter = new ArrayAdapter<Scripture>(this, android.R.layout.simple_list_item_1, scriptureList);
        ListView list = findViewById(R.id.list_scriptures);
        list.setAdapter(scriptureAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Scripture temp = scripture;
                scripture = scriptureList.get(position);
                scriptureList.set(position, temp);
                scriptureAdapter.notifyDataSetChanged();
                //scriptureAdapter.remove(scripture);
                //scriptureAdapter.add(temp);
                updateScriptureView();
            }
        });
        _gson = new Gson();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        int sCount = pref.getInt("Scripture_Count", 0);
        if (sCount > 0)
            scripture = _gson.fromJson(pref.getString("s_" + 0, null ), Scripture.class);
        for (int i = 1; i < sCount; i++)
        {
            Scripture s = _gson.fromJson(pref.getString("s_" + i, null ), Scripture.class);
            if (s != null)
                scriptureAdapter.add(s);
        }

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (scripture != null) {
                    if (scripture.memorized) {
                        scripture.memorized = false;
                        scripture.dateMemorized = null;
                    }
                    else {
                        scripture.memorized = true;
                        scripture.lastReviewed = new Date();
                        scripture.dateMemorized = new Date();
                    }
                    updateScriptureView();
                    Log.d("icecream", "button pushed");
                }
            }
        });

        Button buttonNew = findViewById(R.id.button3);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (scripture != null) {
                    scripture.lastReviewed = new Date();
                    updateScriptureView();
                    Log.d("icecream", "button2 pushed");
                }
            }
        });
        updateScriptureView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.add(0, 0, 0, "Add a new Scripture to your list");
        menu.add(0, 1, 1, "Remove Current Scripture from your list");
        menu.add(0, 2, 2, "Recite Current Scripture");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case 0:
                getScripture();
                return true;
            case 1:
                removeScripture();
                return true;
            case 2:
                reciteScripture();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getScripture() {
        Intent intent = new Intent(MainActivity.this, testActivity.class);
        startActivityForResult(intent, 0);
    }

    private void removeScripture() {
        if (scripture == null) {
            Toast.makeText(MainActivity.this, "No scripture to remove", Toast.LENGTH_LONG).show();
            return;
        }
        if (!scriptureList.isEmpty()) {
            scripture = scriptureList.get(0);
            scriptureAdapter.remove(scripture);
            updateScriptureView();
        } else {
            scripture = null;
            updateScriptureView();
        }
    }

    private void reciteScripture() {
        if (scripture == null) {
            Toast.makeText(MainActivity.this, "Please add a scripture to recite", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, ReciteActivity.class);
        intent.putExtra("Scripture", scripture);
        startActivityForResult(intent, 1);
    }

    /*
    Handles the returned data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Scripture s = data.getParcelableExtra("Scripture");
            if (scripture == null) {
                scripture = s;
            } else {
                scriptureAdapter.add(scripture);
                scripture = s;
            }
            updateScriptureView();
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            scripture = data.getParcelableExtra("Scripture");
            updateScriptureView();
        } else {
            Log.d("icecream", "result code not okay");
        }
    }


    private void updateScriptureView() {
        if (scripture == null) {
            scriptureReference.setText(getString(R.string.main_ScriptureDefaultText));
            scriptureText.setVisibility(View.INVISIBLE);
            scriptureLastReviewed.setVisibility(View.INVISIBLE);
            scriptureMemorized.setVisibility(View.INVISIBLE);
            scriptureMemorizedSticker.setVisibility(View.INVISIBLE);
        }
        else {
            scriptureReference.setText(String.format("%s %s:%s", scripture.book, scripture.chapter, scripture.verse));
            scriptureText.setVisibility(View.VISIBLE);
            scriptureText.setText(scripture.text);
            if (scripture.lastReviewed != null) {
                scriptureLastReviewed.setVisibility(View.VISIBLE);
                scriptureLastReviewed.setText(String.format(Locale.ENGLISH,
                        "Last Reviewed: %s %d, %d",
                        getMonth(scripture.lastReviewed.getMonth()),
                        scripture.lastReviewed.getDay(),
                        scripture.lastReviewed.getYear()));
            }
            else {
                scriptureLastReviewed.setVisibility(View.INVISIBLE);
            }
            if (scripture.dateMemorized != null) {
                scriptureMemorized.setVisibility(View.VISIBLE);
                scriptureMemorized.setText(String.format(Locale.ENGLISH,
                        "Memorized: %s %d, %d",
                        getMonth(scripture.dateMemorized.getMonth()),
                        scripture.dateMemorized.getDay(),
                        scripture.dateMemorized.getYear()));
            }
            else {
                scriptureMemorized.setVisibility(View.INVISIBLE);
            }
            if (scripture.memorized) {
                scriptureMemorizedSticker.setVisibility(View.VISIBLE);
            }
            else {
                scriptureMemorizedSticker.setVisibility(View.INVISIBLE);
                scriptureMemorized.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (scripture != null) {
            editor.putInt("Scripture_Count", 1 + scriptureList.size());
            editor.putString("s_0", _gson.toJson(scripture));
            for (int i = 0; i < scriptureList.size(); i++) {
                editor.putString("s_" + i, _gson.toJson(scriptureList.get(i)));
            }
        }
        else
            editor.putInt("Scripture_Count", 0);
        editor.commit();
    }

    private String getMonth(int month)
    {
        switch (month) {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "July";
            default:
                return "Aug";
        }
    }
}

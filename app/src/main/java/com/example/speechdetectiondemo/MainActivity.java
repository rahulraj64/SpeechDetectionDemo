package com.example.speechdetectiondemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SPEECH_RECOGNITION_CODE = 1;

    EditText write;
    Button speak;

    LevenshteinDistance levenshteinDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        write = (EditText) findViewById(R.id.write);
        speak = (Button) findViewById(R.id.speak);
        speak.setOnClickListener(this);
        levenshteinDistance = new LevenshteinDistance();
    }

    @Override
    public void onClick(View view) {
        startSpeechToText();
    }


    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak ..");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    int accuracy = levenshteinDistance.apply(text, write.getText().toString()); // 0 for highest accuracy
                    String accuracyText = getAccuracyText(accuracy);
                    Toast.makeText(this, "Accuracy value : " + accuracy + " [ " + accuracyText + " ] ", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private String getAccuracyText(int accuracy) {
        String accuracyText;
        if(accuracy <= 1) accuracyText = "High";
        else if(accuracy > 1 && accuracy <= 4 ) accuracyText = "Medium";
        else accuracyText = "Low";
        return accuracyText;
    }

}
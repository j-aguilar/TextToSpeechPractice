package com.example.texttospeechpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private Button btn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.tts_btn);
        editText = findViewById(R.id.edit_text);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = tts.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_MISSING_DATA){
                        Log.e("TTS", "Language not supported");
                    } else {
                        Log.i("TTS", "Language is supported");
                    }
                    Log.i("TTS", "Initialization success");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization Fail!", Toast.LENGTH_SHORT);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                Log.i("TTS", "Converting... " + data);
                int speechStatus = tts.speak(data, TextToSpeech.QUEUE_FLUSH, null);

                if (speechStatus == TextToSpeech.ERROR){
                    Log.e("TTS", "Error in converting Text to Speech");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null){
            tts.stop();
            tts.shutdown();
        }
    }
}

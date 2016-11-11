package com.example.codetribe.miwok;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class NumberActivity extends AppCompatActivity {
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();


        words.add(new Word(getResources().getString(R.string.one), "lutti", R.drawable.cute_number_one));
        words.add(new Word(getResources().getString(R.string.two), "otiiko", R.drawable.cut_number_two));
        words.add(new Word(getResources().getString(R.string.three), "tolookosu", R.drawable.cute_number_three));
        words.add(new Word(getResources().getString(R.string.four), "oyyisa", R.drawable.cute_number_four));
        words.add(new Word(getResources().getString(R.string.five), "massokka", R.drawable.cute_number_five));
        words.add(new Word(getResources().getString(R.string.six), "temmokka", R.drawable.cute_number_six));
        words.add(new Word(getResources().getString(R.string.seven), "kenekaku", R.drawable.cute_number_seven));
        words.add(new Word(getResources().getString(R.string.eight), "kawinta", R.drawable.cute_number_eight));
        words.add(new Word(getResources().getString(R.string.nine), "wo'e", R.drawable.cute_number_nine));
        words.add(new Word(getResources().getString(R.string.zero), "na,aacha", R.drawable.cute_number_zero));

        WordAdopter wordAdopter = new WordAdopter(this, words, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(wordAdopter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word w = (Word) adapterView.getItemAtPosition(i);
                speakWord(w.getDefaultTranslation());

            }
        });
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Initilization failed", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void speakWord(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}

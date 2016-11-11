package com.example.codetribe.miwok;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class FamilyActivity extends AppCompatActivity {
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(getResources().getString(R.string.grandfather),"wetetti",R.drawable.grandpa));
        words.add(new Word(getResources().getString(R.string.grandmother),"chiwiita",R.drawable.grandmother));
        words.add(new Word(getResources().getString(R.string.father),"topiia",R.drawable.father));
        words.add(new Word(getResources().getString(R.string.mother),"chokokki",R.drawable.mother));
        words.add(new Word(getResources().getString(R.string.brother),"takaakki",R.drawable.brother));
        words.add(new Word(getResources().getString(R.string.sister),"topoppi",R.drawable.sister));
        words.add(new Word(getResources().getString(R.string.uncle),"kululli",R.drawable.uncle));
        words.add(new Word(getResources().getString(R.string.aunty),"kelelli",R.drawable.aunt));


        WordAdopter wordAdopter = new WordAdopter(this,words,R.color.category_family);
        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(wordAdopter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word w = (Word)adapterView.getItemAtPosition(i);
                speakWord(w.getDefaultTranslation());

            }
        });
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    int result = tts.setLanguage(Locale.US );
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Toast.makeText(getApplicationContext(),"Language not supported",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Initilization failed",Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public void speakWord(String text){
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
    @Override
    protected void onPause() {
        if(tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
    }


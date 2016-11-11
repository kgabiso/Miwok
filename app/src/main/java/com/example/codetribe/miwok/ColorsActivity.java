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

public class ColorsActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private int data_check = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(getResources().getString(R.string.red),"wetetti",R.drawable.red));
        words.add(new Word(getResources().getString(R.string.mustard_yellow),"chiwiita",R.drawable.mustard_yellow));
        words.add(new Word(getResources().getString(R.string.dusty_yellow),"topiia",R.drawable.dusty_yellow));
        words.add(new Word(getResources().getString(R.string.green),"chokokki",R.drawable.green));
        words.add(new Word(getResources().getString(R.string.brown),"takaakki",R.drawable.brown));
        words.add(new Word(getResources().getString(R.string.gray),"topoppi",R.drawable.gray));
        words.add(new Word(getResources().getString(R.string.black),"kululli",R.drawable.black));
        words.add(new Word(getResources().getString(R.string.white),"kelelli",R.drawable.white));


        WordAdopter wordAdopter = new WordAdopter(this,words,R.color.category_colors);
        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(wordAdopter);
        listView.setClickable(true);
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
                   int result = tts.setLanguage(Locale.US);
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

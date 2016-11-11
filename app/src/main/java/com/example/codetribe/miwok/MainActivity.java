package com.example.codetribe.miwok;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvColor, tvFamily, tvNumber, tvPhrase;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = settings.getString("LANG", "");
      if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

      }
        tvColor = (TextView) findViewById(R.id.colors);
        tvFamily = (TextView) findViewById(R.id.family);
        tvNumber = (TextView) findViewById(R.id.numbers);
        tvPhrase = (TextView) findViewById(R.id.phrases);

        tvColor.setText(getResources().getString(R.string.category_colors));
        tvPhrase.setText(getResources().getString(R.string.category_phrases));
        tvFamily.setText(getResources().getString(R.string.category_family));
        tvNumber.setText(getResources().getString(R.string.category_numbers));

        tvColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), ColorsActivity.class);
                startActivity(i);
                speakWord(tvColor.getText().toString());
            }
        });
        tvFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), FamilyActivity.class);
                startActivity(i);
                speakWord(tvFamily.getText().toString());
            }
        });
        tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), NumberActivity.class);
                startActivity(i);
                speakWord(tvNumber.getText().toString());
            }
        });
        tvPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), PhrasesActivity.class);
                startActivity(i);
                speakWord(tvPhrase.getText().toString());
            }
        });
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    int result = tts.setLanguage(Locale.US    );
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
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();

            switch (id) {
                case R.id.action_change_language:
                    showChangeLangDialog();
                    return true;

                //similarly write for other actions

                default:
                    return super.onOptionsItemSelected(item);
            }
            //return true;
        }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.language_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

        dialogBuilder.setTitle(getResources().getString(R.string.lang_dialog_title));
        dialogBuilder.setMessage(getResources().getString(R.string.lang_dialog_message));
        dialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                switch (langpos) {
                    case 0: //English
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
                        setLangRecreate("en");
                        return;
                    case 1: //zulu
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "zu").commit();
                        setLangRecreate("zu");
                        return;
                    case 2://sotho
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "nso").commit();
                        setLangRecreate("nso");
                        return;
                    default: //By default set to english
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
                        setLangRecreate("en");
                        return;
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
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


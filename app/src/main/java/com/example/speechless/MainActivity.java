package com.example.speechless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
private TextToSpeech mtts;
private EditText medittext;
private SeekBar mseekpitch;
private SeekBar mseekspeed;
private Button mbtnspeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtnspeak=findViewById(R.id.button);
        mtts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {

                    int result=mtts.setLanguage(Locale.ENGLISH);
                    if(result== TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.e("TTS","LANGUAGE NOT SUPPORTED");
                    }
                    else
                    {
                        mbtnspeak.setEnabled(true);
                    }
                }
                else
                {
                    Log.e("TTS","INITIALIZATION FAILED");
                }
            }
        });

    medittext=findViewById(R.id.edit_text);
    mseekpitch=findViewById(R.id.seek_scale);
    mseekspeed=findViewById(R.id.seek_speed);
    mbtnspeak.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            speak();
        }
    });


    }




    private void speak()
    {
        String text=medittext.getText().toString();
        float pitch=(float)mseekpitch.getProgress()/50;
        if(pitch<0.1) pitch=0.1f;



        float speed=(float)mseekspeed.getProgress()/50;
        if(speed<0.1) speed=0.1f;
mtts.setPitch(pitch);
mtts.setSpeechRate(speed);
mtts.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }


    @Override
    protected void onDestroy() {
        if(mtts!=null)
        {

            mtts.stop();
            mtts.shutdown();
        }



        super.onDestroy();
    }
}
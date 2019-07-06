package com.example.dineth.mymp3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Play_song extends AppCompatActivity {

    MediaPlayer mp;
    ArrayList<File> mySongs;
    private Button play,pause,stop;
    private SeekBar sk;
    private ImageButton list;
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        play=(Button)findViewById(R.id.btn_play);
        pause=(Button)findViewById(R.id.btn_pause);
        stop=(Button)findViewById(R.id.btn_stop);
        list=(ImageButton)findViewById(R.id.btn_list);
        sk=(SeekBar)findViewById(R.id.seekbar);

        Intent a=getIntent();
        Bundle b=a.getExtras();
        mySongs =(ArrayList)b.getParcelableArrayList("songlist");
        int position=b.getInt("pos",0);

        handler=new Handler();

        Uri u=Uri.parse(mySongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        sk.setMax(mp.getDuration());
        //mp.start();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    toast("Song is Playing");
                }else {

                    mp.start();
                    try {
                        changeSeekBar();
                    }catch (Exception e){
                        toast("no moving");
                    }
                }
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    mp.pause();
                }else{
                    toast("Play the Song first");
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    mp.stop();
                    sk.setProgress(0);
                }else{
                    toast("Play the song First");
                }
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ac=new Intent(Play_song.this,MainActivity.class);
                startActivity(ac);
            }
        });

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mp.seekTo(i);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    private void changeSeekBar(){
        sk.setProgress(mp.getCurrentPosition());

        if(mp.isPlaying()){
            runnable=new Runnable(){
                @Override
                public void run(){
                    changeSeekBar();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }
}

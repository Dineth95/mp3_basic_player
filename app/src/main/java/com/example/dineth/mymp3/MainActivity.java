package com.example.dineth.mymp3;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private String[] items;
    //private ImageButton path;
    //private static final int REQUEST_CODE=40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=(ListView)findViewById(R.id.listview);

        final ArrayList<File> mysongs=findsongs(Environment.getExternalStorageDirectory());

        items=new String[mysongs.size()];
        for(int i=0;i<mysongs.size();i++){

            items[i]=mysongs.get(i).toString().replace(".mp3","").replace("wav","");
        }
        ArrayAdapter<String> adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.songlist_layout,R.id.textview,items);
        list.setAdapter(adp);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    startActivity(new Intent(getApplicationContext(), Play_song.class).putExtra("pos",i).putExtra("songlist",mysongs)  );
                }catch (Exception e){
                    toast(e.toString());
                }
            }
        });
    }


    public ArrayList<File> findsongs(File root){
        ArrayList<File> a1=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File singleFile:files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                a1.addAll(findsongs(singleFile));
            }else {
                if(singleFile.getName().endsWith((".mp3")) || singleFile.getName().endsWith(".wav")){
                    a1.add(singleFile);
                }
            }
        }
        return a1;
    }


    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }


}

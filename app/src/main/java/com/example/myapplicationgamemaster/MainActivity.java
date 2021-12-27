package com.example.myapplicationgamemaster;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            // clear shared P
            getSharedPreferences("userShared",MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
            startActivity(new Intent(MainActivity.this,Splash_Screen.class));
            finish();

        });

        videoView=findViewById(R.id.Videoview);
        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.assassincreednew);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }
    @Override
    protected void onPostResume() {
        videoView.resume();
        super.onPostResume();
    }
    @Override
    protected void onRestart(){
        videoView.start();
        super.onRestart();
    }
    @Override
    protected void onPause(){
        videoView.suspend();
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        videoView.stopPlayback();
        super.onDestroy();
    }
}
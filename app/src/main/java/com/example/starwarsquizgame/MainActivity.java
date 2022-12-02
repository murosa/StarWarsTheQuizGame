package com.example.starwarsquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MusicStoppedListener {

    Button btnPlay, btnHighScore, btnOptions, btnBack2, btnMusicToggle, btnChangeMusic, btnResetScore;
    LinearLayout vllMain, vllOptions;
    String[] audioLinks = {"https://dl.dropboxusercontent.com/s/pn92cxt5f6rlayu/Anakin%20vs.%20Obi-Wan.mp3?dl=0",
    "https://dl.dropboxusercontent.com/s/uk47mi75qr27a2w/Anakin_s%20Betrayal.mp3?dl=0",
    "https://dl.dropboxusercontent.com/s/79al52dpqfp2vqv/Battle%20of%20the%20Heroes.webm?dl=0",
    "https://dl.dropboxusercontent.com/s/nexhugbvoi8etfh/Lapti%20Nek%20%28Jabba_s%20Palace%20Band%29.mp3?dl=0",
    "https://dl.dropboxusercontent.com/s/d0kthggy2gi97ff/Star%20Wars%20Main%20Theme%20%28Full%29.mp3?dl=0",
    "https://dl.dropboxusercontent.com/s/bspv692sbngfkey/Star%20Wars%20VI%20-%20Jedi%20Rocks.mp3?dl=0",
    "https://dl.dropboxusercontent.com/s/mmzdvoimlkc66lh/Star%20Wars-%20The%20Imperial%20March%20%28Darth%20Vader_s%20Theme%29.mp3?dl=0",
    "https://dl.dropboxusercontent.com/s/pqyf5ycgnjk79kl/The%20Emperor.mp3?dl=0"};
    int previousSong = -1;
    boolean stopPlaying = false;
    Intent serviceIntent;
    public static final String MY_PREFS_FILENAME = "com.example.starwarsquizgame.Options";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnHighScore = findViewById(R.id.btnHighScore);
        btnOptions = findViewById(R.id.btnOptions);
        btnBack2 = findViewById(R.id.btnBack2);
        btnMusicToggle = findViewById(R.id.btnMusicToggle);
        btnChangeMusic = findViewById(R.id.btnChangeMusic);
        btnResetScore = findViewById(R.id.btnResetScore);

        vllMain = findViewById(R.id.vllMain);
        vllOptions = findViewById(R.id.vllOptions);

        serviceIntent = new Intent(this, MyPlayService.class);

        ApplicationClass.context = (Context) MainActivity.this;

        File file = getApplicationContext().getFileStreamPath("HighScore.txt");

        if (file.exists() == false)
        {
            setInitialHighScore();
        }

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE);
        stopPlaying = prefs.getBoolean("music_toggle", false);

        if(!stopPlaying)
        {
            playAudio();
        }
        else
        {
            Toast.makeText(this, "Music is toggled off!", Toast.LENGTH_SHORT).show();
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuizGame.class));
            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HighScore.class));
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vllMain.setVisibility(View.GONE);
                vllOptions.setVisibility(View.VISIBLE);
            }
        });

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vllOptions.setVisibility(View.GONE);
                vllMain.setVisibility(View.VISIBLE);
            }
        });

        btnMusicToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE).edit();
                editor.putBoolean("music_toggle", stopPlaying);
                editor.commit();
            }
        });

        btnChangeMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAudio();
            }
        });

        btnResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInitialHighScore();
            }
        });
    }

    // stops the service that is used to play the music
    private void stopPlayService() {

        try {
            stopService(serviceIntent);
        } catch (SecurityException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // Plays a song depending on the link returned from chooseAudio.
    private void playAudio() {

        int chosenSong = -1;

        chosenSong = chooseAudio();

        previousSong = chosenSong;

        String audioLink = audioLinks[chosenSong];

        serviceIntent.putExtra("AudioLink", audioLink);

        try {
            startService(serviceIntent);
        } catch (SecurityException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // used to either call the stopPlayService or playAudio methods depending on the stopPlaying boolean
    private void stopAudio() {

        if (!stopPlaying) {
            stopPlaying = true;
            stopPlayService();
            Toast.makeText(this, "Music turned off!", Toast.LENGTH_SHORT).show();
        } else {
            stopPlaying = false;
            playAudio();
            Toast.makeText(this, "Music turned on!", Toast.LENGTH_SHORT).show();
        }

    }

    // calls the playAudio method to change the song
    private void changeAudio()
    {
        if(stopPlaying == true)
        {
            stopPlaying = false;
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE).edit();
            editor.putBoolean("music_toggle", stopPlaying);
            editor.commit();
        }

        playAudio();
        Toast.makeText(this, "Music has been changed!", Toast.LENGTH_SHORT).show();
    }

    // returns an int that represents the index of the selected song. This method will
    // call itself again if the song that played before was selected again.
    private int chooseAudio()
    {
        Random r = new Random();
        int chosenSong = -1;
        chosenSong = r.nextInt(audioLinks.length - 0);

        if(chosenSong == previousSong)
        {
            chosenSong = chooseAudio();
        }

        return chosenSong;
    }

    // this method will run when the current song that is playing is finished. The playAudio method
    // will be called so another song can be played.
    @Override
    public void onMusicStopped() {

        if(!stopPlaying)
        {
            playAudio();
        }

    }

    // this method is used to set the initial high scores if there are none
    public void setInitialHighScore()
    {
        String[] names = {"R2D2", "Luke", "Leia", "Ani", "Palp", "Baca", "Solo", "Boba", "Jaba", "Gredo"};
        int[] scores = {1000, 900, 900, 800, 800, 700, 600, 500, 500, 300, 200};

        try
        {
            FileOutputStream file = openFileOutput("HighScore.txt", MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(file);

            for(int i = 0 ; i < names.length ; i++)
            {
                outputFile.write(names[i] + "," + scores[i] + "\n");
            }

            outputFile.flush();
            outputFile.close();
        }

        catch(IOException e)
        {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
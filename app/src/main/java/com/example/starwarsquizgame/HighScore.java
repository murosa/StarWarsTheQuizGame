package com.example.starwarsquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class HighScore extends AppCompatActivity {

    Button btnBack;
    TextView tvName, tvHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        btnBack = findViewById(R.id.btnBack);

        tvName = findViewById(R.id.tvName);
        tvHighScore = findViewById(R.id.tvHighScore);

        File file = getApplicationContext().getFileStreamPath("HighScore.txt");

        if (file.exists() == false)
        {
            Toast.makeText(this, "The high scores text document has not been created!", Toast.LENGTH_SHORT).show();
        }

        else if(file.exists() == true)
        {
            loadHighScore();
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //this method will be used to load the high scores from the high scores text document
    private void loadHighScore()
    {
        String lineFromFile;

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("HighScore.txt")));

            String name = "Name\n";
            String score = "Score\n";
            while ((lineFromFile = reader.readLine()) != null)
            {
                StringTokenizer tokens = new StringTokenizer(lineFromFile, ",");
                name += "\n" + tokens.nextToken();
                score += "\n" + tokens.nextToken();
            }

            reader.close();

            tvName.setText(name);
            tvHighScore.setText(score);
        }

        catch (IOException e)
        {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
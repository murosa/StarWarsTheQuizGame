package com.example.starwarsquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class QuizGame extends AppCompatActivity {

    Questions questions = new Questions();
    ImageView ivGame;
    TextView tvQuestion, tvScoreView, tvScoreView2;
    EditText etName;
    Button btnEnter, btnEnterName, btnExit;
    RadioButton btnOption1, btnOption2, btnOption3, btnOption4;
    RadioGroup rgAnswers;
    LinearLayout vllGame, vllHighScore, vllGameOver;
    String name = "";
    int score = 0;
    int answerPicked = 0;
    int correctAnswer;
    int questionNumber = 0;
    ArrayList<Integer> questionsAvailable = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        ivGame = findViewById(R.id.ivGame);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvScoreView = findViewById(R.id.tvScoreView);
        tvScoreView2 = findViewById(R.id.tvScoreView2);
        etName = findViewById(R.id.etName);
        btnEnter = findViewById(R.id.btnEnter);
        btnEnterName = findViewById(R.id.btnEnterName);
        btnExit = findViewById(R.id.btnExit);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        rgAnswers = findViewById(R.id.rgAnswers);
        vllGame = findViewById(R.id.vllGame);
        vllHighScore = findViewById(R.id.vllHighScore);
        vllGameOver = findViewById(R.id.vllGameOver);

        getAvailableQuestions();
        loadQuestion();

        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                answerPicked = 1;
            }
        });

        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                answerPicked = 2;
            }
        });

        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                answerPicked = 3;
            }
        });

        btnOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                answerPicked = 4;
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEnter.setClickable(false);
                checkAnswer();
                rgAnswers.clearCheck();
            }
        });

        btnEnterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty() == false)
                {
                    name = etName.getText().toString().trim();

                    loadHighScore();
                    finish();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // this method is used to both check the answer the user selected and call the loadQuestion function
    private void checkAnswer()
    {
        if(answerPicked != 0)
        {
            if(answerPicked == correctAnswer)
            {
                score += 100;
                Toast.makeText(this, "Answer is correct!", Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(this, "Answer is incorrect!", Toast.LENGTH_SHORT).show();
            }

            answerPicked = 0;
            loadQuestion();
        }

        //used if the answer did not select an answer
        else
        {
            Toast.makeText(this, "Pick an answer!", Toast.LENGTH_SHORT).show();
            btnEnter.setClickable(true);
        }
    }


    private void loadQuestion()
    {
        //if the question number is less than 10 then another question is selected and displayed to the user
        if(questionNumber < 10)
        {
            Random r = new Random();
            int chosenIndex;
            chosenIndex = r.nextInt(questionsAvailable.size() - 0);
            int chosenQues = 0;
            chosenQues = questionsAvailable.get(chosenIndex);

            tvQuestion.setText(questions.getQuestion(chosenQues));
            btnOption1.setText(questions.getAnswer1(chosenQues));
            btnOption2.setText(questions.getAnswer2(chosenQues));
            btnOption3.setText(questions.getAnswer3(chosenQues));
            btnOption4.setText(questions.getAnswer4(chosenQues));
            correctAnswer = questions.getCorrectAnswer(chosenQues);
            questionsAvailable.remove(chosenIndex);
            questionNumber++;

            btnEnter.setClickable(true);
        }

        //if questionNumber is not less than 10 then a check is done to determine whether the user got
        //a highscore.
        else
        {
            boolean highScore = false;

            highScore = checkHighScores();

            ivGame.setVisibility(View.GONE);
            vllGame.setVisibility(View.GONE);

            if(!highScore)
            {
                tvScoreView2.setText("Your score was " + score + "!");
                vllGameOver.setVisibility(View.VISIBLE);
            }

            else
            {
                tvScoreView.setText("Your score was " + score + "!");
                vllHighScore.setVisibility(View.VISIBLE);
            }
        }
    }

    // this method is supposed to run when this activity is opened. This method is used to determine
    // how many questions are available to be picked and then store numbers in a list that represent
    // the indexes of the questions in the Questions class
    private void getAvailableQuestions()
    {
        int ques = questions.getQuestionListSize();

        for(int i = 0 ; i < ques ; i++)
        {
            questionsAvailable.add(i);
        }
    }

    // this method is used to read and retrieve the high score values stored in the high score
    // text document and compared them to the users score. It will return a true or false value
    // depending on if the user's score is higher than any of the previous high scores.
    public boolean checkHighScores()
    {
        int[] scores = new int[10];

        String lineFromFile;

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("HighScore.txt")));
            int i = 0;
            while ((lineFromFile = reader.readLine()) != null)
            {
                StringTokenizer tokens = new StringTokenizer(lineFromFile, ",");
                tokens.nextToken();
                scores[i] = Integer.parseInt(tokens.nextToken()) ;

                if(i < scores.length)
                {
                    i++;
                }
            }

            reader.close();
        }

        catch (IOException e)
        {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        for(int i = 0 ; i < scores.length ; i++)
        {
            if(score > scores[i])
            {
                return true;
            }
        }

        return false;
    }

    // retrieves and stores the names and scores stored in the high score text document and
    // passes them to the saveScore method
    private void loadHighScore()
    {
        String[] names = new String[10];
        int[] scores = new int[10];

        String lineFromFile;

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("HighScore.txt")));
            int i = 0;
            while ((lineFromFile = reader.readLine()) != null)
            {
                StringTokenizer tokens = new StringTokenizer(lineFromFile, ",");
                names[i] = tokens.nextToken();
                scores[i] = Integer.parseInt(tokens.nextToken()) ;

                if(i < names.length)
                {
                    i++;
                }
            }

            reader.close();
        }

        catch (IOException e)
        {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        saveScore(names, scores);
    }

    // this method is used to create two arrays that will contain names and scores and will store the
    // values in both arrays to the high score text document. It will also organize the user's name and
    // and score in both array's.
    private void saveScore(String[] oldNames, int[] oldScores)
    {
        String[] newNames = new String[10];
        int[] newScores = new int[10];

        boolean newScoreSet = false;

        for(int i = 0 ; i < newNames.length - 1 ; i++)
        {
            if(!newScoreSet)
            {
                if(score >= oldScores[i])
                {
                    newNames[i] = name;
                    newScores[i] = score;

                    newNames[i + 1] = oldNames[i];
                    newScores[i + 1] = oldScores[i];

                    newScoreSet = true;
                }

                else
                {
                    newNames[i] = oldNames[i];
                    newScores[i] = oldScores[i];
                }
            }

            else
            {
                newNames[i + 1] = oldNames[i];
                newScores[i + 1] = oldScores[i];
            }
        }

        try
        {
            FileOutputStream file = openFileOutput("HighScore.txt", MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(file);

            for(int i = 0 ; i < newNames.length ; i++)
            {
                outputFile.write(newNames[i] + "," + newScores[i] + "\n");
            }

            outputFile.flush();
            outputFile.close();
        }

        catch(IOException e)
        {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
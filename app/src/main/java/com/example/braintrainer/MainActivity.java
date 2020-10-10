package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button nowButton;
    TextView nowTextView;
    TextView sumTextView;
    TextView timerTextView;
    ArrayList<Integer> answers;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    int correctAnswerLocation;
    TextView resultTextView;
    TextView scoreTextView;
    int score = 0;
    int total = 0;
    ConstraintLayout gameLayout;
    Button playAgainButton;
    MediaPlayer mediaPlayer;

    public void nowButton(View view)
    {
        nowButton.setVisibility(View.INVISIBLE);
        nowTextView.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound);
        mediaPlayer.start();
    }

    public void newQuestion()
    {
        answers.clear();
        Random random = new Random();
        int first = random.nextInt(100);
        int second = random.nextInt(100);
        int correctAnswer = first + second;
        correctAnswerLocation = random.nextInt(4);

        sumTextView.setText(Integer.toString(first) + " + " + Integer.toString(second));

        for (int i=0; i<4; i++)
        {
            if(correctAnswerLocation == i)
            {
                answers.add(correctAnswer);
            }
            else
            {
                int wrongAnswer = random.nextInt(200);
                while(wrongAnswer == correctAnswer)
                {
                    wrongAnswer = random.nextInt(200);
                }
                answers.add(wrongAnswer);
            }

        }
        button0.setText(answers.get(0).toString());
        button1.setText(answers.get(1).toString());
        button2.setText(answers.get(2).toString());
        button3.setText(answers.get(3).toString());

    }

    public void checkAnswer(View view)
    {
        if(Integer.toString(correctAnswerLocation).equals(view.getTag().toString()))
        {
            resultTextView.setText("Correct !");
            score++;
        }
        else
        {
            resultTextView.setText("Wrong :(");
        }
        total++;

        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(total));

        newQuestion();
    }

    public void playAgain(View view)
    {
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        timerTextView.setText("30s");
        score=0;
        total=0;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(total));

        new CountDownTimer(30100, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                mediaPlayer.pause();
                resultTextView.setText("Done!");
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);

                playAgainButton.setVisibility(View.VISIBLE);

                if(score >=0 && score<=5)
                {
                    Toast.makeText(MainActivity.this, "You... work harder!", Toast.LENGTH_LONG).show();
                }
                else if(score >=6 && score<=10)
                {
                    Toast.makeText(MainActivity.this, "Job Done NICELY!", Toast.LENGTH_LONG).show();
                }
                else if(score>=11 && score<=15){
                    Toast.makeText(MainActivity.this, "A champion is at play!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "East OR West! You are the best!", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();

        newQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nowButton = (Button) findViewById(R.id.nowButton);
        nowTextView = (TextView) findViewById(R.id.nowTextView);
        answers = new ArrayList<Integer>();
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        gameLayout = (ConstraintLayout) findViewById(R.id.gameLayout);

        gameLayout.setVisibility(View.INVISIBLE);

        playAgain(findViewById(R.id.resultTextView));
    }
}
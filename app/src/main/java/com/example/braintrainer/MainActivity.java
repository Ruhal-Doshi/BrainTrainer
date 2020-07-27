package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button playAgainButton;
    TextView quesTextView;
    TextView timeTextView;
    TextView scoreTextView;
    GridLayout gridLayout;
    ArrayList<TextView> options;
    int correctOption;
    int totalQuestions;
    int correctQuestions;
    boolean isActive;

    public void setScore(){
        String format = "";

        if(correctQuestions<10){
            format+= "0" + Integer.toString(correctQuestions) +"/";
        }else{
            format+= Integer.toString(totalQuestions) +"/";
        }

        if(totalQuestions<10){
            format+="0"+Integer.toString(totalQuestions);
        }else{
            format+= Integer.toString(totalQuestions);
        }

        scoreTextView.setText(format);
    }
    public void generateQuestion(){
        Random r = new Random();
        int x = r.nextInt(50);
        int y = r.nextInt(50);

        for(int i =0;i<4;i++){
            int temp = r.nextInt(100);
            while(temp==x+y){
                temp = r.nextInt(100);
            }
            options.get(i).setText(Integer.toString(temp));
        }

        int correct = r.nextInt(4);

        options.get(correct).setText(Integer.toString(x+y));
        correctOption = correct;

        String format = Integer.toString(x)+" + "+Integer.toString(y);
        quesTextView.setText(format);

        totalQuestions++;

        setScore();
    }
    public void check(View view){
        if(!isActive)return;
        int tag = Integer.parseInt(view.getTag().toString());
        if(tag==correctOption){
            correctQuestions++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Wrong!",Toast.LENGTH_SHORT).show();
        }
        generateQuestion();
    }
    public void startGame(View view){
        isActive=true;
        goButton.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        quesTextView.setVisibility(View.VISIBLE);
        timeTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);

        new CountDownTimer(31000,1000){

            @Override
            public void onTick(long l) {
                int sec = (int)(l/1000);
                String format = "";

                if(sec<10){
                    format += "0" + Integer.toString(sec) + "s";
                }else{
                    format += Integer.toString(sec) + "s";
                }

                timeTextView.setText(format);
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                isActive = false;
            }
        }.start();
        totalQuestions=0;
        correctQuestions=0;
        generateQuestion();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button) findViewById(R.id.goButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        quesTextView = (TextView) findViewById(R.id.quesTextView);
        timeTextView = (TextView) findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        gridLayout = (GridLayout)findViewById(R.id.grid);

        options = new ArrayList<>();
        options.add((TextView)findViewById(R.id.textView0));
        options.add((TextView)findViewById(R.id.textView1));
        options.add((TextView)findViewById(R.id.textView2));
        options.add((TextView)findViewById(R.id.textView3));

        isActive = false;
    }
}
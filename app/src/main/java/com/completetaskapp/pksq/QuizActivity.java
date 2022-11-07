package com.completetaskapp.pksq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    private TextView questions;
    private TextView question;

    private AppCompatButton option1, option2, option3, option4;

    private AppCompatButton nextBtn;

    private Timer quizTimer;

    private int totalTimeInMins = 1;

    private int seconds = 0;

    private List<QuestionsList> questionsLists;

    private int currentQuestionPosition = 0;

    private String  selectedOptionByUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView timer = findViewById(R.id.timer);
        final TextView selectedTopicName = findViewById(R.id.topic_name);

        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        nextBtn = findViewById(R.id.nextBtn);

        final String getSelectedTopicName = getIntent().getStringExtra("selectedTopicName");

        selectedTopicName.setText(getSelectedTopicName);

        questionsLists = QuestionBank.getQuestions(getSelectedTopicName);

        startTimer(timer);

        questions.setText((currentQuestionPosition+1)+"/"+questionsLists.size());
        question.setText(questionsLists.get(0).getQuestion());
        option1.setText(questionsLists.get(0).getOption1());
        option2.setText(questionsLists.get(0).getOption2());
        option3.setText(questionsLists.get(0).getOption3());
        option4.setText(questionsLists.get(0).getOption4());


        option1.setOnClickListener((View v)-> {

            if (selectedOptionByUser.isEmpty()){

                selectedOptionByUser = option1.getText().toString();
                option1.setBackgroundResource(R.drawable.answer_round_back);
                option1.setTextColor(Color.WHITE);

                revealAnswer();

                questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
            }
        });

        option2.setOnClickListener((View v)-> {

            if (selectedOptionByUser.isEmpty()){

                selectedOptionByUser = option2.getText().toString();
                option2.setBackgroundResource(R.drawable.answer_round_back);
                option2.setTextColor(Color.WHITE);

                revealAnswer();

                questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
            }
        });

        option3.setOnClickListener((View v)-> {

            if (selectedOptionByUser.isEmpty()){

                selectedOptionByUser = option3.getText().toString();
                option3.setBackgroundResource(R.drawable.answer_round_back);
                option3.setTextColor(Color.WHITE);

                revealAnswer();

                questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
            }
        });

        option4.setOnClickListener((View v)-> {

            if (selectedOptionByUser.isEmpty()){

                selectedOptionByUser = option4.getText().toString();
                option4.setBackgroundResource(R.drawable.answer_round_back);
                option4.setTextColor(Color.WHITE);

                revealAnswer();

                questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
            }
        });

        nextBtn.setOnClickListener((View v)-> {

            if (selectedOptionByUser.isEmpty()){
                Toast.makeText(QuizActivity.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
            else{
                changeNextQuestion();
            }
        });


        backBtn.setOnClickListener((View v)-> {
            quizTimer.purge();
            quizTimer.cancel();

            startActivity(new Intent(QuizActivity.this, MainActivity.class));
        });

    }

    private void changeNextQuestion(){

        currentQuestionPosition++;

        if ((currentQuestionPosition+1) == questionsLists.size()){
            nextBtn.setText("\n" +
                    "Finish Test");
        }

        if (currentQuestionPosition < questionsLists.size()){

            selectedOptionByUser = "";

            option1.setBackgroundResource(R.drawable.option_bg);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.option_bg);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.option_bg);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.option_bg);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            questions.setText((currentQuestionPosition+1)+"/"+questionsLists.size());
            question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
            option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionsLists.get(currentQuestionPosition).getOption4());
        }
        else{
            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
            intent.putExtra("TRUE", getCorrectAnswers());
            intent.putExtra("FALSE", getIncorrectAnswers());
            startActivity(intent);

            finish();
        }
    }

    private void startTimer(TextView timerTextView){

        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(seconds == 0){
                    totalTimeInMins--;
                    seconds = 59;
                }
                else if(seconds == 0 && totalTimeInMins == 0){
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(QuizActivity.this, "Time Expired", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(QuizActivity.this, QuizResults.class);
                    intent.putExtra("TRUE", getCorrectAnswers());
                    intent.putExtra("FALSE",getIncorrectAnswers());
                    startActivity(intent);

                    finish();
                }
                else{
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1){
                            finalMinutes = "0"+finalMinutes;
                        }
                        if (finalSeconds.length() == 1){
                            finalSeconds = "0"+finalSeconds;
                        }

                        timerTextView.setText(finalMinutes +":"+finalSeconds);
                    }
                });
            }
        },1000,1000);
    }

    private int getCorrectAnswers(){

        int correctAnswers = 0;

        for (int i=0;i<questionsLists.size();i++){

            final String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsLists.get(i).getAnswer();

            if (getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    private int getIncorrectAnswers(){

        int correctAnswers = 0;

        for (int i=0;i<questionsLists.size();i++){

            final String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsLists.get(i).getAnswer();

            if (!getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(QuizActivity.this, MainActivity.class));
    }

    private void revealAnswer(){

        final String getAnswer = questionsLists.get(currentQuestionPosition).getAnswer();

        if (option1.getText().equals(getAnswer)){
            option1.setBackgroundResource(R.drawable.true_answer);
            option1.setTextColor(Color.WHITE);
        }
        else if(option2.getText().equals(getAnswer)){
            option2.setBackgroundResource(R.drawable.true_answer);
            option2.setTextColor(Color.WHITE);
        }
        else if(option3.getText().equals(getAnswer)){
            option3.setBackgroundResource(R.drawable.true_answer);
            option3.setTextColor(Color.WHITE);
        }
        else if(option4.getText().equals(getAnswer)){
            option4.setBackgroundResource(R.drawable.true_answer);
            option4.setTextColor(Color.WHITE);
        }
    }
}
package com.completetaskapp.pksq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtn);
        TextView correctAnswer = findViewById(R.id.correctAnswers);
        TextView incorrectAnswer = findViewById(R.id.incorrectAnswers);

        int getCorrectAnswer = getIntent().getIntExtra("correct: ", 0);
        int getincorrectAnswer = getIntent().getIntExtra("incorrect: ", 0);

        correctAnswer.setText(String.valueOf(getCorrectAnswer));
        incorrectAnswer.setText(String.valueOf(getincorrectAnswer));

        startNewBtn.setOnClickListener((View v)-> {

            startActivity(new Intent(QuizResults.this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizResults.this, MainActivity.class));
        finish();
    }
}
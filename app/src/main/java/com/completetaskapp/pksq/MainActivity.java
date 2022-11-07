package com.completetaskapp.pksq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String selectedTopicName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout art = findViewById(R.id.art_layout);
        final LinearLayout eglence = findViewById(R.id.eglence_layout);
        final LinearLayout spor = findViewById(R.id.spor_layout);
        final LinearLayout science = findViewById(R.id.science_layout);

        final Button start_btn = findViewById(R.id.start_btn);

        art.setOnClickListener((View v)-> {
            selectedTopicName = "\n" +
                    "Art";

            art.setBackgroundResource(R.drawable.white_stroke2);

            eglence.setBackgroundResource(R.drawable.white_stroke);
            spor.setBackgroundResource(R.drawable.white_stroke);
            science.setBackgroundResource(R.drawable.white_stroke);
        });

        eglence.setOnClickListener((View v)->{
            selectedTopicName = "\n" +
                    "Fun";

            eglence.setBackgroundResource(R.drawable.white_stroke2);

            art.setBackgroundResource(R.drawable.white_stroke);
            spor.setBackgroundResource(R.drawable.white_stroke);
            science.setBackgroundResource(R.drawable.white_stroke);

        });

        spor.setOnClickListener((View v)->{
            selectedTopicName = "Sport";

            spor.setBackgroundResource(R.drawable.white_stroke2);

            eglence.setBackgroundResource(R.drawable.white_stroke);
            art.setBackgroundResource(R.drawable.white_stroke);
            science.setBackgroundResource(R.drawable.white_stroke);

        });

        science.setOnClickListener((View v)->{
            selectedTopicName = "Science";

            science.setBackgroundResource(R.drawable.white_stroke2);

            art.setBackgroundResource(R.drawable.white_stroke);
            eglence.setBackgroundResource(R.drawable.white_stroke);
            spor.setBackgroundResource(R.drawable.white_stroke);

        });

        start_btn.setOnClickListener((View v)->{

            if(selectedTopicName.isEmpty()){
                Toast.makeText(MainActivity.this, "Please select category.", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("selectedTopicName", selectedTopicName);
                startActivity(intent);
            }
        });
    }
}
package com.basakkandemir.quizmasters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText EditEmail = findViewById(R.id.EditEmail);
        EditText EditSifre = findViewById(R.id.EditSifre);
        Button BtnGiris = findViewById(R.id.BtnGiris);

        //TextView TextUyariMesaji = findViewById(R.id.TextUyariMesaji);
        //TextUyariMesaji.setVisibility(View.GONE);

        BtnGiris.setOnClickListener((e)->{
            String email = EditEmail.getText().toString();
            String sifre = EditSifre.getText().toString();

            if(email.equals("basak@admin.com") && sifre.equals("12345")){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
//            else
//            {
//                TextUyariMesaji.setVisibility(View.VISIBLE);
//                Toast.makeText(this, "Email Adresi veya Şifre Yanlıştır."
//                        , Toast.LENGTH_SHORT).show();
//            }
        });
    }
}
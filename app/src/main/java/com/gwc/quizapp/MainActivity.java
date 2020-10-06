package com.gwc.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.gwc.quizapp.configurations.Constants.COM_GWC_APPS_QUIZAPP_SESSION_NAME;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QuizApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: btn_start Button clicked");
                String name = ((EditText)findViewById(R.id.et_name)).getText().toString();
                if(name.isEmpty()){
                    Log.i(TAG, "onClick: Name text is empty. Show toast");
                    Toast.makeText(MainActivity.this, "Please enter your name !", Toast.LENGTH_SHORT).show();
                }else{
                    launchActivity(QuizQuestionsActivity.class, name);
                }
            }
        });
    }

    private void launchActivity(Class<QuizQuestionsActivity> quizQuestionsActivityClass, String name) {
        Intent intent = new Intent(MainActivity.this, quizQuestionsActivityClass);
        intent.putExtra(COM_GWC_APPS_QUIZAPP_SESSION_NAME,name);
        startActivity(intent);
        finish();
    }
}
package com.example.rnv_examenandroid2evalu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rnv_examenandroid2evalu.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

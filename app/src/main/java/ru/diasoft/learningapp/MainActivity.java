package ru.diasoft.learningapp;

import android.content.Intent;
import android.nfc.Tag;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onQuizClick(View view) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        switch (id) {
            case R.id.action_green:
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorLightGreen));
                break;
            case R.id.action_red:
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorLightRed));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

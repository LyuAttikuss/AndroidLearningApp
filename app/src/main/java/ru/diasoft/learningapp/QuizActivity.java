package ru.diasoft.learningapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CODE = "answer_code";
    private static final int REQUEST_CODE = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionsBank = new Question[] {
            new Question(R.string.quiz_cities, true),
            new Question(R.string.quiz_lakes, false),
    };

    private int mCurrentIndex = 0;
    private boolean mHasCheated;

    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean pressedTrue) {
        boolean answerIsCorrect = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        // Если ответ просмотрели.
        if (mHasCheated) {
            messageResId = R.string.quiz_cheated;
        } else {
            if (pressedTrue == answerIsCorrect) {
                messageResId = R.string.correct;
            } else {
                messageResId = R.string.incorrect;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        mQuestionTextView =(TextView)findViewById(R.id.quizContent);
        mTrueButton = (Button)findViewById(R.id.buttonYes);
        mFalseButton = (Button)findViewById(R.id.buttonNo);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.buttonNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                mHasCheated = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.buttonCheat);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // Проверка сохраненных данных.
        if (saveInstanceState != null) {
            mCurrentIndex = saveInstanceState.getInt(KEY_INDEX, 0);
            mHasCheated = saveInstanceState.getBoolean(KEY_CODE, false);
        }

        updateQuestion();
    }

    // Проверка кода запроса и кода результата.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult() called");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE) {
            if (data == null) {
                return;
            }
            mHasCheated = CheatActivity.wasAnswerShown(data);
        }
    }

    // Сохранение индекса между поворотами.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState() called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CODE, mHasCheated);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}

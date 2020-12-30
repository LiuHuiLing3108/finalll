package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private List<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewQuestion = findViewById(R.id.txtQuestion);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount = findViewById(R.id.txtQuestionCount);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.btnconfirmnext);

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);
        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if ( rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() ) {
                        checkAnswer();
                    } else {
                        Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {

        rbGroup.clearCheck();
        rb1.setBackgroundColor(Color.WHITE);
        rb2.setBackgroundColor(Color.WHITE);
        rb3.setBackgroundColor(Color.WHITE);
        rb4.setBackgroundColor(Color.WHITE);

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }
        showSolution(answerNr, rbSelected);
    }

    private void showSolution(int answerNr, View rbSelected) {

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb1.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("Correct");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("You Wrong \n Correct answer is 1");
                }
                break;
            case 2:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb2.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("Correct");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("You Wrong \n Correct answer is 2");
                }
                break;
            case 3:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb3.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("Correct");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("You Wrong \n Correct answer is 3");
                }
                break;
            case 4:
                if(currentQuestion.getAnswerNr() == answerNr){
                    rb4.setBackgroundColor(Color.GREEN);
                    textViewQuestion.setText("Correct");
                }else {
                    rbSelected.setBackgroundColor(Color.RED);
                    textViewQuestion.setText("You Wrong \n Correct answer is 4");
                }
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() {
        finish();
    }
}
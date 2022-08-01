package com.bignerdranch.android.geomain

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView
import android.widget.Toast
import android.util.Log
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView:TextView
    private val questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americans,true),
        Question(R.string.question_asia,true))
    private var currentIndex = 0
    private var currentScore = 0.0
    private var lockedQuestion = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        setContentView(R.layout.activity_main)

        trueButton =
            findViewById(R.id.true_button)
        trueButton.setOnClickListener { view: View ->
            lockedQuestion.add(currentIndex)
          checkAnswer(true)
            disableButtons()
        }
        falseButton =
            findViewById(R.id.false_button)

        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        falseButton.setOnClickListener { view: View ->
            lockedQuestion.add(currentIndex)
            checkAnswer(false)
            disableButtons()

        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) %
                    questionBank.size
            updateQuestion()
            disableButtons()
        }
        prevButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex = (currentIndex - 1)
                updateQuestion()
            } else {
                currentIndex = questionBank.size - 1
                updateQuestion()
            }
            disableButtons()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
        disableButtons()

        }
    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }
    private fun updateQuestion() {


        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)

    }
    private fun checkAnswer(userAnswer:Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId: Int
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            currentScore++
        } else {
            messageResId = R.string.incorrect_toast
        }
        if (questionBank.size == lockedQuestion.size) {
            Toast.makeText(
                this, "${(currentScore / questionBank.size * 100).toInt()}%"
          ,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, messageResId,Toast.LENGTH_SHORT)
        .show()
    }
}
    private fun disableButtons() {
      if(lockedQuestion.contains(currentIndex)) {
          trueButton.isEnabled = false
          falseButton.isEnabled = false
      } else {
          trueButton.isEnabled = true
          falseButton.isEnabled = true
      }

    }
}
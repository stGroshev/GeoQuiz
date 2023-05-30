package com.example.geoquiz

import android.os.Bundle
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var prevButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        questionTextView = findViewById(R.id.question_text_view)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        updateQuestion()

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            if (quizViewModel.currentIndex == 0) {
                quizViewModel.currentIndex = quizViewModel.questionBank.size - 1
                updateQuestion()
            } else {
                quizViewModel.currentIndex = (quizViewModel.currentIndex - 1) % quizViewModel.questionBank.size
                updateQuestion()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    // функция обновления вопроса в TextView
    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    // функция проверки ответа пользователя
    private fun checkAnswer (userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (quizViewModel.currentIndex != quizViewModel.questionBank.size - 1 || quizViewModel.currentIndex == quizViewModel.questionBank.size - 1) {
            if (quizViewModel.count == 0) {
                //quizViewModel.count++
                //val correctAnswer = quizViewModel.questionBank[quizViewModel.currentIndex].answer
                var messageResId: String = "0"
                if (userAnswer == quizViewModel.correctAnswer) {
                    messageResId = resources.getString(R.string.correct_toast)
                    quizViewModel.countAnswerUser++
                } else {
                    messageResId = resources.getString(R.string.incorrect_toast)
                }
                toastPrint(messageResId)
            }else {
                val message = resources.getString(R.string.message_re_entry)
                toastPrint(message)
            }
            if (quizViewModel.currentIndex == quizViewModel.questionBank.size - 1){
                val toast = Toast.makeText(this, "Правильных ответов ${quizViewModel.countAnswerUser} из ${quizViewModel.questionBank.size}", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP,0,0)
                toast.show()
                quizViewModel.countAnswerUser = 0
            }
        }
    }
    //функция вывода тоста на экран
    private fun toastPrint (message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}




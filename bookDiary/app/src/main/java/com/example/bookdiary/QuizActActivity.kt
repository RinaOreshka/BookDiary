package com.example.bookdiary

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActActivity : AppCompatActivity() {

    private lateinit var questions: MutableList<Question>
    private var currentQuestionIndex = 0
    private var correctAnswersCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_act)

        val db = DBFormer(this, null)
        val questText: TextView = findViewById(R.id.question_text)
        val radioGroup: RadioGroup = findViewById(R.id.answer_group)
        val checkAnswerButton: Button = findViewById(R.id.check_answer)
        val nextButton: Button = findViewById(R.id.button_next)

        val quizId = intent.getStringExtra("quiz_id")?.toInt() ?: return
        questions = if (quizId < 3) {
            db.getQuestionsByQuizId(quizId.toString()).toMutableList()
        } else {
            db.getQuestionsRand().toMutableList()
        }
        fun setQuestion(questText: TextView, radioGroup: RadioGroup) {
            questText.text = questions[currentQuestionIndex].quiztext
            val db = DBFormer(this, null)

            radioGroup.clearCheck() // Сбрасываем выбор предыдущего ответа
            (radioGroup.findViewById<RadioButton>(R.id.answer1)).text =
                db.getAnsByQuesId(questions[currentQuestionIndex].quizid.toString(), 0).antext
            (radioGroup.findViewById<RadioButton>(R.id.answer2)).text =
                db.getAnsByQuesId(questions[currentQuestionIndex].quizid.toString(), 1).antext
            (radioGroup.findViewById<RadioButton>(R.id.answer3)).text =
                db.getAnsByQuesId(questions[currentQuestionIndex].quizid.toString(), 2).antext
        }

        fun checkAnswer(isCorrect: Boolean, correctAnswersCount: Int): Int {
            return if (isCorrect) {
                correctAnswersCount + 1 // Увеличиваем счетчик, если ответ правильный
            } else {
                correctAnswersCount
            }
        }

        fun showResultDialog(corrAns: Int) {
            val message = "Вы набрали $corrAns очков"

            AlertDialog.Builder(this)
                .setTitle("Результат")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, which ->
                    startActivity(Intent(this, BookMainActivity::class.java))
                    finish()
                }
                .show()

        }

        if (questions.isNotEmpty()) {
            setQuestion(questText, radioGroup)
        }

        checkAnswerButton.setOnClickListener {
            var isCorrect = false
            val selectedId = radioGroup.checkedRadioButtonId

            when (selectedId) {
                R.id.answer1 -> {
                    isCorrect = db.getAnsByQuesId(
                        questions[currentQuestionIndex].quizid.toString(),
                        0
                    ).isCorrect
                }

                R.id.answer2 -> {
                    isCorrect = db.getAnsByQuesId(
                        questions[currentQuestionIndex].quizid.toString(),
                        1
                    ).isCorrect
                }

                R.id.answer3 -> {
                    isCorrect = db.getAnsByQuesId(
                        questions[currentQuestionIndex].quizid.toString(),
                        2
                    ).isCorrect
                }

                else -> {
                    Toast.makeText(this, "Пожалуйста, выберите ответ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // выход, если ответ не выбран
                }
            }

            correctAnswersCount = checkAnswer(isCorrect, correctAnswersCount)
            Toast.makeText(this, if (isCorrect) "Правильный ответ!" else "Неправильный ответ!", Toast.LENGTH_SHORT).show()
        }

        nextButton.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                setQuestion(questText, radioGroup)
            } else {
                showResultDialog(correctAnswersCount)
            }
        }



    }
}


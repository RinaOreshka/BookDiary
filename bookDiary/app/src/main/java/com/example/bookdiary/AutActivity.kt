package com.example.bookdiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aut)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPass: EditText = findViewById(R.id.user_pass)
        val buttonAuth: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        linkToReg.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




        buttonAuth.setOnClickListener {
            val username = userLogin.text.toString()
            val password = userPass.text.toString()

            val intent2 = Intent(this, BookMainActivity::class.java)
            if(username == "" || password == "")
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            else {
                val db = DBFormer(this, null)
                val isAuth: Boolean = db.getUser(username, password)
                val sharedPreferences = this.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val myId = db.getUserId(username, password)
                editor.putString("userId", myId)
                editor.apply()
                if (isAuth) {
                    Toast.makeText(this, "Пользователь авторизован: $username", Toast.LENGTH_SHORT).show()

                    intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent2)

                    userLogin.text.clear()
                    userPass.text.clear()

                    val sharedPreferences = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("IS_AUTHENTICATED", true)
                    editor.apply()
                }
                else{
                    Toast.makeText(this, "Пользователь не зарегистрирован!", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}
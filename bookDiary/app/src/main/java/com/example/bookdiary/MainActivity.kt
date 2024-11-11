package com.example.bookdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linkToAut: TextView = findViewById(R.id.link_to_aut)

        linkToAut.setOnClickListener{
            val intent = Intent(this, AutActivity::class.java)
            startActivity(intent)
        }

        val userLogin: EditText = findViewById(R.id.editTextUsername)
        val userEmail: EditText = findViewById(R.id.editTextEmail)
        val userPass: EditText = findViewById(R.id.editTextPassword)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val username = userLogin.text.toString()
            val email = userEmail.text.toString()
            val password = userPass.text.toString()

            if(username == "" || email == "" || password == "")
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            else {
                val user = User(username, email, password)
                val db = DBFormer(this, null)
                db.addUser(user) // добавление пользователя в базу данных
                Toast.makeText(this, "Пользователь зарегистрирован: $username", Toast.LENGTH_SHORT).show()
                userLogin.text.clear()
                userEmail.text.clear()
                userPass.text.clear()

            }
        }
    }
}
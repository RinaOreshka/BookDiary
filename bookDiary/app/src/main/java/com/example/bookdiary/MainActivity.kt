package com.example.bookdiary

import android.content.Context
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

        val db = DBFormer(this, null)

        val linkToAut: TextView = findViewById(R.id.link_to_aut)

        linkToAut.setOnClickListener{
            val intent = Intent(this, AutActivity::class.java)
            startActivity(intent)
        }

        val userLogin: EditText = findViewById(R.id.editTextUsername)
        val userEmail: EditText = findViewById(R.id.editTextEmail)
        val userPass: EditText = findViewById(R.id.editTextPassword)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)

        val sharedPreferences = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        val isAuthenticated = sharedPreferences.getBoolean("IS_AUTHENTICATED", false)

        if (isAuthenticated) {
            val intent = Intent(this, BookMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        fun areFieldsEmpty(vararg fields: String): Boolean {
            return fields.any { it.isEmpty() }
        }
        buttonRegister.setOnClickListener {
            val username = userLogin.text.toString()
            val email = userEmail.text.toString()
            val password = userPass.text.toString()
            val intent = Intent(this, BookMainActivity::class.java)

            if(areFieldsEmpty(username, email, password))
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            else {
                val sharedPreferences = this.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val myId = db.addUser(username, email, password)
                editor.putString("userId", myId)
                editor.apply()
                Toast.makeText(this, "Пользователь зарегистрирован: $username", Toast.LENGTH_SHORT).show()
                userLogin.text.clear()
                userEmail.text.clear()
                userPass.text.clear()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}
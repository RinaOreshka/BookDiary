package com.example.bookdiary

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileOutputStream

class BookFormActivity : AppCompatActivity() {

    lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_form)

        val bookName: EditText = findViewById(R.id.bookTitle)
        val bookAuthor: EditText = findViewById(R.id.bookAuthor)
        val bookGenre: EditText = findViewById(R.id.bookGenre)
        val bookDescription: EditText = findViewById(R.id.bookDescription)
        val saveButton: Button = findViewById(R.id.saveButton)

    }
}
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
        val uploadImage: Button = findViewById(R.id.buttonUploadImage)
        val bookImage: ImageView = findViewById(R.id.imageViewBook)
        val saveButton: Button = findViewById(R.id.saveButton)

        fun areFieldsEmpty(vararg fields: String): Boolean {
            return fields.any { it.isEmpty() }
        }


        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val imageUri = data?.data
                if (imageUri != null) {
                    saveImageToGallery(imageUri)
                    bookImage.setImageURI(imageUri)
                    uploadImage.visibility = View.GONE
                    bookImage.visibility = View.VISIBLE
                }
            }
        }

        uploadImage.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            pickImageLauncher.launch(Intent.createChooser(intent, "Select Picture"))
        }



        saveButton.setOnClickListener{
            val db = DBFormer(this, null)

            val name = bookName.text.toString()
            val author = bookAuthor.text.toString()
            val genre = bookGenre.text.toString()
            val description = bookDescription.text.toString()
            val photo = imagePath
            val intent = Intent(this, BookMainActivity::class.java)

            if (areFieldsEmpty(name, author, genre, description)){
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            }
            else{
                db.addBook(name, author, genre, description, photo)

                bookName.text.clear()
                bookGenre.text.clear()
                bookAuthor.text.clear()
                bookDescription.text.clear()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

    }

    private fun saveImageToGallery(imageUri: Uri?) {
        if (imageUri != null) {
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            val file = File(filesDir, "image_${System.currentTimeMillis()}.jpg")

            try {
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
                imagePath = file.absolutePath
                MediaScannerConnection.scanFile(this, arrayOf(file.toString()), null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
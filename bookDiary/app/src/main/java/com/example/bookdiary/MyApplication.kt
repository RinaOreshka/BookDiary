package com.example.bookdiary

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Инициализация Firebase
        FirebaseApp.initializeApp(this)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}

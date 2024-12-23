package com.example.bookdiary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// ? - для обработки пустых значений
class DBFormer(private val context: Context, private val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "BookDiary.db", factory, 2){
    // создание таблиц
    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = "CREATE TABLE IF NOT EXISTS users  (userID INT PRIMARY KEY, login TEXT, email TEXT, password TEXT, profileInfo TEXT)"
        db!!.execSQL(createUserTable)
        val createBookTable = "CREATE TABLE IF NOT EXISTS books  (bookID INT PRIMARY KEY, bookName TEXT, author TEXT, genre TEXT, description TEXT, photo BLOB)"
        db!!.execSQL(createBookTable)
        val createQuizTable = "CREATE TABLE IF NOT EXISTS quizzes  (quizID INT PRIMARY KEY, quizName TEXT, quizDate DATE, userid INT, FOREIGN KEY (userid) REFERENCES users(userID))"
        db!!.execSQL(createQuizTable)
        val createQuestionTable = "CREATE TABLE IF NOT EXISTS questions  (questionID INT PRIMARY KEY, questionText TEXT, quizid INT, FOREIGN KEY (quizid) REFERENCES quizzes(quizID))"
        db!!.execSQL(createQuestionTable)
        val createAnswerTable = "CREATE TABLE IF NOT EXISTS answers  (answerID INT PRIMARY KEY, answerText TEXT, questionid INT, FOREIGN KEY (questionid) REFERENCES questions(questionID))"
        db!!.execSQL(createAnswerTable)
        val createListTable = "CREATE TABLE IF NOT EXISTS lists  (listID INT PRIMARY KEY, listName TEXT, listDate INTEGER, userid INT, FOREIGN KEY (userid) REFERENCES users(userID))"
        db!!.execSQL(createListTable)
        val createReviewTable = "CREATE TABLE IF NOT EXISTS reviews  (reviewID INT PRIMARY KEY, reviewText TEXT, rating INTEGER, reviewDate DATE, userid INT, bookid INT,  FOREIGN KEY (userid) REFERENCES users(userID), FOREIGN KEY (bookid) REFERENCES books(bookID))"
        db!!.execSQL(createReviewTable)
        val createListElementsTable = "CREATE TABLE IF NOT EXISTS listElements  (elementID INT PRIMARY KEY, elementName TEXT, elementDescription DATE, userid INT, bookid INT,  FOREIGN KEY (userid) REFERENCES users(userID), FOREIGN KEY (bookid) REFERENCES books(bookID))"
        db!!.execSQL(createListElementsTable)
        val createGoalTable = "CREATE TABLE IF NOT EXISTS goals  (goalID INT PRIMARY KEY, goalStart DATE, goalEnd DATE, isDone BOOLEAN,  userid INT, bookid INT,  FOREIGN KEY (userid) REFERENCES users(userID), FOREIGN KEY (bookid) REFERENCES books(bookID))"
        db!!.execSQL(createGoalTable)
        val createQuoteTable = "CREATE TABLE IF NOT EXISTS quotes  (quoteID INT PRIMARY KEY, quoteText TEXT, userid INT, bookid INT,  FOREIGN KEY (userid) REFERENCES users(userID), FOREIGN KEY (bookid) REFERENCES books(bookID))"
        db!!.execSQL(createQuoteTable)
        val createProgressTable = "CREATE TABLE IF NOT EXISTS progresses  (progressID INT PRIMARY KEY, lastPage INTEGER, goalid INT,  FOREIGN KEY (goalid) REFERENCES goals(goalID))"
        db!!.execSQL(createProgressTable)
        val createDoneTable = "CREATE TABLE IF NOT EXISTS doneBooks  (doneID INT PRIMARY KEY, userid INT, bookid INT, FOREIGN KEY (userid) REFERENCES users(userID),   FOREIGN KEY (bookid) REFERENCES books(bookID))"
        db!!.execSQL(createDoneTable)
        val createMessageTable = "CREATE TABLE IF NOT EXISTS messages  (messageID INT PRIMARY KEY, messageText TEXT, messageDate DATE, userid INT,  FOREIGN KEY (userid) REFERENCES users(userID))"
        db!!.execSQL(createMessageTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropUser = "DROP TABLE IF EXISTS users"
        val dropBook = "DROP TABLE IF EXISTS books"
        val dropQuiz = "DROP TABLE IF EXISTS quizzes"
        val dropQuestion = "DROP TABLE IF EXISTS questions"
        val dropAnswer = "DROP TABLE IF EXISTS answers"
        val dropList = "DROP TABLE IF EXISTS lists"
        val dropReview = "DROP TABLE IF EXISTS reviews"
        val dropGoal = "DROP TABLE IF EXISTS goals"
        val dropQuote = "DROP TABLE IF EXISTS quotes"
        val dropProgress = "DROP TABLE IF EXISTS progresses"
        val dropDone = "DROP TABLE IF EXISTS doneBooks"
        val dropTables = listOf(dropDone, dropReview, dropAnswer, dropBook, dropGoal, dropProgress, dropQuestion,
            dropQuiz, dropUser, dropList, dropQuote)
        dropTables.forEach{
            db!!.execSQL(it)
        }
        onCreate(db)
    }


    fun addUser(login: String, email: String, password: String) {
        val values = ContentValues()
        values.put("login", login)
        values.put("email", email)
        values.put("password", password)

        val db = this.writableDatabase
        db.insert("users", null, values)

        db.close()
    }

    @SuppressLint("Recycle")
    fun getUser (login: String, password: String ) : Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$password'", null)
        return result.moveToFirst()
    }

    fun addBook(login: String, email: String, password: String) {
        val values = ContentValues()
        values.put("login", login)
        values.put("email", email)
        values.put("password", password)

        val db = this.writableDatabase
        db.insert("users", null, values)

        db.close()
    }
}
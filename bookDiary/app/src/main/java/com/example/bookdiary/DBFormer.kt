package com.example.bookdiary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// ? - для обработки пустых значений
class DBFormer(private val context: Context, private val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "BookDiary.db", factory, 12){
    // создание таблиц
    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = "CREATE TABLE IF NOT EXISTS users  (userID INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, email TEXT, password TEXT, profileInfo TEXT)"
        db!!.execSQL(createUserTable)
        val createBookTable = "CREATE TABLE IF NOT EXISTS books  (bookID INTEGER PRIMARY KEY AUTOINCREMENT, bookName TEXT, author TEXT, genre TEXT, description TEXT, photo TEXT)"
        db!!.execSQL(createBookTable)
        val createQuizTable = "CREATE TABLE IF NOT EXISTS quizzes  (quizID INTEGER PRIMARY KEY AUTOINCREMENT, quizName TEXT)"
        db!!.execSQL(createQuizTable)
        val createQuestionTable = "CREATE TABLE IF NOT EXISTS questions  (questionID INTEGER PRIMARY KEY AUTOINCREMENT, questionText TEXT, quizid INT NOT NULL, FOREIGN KEY (quizid) REFERENCES quizzes(quizID))"
        db!!.execSQL(createQuestionTable)
        val createAnswerTable = "CREATE TABLE IF NOT EXISTS answers  (answerID INTEGER PRIMARY KEY AUTOINCREMENT, answerText TEXT, isCorrect BOOLEAN, questionid INT NOT NULL, FOREIGN KEY (questionid) REFERENCES questions(questionID))"
        db!!.execSQL(createAnswerTable)
        val createGoalTable = "CREATE TABLE IF NOT EXISTS goals  (goalID INTEGER PRIMARY KEY AUTOINCREMENT, goalStart TEXT, goalEnd TEXT, isDone BOOLEAN, numPage INT, userid INT, bookid INT NOT NULL,  FOREIGN KEY (userid) REFERENCES users(userID), FOREIGN KEY (bookid) REFERENCES books(bookID))"
        db!!.execSQL(createGoalTable)
        val createQuoteTable = "CREATE TABLE IF NOT EXISTS quotes  (quoteID INTEGER PRIMARY KEY AUTOINCREMENT, quoteText TEXT, bookName TEXT, bookAuthor INT, userid INT NOT NULL, FOREIGN KEY (userid) REFERENCES users(userID))"
        db!!.execSQL(createQuoteTable)
        val createProgressTable = "CREATE TABLE IF NOT EXISTS progresses  (progressID INTEGER PRIMARY KEY AUTOINCREMENT, lastPage INTEGER, goalid INT NOT NULL,  FOREIGN KEY (goalid) REFERENCES goals(goalID))"
        db!!.execSQL(createProgressTable)
        val createDoneTable = "CREATE TABLE IF NOT EXISTS doneBooks  (doneID INTEGER PRIMARY KEY AUTOINCREMENT, userid INT NOT NULL, bookid INT NOT NULL, FOREIGN KEY (userid) REFERENCES users(userID),   FOREIGN KEY (bookid) REFERENCES books(bookID))"
        db!!.execSQL(createDoneTable)
        val createMessageTable = "CREATE TABLE IF NOT EXISTS feedback  (messageID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, userEmail TEXT, messageText TEXT, userid INT NOT NULL,  FOREIGN KEY (userid) REFERENCES users(userID))"
        db!!.execSQL(createMessageTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropUser = "DROP TABLE IF EXISTS users"
        val dropBook = "DROP TABLE IF EXISTS books"
        val dropQuiz = "DROP TABLE IF EXISTS quizzes"
        val dropQuestion = "DROP TABLE IF EXISTS questions"
        val dropAnswer = "DROP TABLE IF EXISTS answers"
        val dropGoal = "DROP TABLE IF EXISTS goals"
        val dropQuote = "DROP TABLE IF EXISTS quotes"
        val dropProgress = "DROP TABLE IF EXISTS progresses"
        val dropDone = "DROP TABLE IF EXISTS doneBooks"
        val dropTables = listOf(dropDone, dropAnswer, dropBook, dropGoal, dropProgress, dropQuestion,
            dropQuiz, dropUser, dropQuote)
        dropTables.forEach{
            db!!.execSQL(it)
        }
        onCreate(db)
    }


    fun addUser(login: String, email: String, password: String): String {
        val values = ContentValues()
        values.put("login", login)
        values.put("email", email)
        values.put("password", password)

        val db = this.writableDatabase

        db.execSQL("PRAGMA foreign_keys = ON;")
        val result = db.insert("users", null, values)

        db.close()

        return result.toString()

    }

    @SuppressLint("Recycle")
    fun getUser (login: String, password: String ) : Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$password'", null)
        return result.moveToFirst()
    }

    @SuppressLint("Recycle")
    fun getUserId (login: String, password: String ) : String {
        val db = this.readableDatabase

        var userId = "-1"

        val cursor = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$password'", null)
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getString(0)
        }
        cursor.close()
        return userId
    }

    fun addBook(bookName: String, bookAuthor: String, bookGenre: String, bookDescription: String, bookPhoto: String?) {
        val values = ContentValues()
        values.put("bookName", bookName)
        values.put("author", bookAuthor)
        values.put("genre", bookGenre)
        values.put("description", bookDescription)
        values.put("photo", bookPhoto)

        val db = this.writableDatabase
        db.insert("books", null, values)

        db.close()
    }

    fun getBooks(): ArrayList<Book> {
        val db = this.readableDatabase
        val books = ArrayList<Book>()
        val cursor = db.rawQuery("SELECT * FROM books", null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(1)
                val author = cursor.getString(2)
                val genre= cursor.getString(3)
                val description = cursor.getString(4)
                val photo = cursor.getString(5)
                books.add(Book(title, author, genre, description, photo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return books
    }

    fun addQuote(quoteText: String, bookName: String, bookAuthor: String, user: String?){
        val db = this.writableDatabase
        db.execSQL("PRAGMA foreign_keys = ON;")
        val valuesQuote = ContentValues()
        valuesQuote.put("quoteText", quoteText)
        valuesQuote.put("bookName", bookName)
        valuesQuote.put("bookAuthor", bookAuthor)
        if (user != null) {
            user.toIntOrNull()
        }
        valuesQuote.put("userid", user)

        db.insert("quotes", null, valuesQuote)
        db.close()
    }

    fun getQuotes(): ArrayList<Quote> {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userID = sharedPreferences.getString("userId", null)

        userID.toString()

        val db = this.readableDatabase
        db.execSQL("PRAGMA foreign_keys = ON;")
        val quotes = ArrayList<Quote>()
        val cursor = db.rawQuery(
            "SELECT * FROM quotes", null
        )

        if (cursor.moveToFirst()) {
            do {
                val text = cursor.getString(1)
                val book = cursor.getString(2)
                val author= cursor.getString(3)
                quotes.add(Quote(text, book, author))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return quotes
    }
    fun searchBook(searchQuery: String): List<Book> {
        val db = this.readableDatabase
        val booksList = mutableListOf<Book>()

        val cursor = db.rawQuery(
            "SELECT * FROM books WHERE bookName LIKE ?",
            arrayOf("%$searchQuery%")
        )

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(1)
                val author = cursor.getString(2)
                val genre= cursor.getString(3)
                val description = cursor.getString(4)
                val photo = cursor.getString(5)
                booksList.add(Book(title, author, genre, description, photo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return booksList
    }
    fun getBookName(bookid: Int): String {
        val db = this.readableDatabase
        val booksList = mutableListOf<Book>()

        bookid.toString()

        val cursor = db.rawQuery(
            "SELECT * FROM books WHERE bookID LIKE ?",
            arrayOf("%$bookid%")
        )

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(1)
                val author = cursor.getString(2)
                val genre= cursor.getString(3)
                val description = cursor.getString(4)
                val photo = cursor.getString(5)
                booksList.add(Book(title, author, genre, description, photo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        val result = booksList[0].name
        return result
    }

    fun addGoal(startTime: String, endTime: String, page: Int, book: String): String {
        val db = this.readableDatabase
        val values = ContentValues()
        values.put("goalStart", startTime)
        values.put("goalEnd", endTime)
        values.put("isDone", false)
        values.put("numPage", page)
        val cursor = db.rawQuery("SELECT * FROM books WHERE bookName = ?", arrayOf(book))

        var bookid: Int = -1

        if (cursor.moveToFirst()) {
            do {
                val idBook = cursor.getInt(0)
                bookid = idBook
            } while (cursor.moveToNext())
        }

        cursor.close()
        val sharedPreferences =
            context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("userId", null)
        values.put("userid", user)
        values.put("bookid", bookid)
        if (bookid == -1) {
            return "Нет книги в библиотеке"
        } else {
            val newRowId = db.insert("goals", null, values)
            val contentValues = ContentValues().apply {
                put("lastPage", 0)
                put("goalid", newRowId.toInt())
            }
            db.insert("progresses", null, contentValues)
            return "Цель создана"
        }
    }
    fun getGoals(): ArrayList<Goal> {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userID = sharedPreferences.getString("userId", null)

        val db = this.readableDatabase
        db.execSQL("PRAGMA foreign_keys = ON;")
        val goals = ArrayList<Goal>()
        val cursor = db.rawQuery("SELECT * FROM goals WHERE userid = ?", arrayOf(userID))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val start = cursor.getString(1)
                val end = cursor.getString(2)
                val pages = cursor.getInt(4)
                val userId = cursor.getInt(5)
                val bookId = cursor.getInt(6)
                goals.add(Goal(id, start, end, pages, userId, bookId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return goals
    }

    fun getGoal(goalid: String?): Goal {
        val db = this.readableDatabase
        val goals = mutableListOf<Goal>()

        val cursor = db.rawQuery("SELECT * FROM goals WHERE goalID = ?", arrayOf(goalid))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val start = cursor.getString(1)
                val end = cursor.getString(2)
                val pages = cursor.getInt(4)
                val userId = cursor.getInt(5)
                val bookId = cursor.getInt(6)
                goals.add(Goal(id, start, end, pages, userId, bookId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        val result = goals[0]
        return result
    }

    fun getProgress(goalId: String?): Int {
        val db = this.readableDatabase
        val progress = mutableListOf<Progress>()

        val cursor = db.rawQuery("SELECT * FROM progresses WHERE goalid = ?", arrayOf(goalId))

        if (cursor.moveToFirst()) {
            do {
                val pages = cursor.getInt(1)
                progress.add(Progress(pages))
            } while (cursor.moveToNext())
        }
        cursor.close()
        val result = progress[0].pageNum
        return result
    }

    fun changeProgress(goalId: String?, newPages: String) {
        val db = this.writableDatabase

        val sql = "UPDATE progresses SET lastPage = $newPages WHERE goalid = $goalId"
        val statement = db.compileStatement(sql)

        statement.executeUpdateDelete()

        statement.close()
        db.close()
    }
    fun finishGoal(goalId: String?){
        val db = this.writableDatabase
        val sql = "UPDATE goals SET isRead = 1 WHERE goalID = $goalId"
        val statement = db.compileStatement(sql)

        val goals = ArrayList<Goal>()
        val cursor = db.rawQuery("SELECT * FROM goals WHERE goalID = ?", arrayOf(goalId))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val start = cursor.getString(1)
                val end = cursor.getString(2)
                val pages = cursor.getInt(4)
                val userId = cursor.getInt(5)
                val bookId = cursor.getInt(6)
                goals.add(Goal(id, start, end, pages, userId, bookId))
            } while (cursor.moveToNext())
        }
        cursor.close()

        val values = ContentValues()
        values.put("userid", goals[0].user)
        values.put("bookid", goals[0].book)

        db.insert("doneBooks", null, values)

        statement.close()
        db.close()
    }
    fun createQuizzez(){
        val db = this.readableDatabase
        val sql = "INSERT INTO quizzes (quizID, quizName) VALUES\n" +
                "(1, 'Викторина по биографии Пушкина'),\n" +
                "(2, 'Русская литература');"
        val statement = db.compileStatement(sql)
        statement.execute()
        statement.close()
    }
    fun createQuestions(questionId: Int, questionText: String, categoryId: Int) {
        val sql = "INSERT INTO questions (questionID, questionText, quizid) VALUES ($questionId, '$questionText', $categoryId);"
        val db = this.writableDatabase
        val statement = db.compileStatement(sql)
        try {
            statement.execute()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun createAnswers(){
        val db = this.readableDatabase
        val sql = "INSERT INTO answers (answerID, answerText, isCorrect, questionid) VALUES\n" +
                "(1, '1799 год', 1, 1),\n" +
                "(2, '1800 год', 0, 1),\n" +
                "(3, '1801 год', 0, 1),\n" +
                "(4, 'Руслан и Людмила', 1, 2),\n" +
                "(5, 'Кавказский пленник', 0, 2),\n" +
                "(6, 'Борис Годунов', 0, 2),\n" +
                "(7, 'Москва', 0, 3),\n" +
                "(8, 'Санкт-Петербург', 1, 3),\n" +
                "(9, 'Одесса', 0, 3),\n" +
                "(10, 'Наталье Гончаровой', 1, 4),\n" +
                "(11, 'Анне Петровне', 0, 4),\n" +
                "(12, 'Екатерине II', 0, 4),\n" +
                "(13, 'Михаил Лермонтов', 0, 5),\n" +
                "(14, 'Александр Пушкин', 1, 5),\n" +
                "(15, 'Федор Тютчев', 0, 5),\n" +
                "(16, 'Пиковая дама', 1, 6),\n" +
                "(17, 'Станционный смотритель', 0, 6),\n" +
                "(18, 'Капитанская дочка', 0, 6),\n" +
                "(19, 'И. А. Крылов', 0, 7),\n" +
                "(20, 'Н. В. Гоголь', 0, 7),\n" +
                "(21, 'П. А. Вяземский', 1, 7),\n" +
                "(22, 'Драма', 0, 8),\n" +
                "(23, 'Лирика', 1, 8),\n" +
                "(24, 'Роман', 0, 8),\n" +
                "(25, 'Романтизм', 1, 9),\n" +
                "(26, 'Реализм', 0, 9),\n" +
                "(27, 'Модернизм', 0, 9),\n" +
                "(28, 'На холмах Грузии лежит ночная мгла', 1, 10),\n" +
                "(29, 'Я вас любил', 0, 10),\n" +
                "(30, 'К морю', 0, 10),\n" +
                "(31, 'Хорей', 0, 11),\n" +
                "(32, 'Ямб', 1, 11),\n" +
                "(33, 'Дактиль', 0, 11),\n" +
                "(34, 'А. С. Грибоедов', 1, 12),\n" +
                "(35, 'В. С. Высоцкий', 0, 12),\n" +
                "(36, 'Б. Л. Пастернак', 0, 12),\n" +
                "(37, 'Скупой рыцарь', 0, 13),\n" +
                "(38, 'Борис Годунов', 1, 13),\n" +
                "(39, 'Дубровский', 0, 13),\n" +
                "(40, 'Жан Батист Мольер', 1, 14),\n" +
                "(41, 'Габриэль Дантес', 0, 14),\n" +
                "(42, 'Жорж Санд', 0, 14),\n" +
                "(43, 'Наталья Гончарова', 1, 15),\n" +
                "(44, 'Мария Фонвизина', 0, 15),\n" +
                "(45, 'Александра Оленина', 0, 15),(46, 'Преступление и наказание', 1, 16),\n" +
                "(47, 'Братья Карамазовы', 0, 16),\n" +
                "(48, 'Идиот', 0, 16),\n" +
                "(49, 'Александр Пушкин', 1, 17),\n" +
                "(50, 'Грибоедов', 0, 17),\n" +
                "(51, 'Лермонтов', 0, 17),\n" +
                "(54, 'Евгений Онегин', 1, 18),\n" +
                "(53, 'Мертвые души', 0, 18),\n" +
                "(52, 'Накануне', 0, 18),\n" +
                "(55, 'Александр Блок', 1, 19),\n" +
                "(56, 'Михаил Лермонтов', 0, 19),\n" +
                "(57, 'Сергей Есенин', 0, 19),\n" +
                "(58, 'Николай Гоголь', 1, 20),\n" +
                "(59, 'Лев Толстой', 0, 20),\n" +
                "(60, 'Федор Достоевский', 0, 20),\n" +
                "(61, 'Капитанская дочка', 1, 21),\n" +
                "(63, 'Родная земля', 0, 21),\n" +
                "(62, 'Военная правда', 0, 21),\n" +
                "(64, 'Лев Толстой', 1, 22),\n" +
                "(65, 'Антон Чехов', 0, 22),\n" +
                "(66, 'Александр Пушкин', 0, 22),\n" +
                "(67, 'Война и мир', 1, 23),\n" +
                "(68, 'Севастопольские рассказы', 0, 23),\n" +
                "(69, 'Тихий Дон', 0, 23),\n" +
                "(70, 'Антон Чехов', 1, 24),\n" +
                "(71, 'Николай Островский', 0, 24),\n" +
                "(72, 'Лев Толстой', 0, 24),\n" +
                "(74, 'Иван Бунин', 1, 25),\n" +
                "(73, 'Александр Блок', 0, 25),\n" +
                "(75, 'Максим Горький', 0, 25);"
        val statement = db.compileStatement(sql)
        statement.execute()
        statement.close()
    }
    fun getQuizzes(): ArrayList<Quiz> {
        val db = this.readableDatabase
        val quizzes = ArrayList<Quiz>()
        val cursor = db.rawQuery(
            "SELECT * FROM quizzes", null
        )

        if (cursor.moveToFirst()) {
            do {
                val qId = cursor.getString(0)
                val name = cursor.getString(1)
                quizzes.add(Quiz(qId, name))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return quizzes
    }
    fun getQuestionsByQuizId(quizId: String?): List<Question> {
        val db = this.readableDatabase
        val questions = mutableListOf<Question>()
        val cursor = db.rawQuery("SELECT * FROM questions WHERE quizid = ?", arrayOf(quizId))

        if (cursor.moveToFirst()) {
            do {
                val quid = cursor.getInt(0)
                val qutext = cursor.getString(1)
                val quizId = cursor.getString(2)
                questions.add(Question(quid, qutext, quizId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return questions
    }
    fun getAnsByQuesId(quesId: String?, num: Int): Answer {
        val db = this.readableDatabase
        val questions = mutableListOf<Answer>()
        val cursor = db.rawQuery("SELECT * FROM answers WHERE questionid = ?", arrayOf(quesId))

        if (cursor.moveToFirst()) {
            do {
                val anid = cursor.getInt(0)
                val antext = cursor.getString(1)
                val isCorr = cursor.getString(2)
                val questId = cursor.getString(3)
                questions.add(Answer(anid, antext, isCorr.toBoolean(), questId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return questions[num]
    }
    fun getQuestionsRand(): List<Question> {
        val db = this.readableDatabase
        val questions = mutableListOf<Question>()
        val cursor = db.rawQuery("SELECT * FROM questions", null)

        if (cursor.moveToFirst()) {
            do {
                val quid = cursor.getInt(0)
                val qutext = cursor.getString(1)
                val quizId = cursor.getString(2)
                questions.add(Question(quid, qutext, quizId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return questions
    }
    fun isEmpty(): Boolean {
        val db = this.readableDatabase
        val questions = mutableListOf<Question>()
        val cursor = db.rawQuery("SELECT * FROM questions", null)
        if (cursor.moveToFirst()) {
            do {
                val quid = cursor.getInt(0)
                val qutext = cursor.getString(1)
                val quizId = cursor.getString(2)
                questions.add(Question(quid, qutext, quizId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        if (questions.isEmpty()){
            return true
        }
        else {
            return false
        }
    }
    fun fillDatabase() {
        val questions = listOf(
            Pair(1, "Когда родился Александр Пушкин?"),
            Pair(2, "Какое произведение считается первым опубликованным произведением Пушкина?"),
            Pair(3, "В каком городе Пушкин провел свое детство?"),
            Pair(4, "Кому был посвящен знаменитый роман в стихах \"Евгений Онегин\"?"),
            Pair(5, "Какой поэт был убит на дуэли в 1837 году?"),
            Pair(6, "Какое произведение Пушкина было написано в 1823 году?"),
            Pair(7, "Кто был первым издателем произведений Пушкина?"),
            Pair(8, "Какой жанр занимает значительное место в творчестве Пушкина?"),
            Pair(9, "С каким литературным направлением ассоциируется Пушкин?"),
            Pair(10, "Какое стихотворение Пушкина было посвящено памяти его друга, поэта В. Кюхельбекера?"),
            Pair(11, "Какой стихотворный размер использует Пушкин в своем знаменитом стихотворении \"Я вас любил\"?"),
            Pair(12, "Какой из вышеперечисленных поэтов был другом Пушкина?"),
            Pair(13, "Какое произведение Пушкина было написано в жанре драмы?"),
            Pair(14, "Какой поэт стал вдохновением для Пушкина в его ранние годы?"),
            Pair(15, "Кто была жена Пушкина?"),
            Pair(16, "Какое произведение написал Фёдор Достоевский?"),
            Pair(17, "Кто является автором поэмы \"Медный всадник\"?"),
            Pair(18, "Какое произведение называется \"романом в стихах\"?"),
            Pair(19, "Какой российский писатель был известен как \"поэт души\"?"),
            Pair(20, "Кто автор повести \"Тарас Бульба\"?"),
            Pair(21, "Какой роман считается первым русским романом?"),
            Pair(22, "Кто написал \"Анну Каренину\"?"),
            Pair(23, "Какое произведение описывает жизнь крепостных крестьян?"),
            Pair(24, "Кто автор пьесы \"Чайка\"?"),
            Pair(25, "Какой писатель известен своими юмористическими рассказами о провинциальной жизни?")
        )

        for ((index, question) in questions.withIndex()) {
            val categoryId = if (index < 15) 1 else 2 // Определяем ID категории
            val db = this
            db.createQuestions(index + 1, question.second, categoryId)
        }
    }
    fun isQuizEmpty(): Boolean {
        val db = this.readableDatabase
        val questions = mutableListOf<Question>()
        val cursor = db.rawQuery("SELECT * FROM questions", null)

        if (cursor.moveToFirst()) {
            do {
                val quid = cursor.getInt(0)
                val qutext = cursor.getString(1)
                val quizId = cursor.getString(2)
                questions.add(Question(quid, qutext, quizId))
            } while (cursor.moveToNext())
        }

        cursor.close()

        if (questions.isEmpty()) {
            return true
        } else {
            return false
        }

    }
    fun insertFeedback(feedback: Feedback) {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userID = sharedPreferences.getString("userId", null)
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", feedback.name)
            put("email", feedback.email)
            put("message", feedback.message)
            put("userid", userID)
        }
        db.insert("feedback", null, contentValues)
        db.close()
    }
    fun deleteUser() {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        userId?.let {
            val sql = "DELETE FROM users WHERE userID = ?"
            val db = this.writableDatabase
            val statement = db.compileStatement(sql)

            // Привязываем параметр к месту в запросе
            statement.bindString(1, it)
            statement.execute()
            statement.close()
        }
    }
}



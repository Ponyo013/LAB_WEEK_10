package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.viewModel.TotalViewModel
import com.example.lab_week_10.viewModel.TotalViewModelFactory

class   MainActivity : AppCompatActivity() {
    // Create an instance of the TotalDatabase
    // by lazy is used to create the database only when it's needed
    private val db by lazy { prepareDatabase() }
    // Create an instance of the TotalViewModel
    // by lazy is used to create the ViewModel only when it's needed
    private val viewModel by lazy {
        ViewModelProvider(this, TotalViewModelFactory(db.totalDao()))[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize the value of the total from the database
        initializeValueFromDatabase()

        // Prepare the ViewModel
        prepareViewModel()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Create and build the TotalDatabase with the name 'total-database'
    // allowMainThreadQueries() is used to allow queries to be run on the main thread
    // This is not recommended, but for simplicity it's used here
    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build()
    }

    // Initialize the value of the total from the database
    // If the database is empty, insert a new Total object with the value of 0
    // If the database is not empty, get the value of the total from the database
    private fun initializeValueFromDatabase() {
        val total = db.totalDao().getTotal(ID)
        if (total.isEmpty()) {
            db.totalDao().insert(Total(id = 1, total = 0))
        } else {
            viewModel.setTotal(total.first().total)
        }
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel(){
        // Observe the LiveData object
        viewModel.total.observe(this, {
            // Whenever the value of the LiveData object changes
            // the updateText() is called, with the new value as the parameter
            updateText(it)
        })
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    companion object {
        const val ID: Long = 1
    }
}

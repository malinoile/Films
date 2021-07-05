package ru.malinoil.films

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttonSave: Button
    private lateinit var buttonRefresh: Button
    private lateinit var buttonCopy: Button
    private lateinit var editName: EditText
    private lateinit var editDuration: EditText
    private lateinit var linearLayout: LinearLayout

    private var listFilms = emptyList<Film>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        buttonSave.setOnClickListener {
            listFilms.add(Film(editName.text.toString(),
                    Integer.valueOf(editDuration.text.toString())))
            editName.text = null
            editDuration.text = null
        }

        buttonRefresh.setOnClickListener {
            listFilms.forEach {
                val view = TextView(this)
                view.text = String.format("%s - %d мин", it.name, it.duration)
                linearLayout.addView(view)
            }
        }

        buttonCopy.setOnClickListener {
            Toast.makeText(this@MainActivity, Coping.copyFilm(listFilms.last()).name, Toast.LENGTH_SHORT).show()
        }
    }


    private fun initViews() {
        editName = findViewById(R.id.film_name)
        editDuration = findViewById(R.id.film_duration)
        buttonSave = findViewById(R.id.button_add)
        buttonRefresh = findViewById(R.id.button_print)
        buttonCopy = findViewById(R.id.button_copy)
        linearLayout = findViewById(R.id.linear_layout)
    }
}
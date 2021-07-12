package ru.malinoil.films

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.malinoil.films.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var listFilms = emptyList<Film>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainer.id, ListFragment())
            .commit()
    }
}
package ru.malinoil.films

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import ru.malinoil.films.databinding.ActivityMainBinding
import ru.malinoil.films.fragment.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainer.id, ListFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_navigation_menu, menu)
        return true
    }
}
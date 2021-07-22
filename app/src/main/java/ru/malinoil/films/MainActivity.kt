package ru.malinoil.films

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.malinoil.films.databinding.ActivityMainBinding
import ru.malinoil.films.fragment.FilmFragment
import ru.malinoil.films.fragment.ListFragment
import ru.malinoil.films.model.Film

private const val HOME_TAG = "home"

class MainActivity : AppCompatActivity(), ListFragment.Contract {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainer.id, ListFragment(), HOME_TAG)
            .commit()

        initBottomNavigationMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_navigation_menu, menu)
        return true
    }

    override fun openFilm(film: Film) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(binding.fragmentContainer.id, FilmFragment.getInstance(film))
            .commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initBottomNavigationMenu() {
        //Не могу понять, почему с binding.bottomNavigation не работает
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_main -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            binding.fragmentContainer.id,
                            supportFragmentManager.findFragmentByTag(HOME_TAG) ?: ListFragment()
                        )
                        .commit()
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.navigation_favorite -> {
                }
                R.id.navigation_category -> {
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return true
    }
}
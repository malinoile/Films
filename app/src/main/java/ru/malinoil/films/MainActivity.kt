package ru.malinoil.films

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.malinoil.films.databinding.ActivityMainBinding
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.ui.fragment.FilmFragment
import ru.malinoil.films.ui.fragment.ListFragment
import ru.malinoil.films.ui.fragment.SettingsFragment

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(binding.fragmentContainer.id, SettingsFragment())
                    .commit()

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openFilm(film: FilmEntity) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(binding.fragmentContainer.id, FilmFragment.getInstance(film))
            .commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initBottomNavigationMenu() {
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

    override fun onDestroy() {
        super.onDestroy()
    }
}
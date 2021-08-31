package ru.malinoil.films

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.malinoil.films.databinding.ActivityMainBinding
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.ui.fragment.ContactsFragment
import ru.malinoil.films.ui.fragment.FilmFragment
import ru.malinoil.films.ui.fragment.ListFragment
import ru.malinoil.films.ui.fragment.SettingsFragment

private const val HOME_TAG = "home_tag"

class MainActivity : AppCompatActivity(), ListFragment.Contract {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(ListFragment(), HOME_TAG)
        initBottomNavigationMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_settings -> {
                setFragment(SettingsFragment(), backStack = true)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            R.id.navigation_contacts -> {
                setFragment(ContactsFragment(), backStack = true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openFilm(film: FilmEntity) {
        setFragment(FilmFragment.getInstance(film), backStack = true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setFragment(fragment: Fragment, tag: String? = null, backStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
        if (backStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    private fun initBottomNavigationMenu() {
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_main -> {
                    setFragment(
                        supportFragmentManager.findFragmentByTag(HOME_TAG) ?: ListFragment(),
                        HOME_TAG
                    )
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
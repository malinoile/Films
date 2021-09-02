package ru.malinoil.films

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.malinoil.films.databinding.ActivityMainBinding
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.ui.fragment.*

private const val HOME_TAG = "home_tag"

class MainActivity : AppCompatActivity(), ListFragment.Contract {
    private lateinit var binding: ActivityMainBinding
    private val listTitles = emptyList<String>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(ListFragment(), getString(R.string.main), HOME_TAG)
        initBottomNavigationMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_settings -> {
                setFragment(
                    SettingsFragment(),
                    getString(R.string.settings),
                    backStack = true,
                    setBackArrow = true
                )
            }
            R.id.navigation_contacts -> {
                setFragment(ContactsFragment(), getString(R.string.get_contacts), backStack = true)
            }
            R.id.navigation_map -> {
                setFragment(
                    GeolocationFragment(),
                    getString(R.string.geolocation),
                    backStack = true
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openFilm(film: FilmEntity) {
        setFragment(
            FilmFragment.getInstance(film),
            getString(R.string.film_info),
            backStack = true,
            setBackArrow = true
        )
    }

    fun setFragment(
        fragment: Fragment,
        title: String,
        tag: String? = null,
        backStack: Boolean = false,
        setBackArrow: Boolean = false
    ) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment, tag)
        if (backStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(setBackArrow)
        listTitles.add(title)
        setTitle()
    }

    private fun initBottomNavigationMenu() {
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_main -> {
                    setFragment(
                        supportFragmentManager.findFragmentByTag(HOME_TAG) ?: ListFragment(),
                        getString(R.string.main),
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
        return true
    }

    override fun onBackPressed() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        listTitles.removeAt(listTitles.size - 1)
        setTitle()
        super.onBackPressed()
    }

    private fun setTitle() {
        if (listTitles.isNotEmpty()) {
            supportActionBar?.title = listTitles[listTitles.size - 1]
        } else {
            supportActionBar?.title = getString(R.string.app_name)
        }
    }
}
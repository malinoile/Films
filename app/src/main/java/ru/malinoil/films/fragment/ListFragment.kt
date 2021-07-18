package ru.malinoil.films.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentMainBinding
import ru.malinoil.films.model.*

class ListFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private var adapter: HomeAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Contract) {
            throw RuntimeException("Activity must implements ListFragment.Contract")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        adapter = HomeAdapter()
        adapter!!.setOnFilmClickListener(object : FilmsAdapter.OnFilmClickListener {
            override fun onClick(film: Film) {
                getContract().openFilm(film)
            }
        })
        adapter!!.setList(getTestList())

        binding!!.homeRecycler.layoutManager = LinearLayoutManager(context)
        binding!!.homeRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    //Формирую заглушечный список фильмов
    private fun getTestList(): List<HomeComponent> {
        var filmsList = ArrayList<Film>()
        filmsList.add(Film("Интерстеллар", 2014, 9.2f, 135, null))
        filmsList.add(Film("Форсаж 9", 2021, 6.5f, 100, null))
        filmsList.add(Film("Начало", 2015, 9.4f, 130, null))
        val list = ArrayList<HomeComponent>()
        list.add(HomeComponent(TitleType.NEWEST, filmsList))
        list.add(HomeComponent(TitleType.NOW, filmsList))
        list.add(HomeComponent(TitleType.HIGH_RATE, filmsList))
        list.add(HomeComponent(TitleType.COMING_SOON, filmsList))
        return list
    }

    private fun getContract(): Contract {
        return activity as Contract
    }

    interface Contract {
        fun openFilm(film: Film)
    }

}
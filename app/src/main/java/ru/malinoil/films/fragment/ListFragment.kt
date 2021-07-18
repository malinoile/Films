package ru.malinoil.films.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentMainBinding
import ru.malinoil.films.model.Film
import ru.malinoil.films.model.HomeAdapter
import ru.malinoil.films.model.HomeComponent
import ru.malinoil.films.model.TitleType

class ListFragment : Fragment() {
    private lateinit var homeRecycler: RecyclerView
    private var binding: FragmentMainBinding? = null
    private var adapter: HomeAdapter? = null

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
        adapter!!.setList(getTestList())

        binding!!.homeRecycler.layoutManager = LinearLayoutManager(context)
        binding!!.homeRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

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

}
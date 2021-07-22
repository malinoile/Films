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
import ru.malinoil.films.model.ComponentsRepository
import ru.malinoil.films.model.Film
import ru.malinoil.films.model.FilmsAdapter
import ru.malinoil.films.model.HomeAdapter

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
        adapter!!.setList(ComponentsRepository.getInstance().getComponents()!!)

        binding!!.homeRecycler.layoutManager = LinearLayoutManager(context)
        binding!!.homeRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getContract(): Contract {
        return activity as Contract
    }

    interface Contract {
        fun openFilm(film: Film)
    }

}
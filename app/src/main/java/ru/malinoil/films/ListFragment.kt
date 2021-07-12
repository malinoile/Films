package ru.malinoil.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {
    private lateinit var newFilmsRecycler: RecyclerView
    private lateinit var comingFilmsRecycler: RecyclerView
    private var newFilmsAdapter: FilmsAdapter? = null
    private var comingFilmsAdapter: FilmsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        newFilmsRecycler = view.findViewById(R.id.new_films_recycler)
        comingFilmsRecycler = view.findViewById(R.id.coming_films_recycler)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newFilmsAdapter = FilmsAdapter()
        comingFilmsAdapter = FilmsAdapter()
        val filmsRepository = FilmsRepositoryImpl()

        newFilmsRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        comingFilmsRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )

        newFilmsRecycler.adapter = newFilmsAdapter
        comingFilmsRecycler.adapter = comingFilmsAdapter

        filmsRepository.newFilmsList.value?.let { newFilmsAdapter?.setList(it) }
        filmsRepository.comingFilmsList.value?.let { comingFilmsAdapter?.setList(it) }

        initializeObservers(filmsRepository)
    }

    private fun initializeObservers(repository: FilmsRepositoryImpl) {
        repository.newFilmsList.observe(viewLifecycleOwner) {
            newFilmsAdapter?.notifyDataSetChanged()
        }
        repository.comingFilmsList.observe(viewLifecycleOwner) {
            comingFilmsAdapter?.notifyDataSetChanged()
        }
    }

}
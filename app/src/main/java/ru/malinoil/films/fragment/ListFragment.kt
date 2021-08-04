package ru.malinoil.films.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.malinoil.films.CategoriesContract
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentMainBinding
import ru.malinoil.films.model.CategoriesRepository
import ru.malinoil.films.model.FilmsAdapter
import ru.malinoil.films.model.HomeAdapter
import ru.malinoil.films.model.entities.CategoryEntity
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.presenter.CategoriesPresenterImpl

class ListFragment : Fragment(), CategoriesContract.View {
    private var binding: FragmentMainBinding? = null
    private var adapter: HomeAdapter? = null
    private var presenter: CategoriesContract.Presenter = CategoriesPresenterImpl()
    private val categories: CategoriesRepository = CategoriesRepository.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Contract) {
            throw RuntimeException("Activity must implements ListFragment.Contract")
        }
        presenter.attach(this)
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
        if (categories.getCategories()?.isEmpty() == true) {
            presenter.initializeCategories()
        }
        adapter = HomeAdapter()
        adapter!!.setOnFilmClickListener(object : FilmsAdapter.OnFilmClickListener {
            override fun onClick(film: FilmEntity) {
                getContract().openFilm(film)
            }
        })

        categories.getCategories()?.let { updateData(it) }

        binding!!.homeRecycler.layoutManager = LinearLayoutManager(context)
        binding!!.homeRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        presenter.detach()
    }

    private fun getContract(): Contract {
        return activity as Contract
    }

    interface Contract {
        fun openFilm(film: FilmEntity)
    }

    override fun updateData(list: MutableList<CategoryEntity>) {
        adapter!!.setList(list)
        adapter!!.notifyDataSetChanged()
    }

}
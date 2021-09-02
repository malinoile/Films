package ru.malinoil.films.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.malinoil.films.MyApplication
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentMainBinding
import ru.malinoil.films.model.contracts.CategoriesContract
import ru.malinoil.films.model.entities.CategoryEntity
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.presenter.CategoriesPresenterImpl
import ru.malinoil.films.ui.FilmsAdapter
import ru.malinoil.films.ui.HomeAdapter

class ListFragment() : Fragment(), CategoriesContract.View {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var adapter: HomeAdapter? = null
    private val app: MyApplication by lazy { activity?.application as MyApplication }
    private lateinit var presenter: CategoriesContract.Presenter
    private var categories = emptyList<CategoryEntity>()

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
        presenter = CategoriesPresenterImpl(app.retrofit)
        presenter.attach(this)
        _binding = FragmentMainBinding.bind(view)
        adapter = HomeAdapter()
        adapter!!.setOnFilmClickListener(object : FilmsAdapter.OnFilmClickListener {
            override fun onClick(film: FilmEntity) {
                getContract().openFilm(film)
            }
        })

        if (categories.isEmpty()) {
            categories = presenter.getCategories()
        } else {
            updateData(categories)
        }

        binding.apply {
            homeRecycler.layoutManager = LinearLayoutManager(context)
            homeRecycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.detach()
    }

    private fun getContract(): Contract {
        return activity as Contract
    }

    interface Contract {
        fun openFilm(film: FilmEntity)
    }

    override fun updateData(list: List<CategoryEntity>) {
        adapter!!.setList(list)
        adapter!!.notifyDataSetChanged()
    }

}
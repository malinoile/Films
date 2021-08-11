package ru.malinoil.films.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.malinoil.films.CategoriesContract
import ru.malinoil.films.MyApplication
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentMainBinding
import ru.malinoil.films.model.FilmsAdapter
import ru.malinoil.films.model.HomeAdapter
import ru.malinoil.films.model.entities.CategoryEntity
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.presenter.CategoriesPresenterImpl

class ListFragment : Fragment(), CategoriesContract.View {
    private var binding: FragmentMainBinding? = null
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
        binding = FragmentMainBinding.bind(view)
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

    override fun updateData(list: List<CategoryEntity>) {
        adapter!!.setList(list)
        adapter!!.notifyDataSetChanged()
    }

}
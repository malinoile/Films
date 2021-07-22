package ru.malinoil.films.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.malinoil.films.FilmsContract
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentFilmBinding
import ru.malinoil.films.model.Film
import ru.malinoil.films.presenter.FilmPresenterImpl

private const val FILM_EXTRA_KEY = "FILM_EXTRA_KEY"

class FilmFragment : Fragment(), FilmsContract.View {

    private var binding: FragmentFilmBinding? = null
    private var film: Film? = null
    private var presenter: FilmPresenterImpl? = null

    companion object {
        fun getInstance(film: Film?): FilmFragment {
            val fragment = FilmFragment()
            val bundle = Bundle()
            bundle.putParcelable(FILM_EXTRA_KEY, film)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        film = arguments?.getParcelable(FILM_EXTRA_KEY)
        film?.let {
            presenter = FilmPresenterImpl(it)
            presenter!!.attach(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilmBinding.bind(view)

        initializeInfo(film)
    }

    private fun initializeInfo(film: Film?) {
        film?.let {
            binding!!.filmTitleTextView.text = it.name
            binding!!.durationTextView.text =
                "${context?.getString(R.string.film_duration)}: ${it.duration}"
            binding!!.filmRateTextView.text = it.rate.toString()
            binding!!.originalTitleTextView.text = "${it.name} (${it.year})"
            binding!!.budgetTextView.text = context?.getString(R.string.fake_budget)
            binding!!.feesTextView.text = context?.getString(R.string.fake_fees)
            binding!!.descriptionTextView.text = context?.getString(R.string.fake_description)
            binding!!.genresAndYearTextView.text = "${it.year},"
            binding!!.favoriteToggleButton.setOnClickListener {
                presenter!!.onClickHeart()
            }
            renderHeart(it.isFavorite)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun renderHeart(check: Boolean) {
        binding!!.favoriteToggleButton.isChecked = check
    }

}
package ru.malinoil.films.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.malinoil.films.MyApplication
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentFilmBinding
import ru.malinoil.films.model.contracts.FilmsContract
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.model.repositories.impls.room.NoteDTO
import ru.malinoil.films.presenter.FilmPresenterImpl

class FilmFragment : Fragment(), FilmsContract.View {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private var film: FilmEntity? = null
    private var note: NoteDTO? = null
    private var presenter: FilmPresenterImpl? = null
    private val app: MyApplication by lazy { activity?.application as MyApplication }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        private const val FILM_EXTRA_KEY = "FILM_EXTRA_KEY"
        fun getInstance(film: FilmEntity?): FilmFragment {
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
            presenter = FilmPresenterImpl(app.retrofit, app.database, it)
            presenter?.attach(this)
            presenter?.getFullInfoAboutFilm(it.id)
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
        _binding = FragmentFilmBinding.bind(view)

        film?.let { currentFilm ->
            fillFields(currentFilm)
            presenter?.getNote(currentFilm.id)
        }
        binding.addNoteButton.setOnClickListener {
            buildDialog()
        }
    }

    private fun fillFields(film: FilmEntity) {
        initializeFields(film)
        setClickListeners(film)
        renderHeart(film.isFavorite)
        Glide.with(requireContext()).load("$IMAGE_BASE_URL${film.poster}")
            .into(binding.filmImageView)
    }

    @SuppressLint("SetTextI18n")
    private fun initializeFields(film: FilmEntity) {
        val year = film.releaseDate.split("-")[0]
        binding.apply {
            filmTitleTextView.text = film.name
            filmRateTextView.text = film.rate.toString()
            originalTitleTextView.text = "${film.original} ($year)"
            budgetTextView.text =
                "${requireContext().getString(R.string.prefix_budget)} ${getMoneyString(film.budget)}"
            feesTextView.text =
                "${requireContext().getString(R.string.prefix_fees)} ${getMoneyString(film.fees)}"
            descriptionTextView.text = film.description
            genresAndYearTextView.text = "$year${film.getGenresString()}"
        }
    }

    // Пока думаю над этим методом, чтобы он был более лаконичным
    private fun getMoneyString(money: String?): String {
        return when (money) {
            null -> requireContext().getString(R.string.loading)
            "0" -> requireContext().getString(R.string.undefined)
            else -> {
                when (getDigitMoney(money.toDouble(), 0)) {
                    1 -> String.format("\$%.1f тыс.", (money.toDouble() / 1_000))
                    2 -> String.format("\$%.1f млн.", (money.toDouble() / 1_000_000))
                    3 -> String.format("\$%.1f млрд.", (money.toDouble() / 1_000_000_000))
                    else -> String.format("\$%.1f", money)
                }
            }
        }
    }

    private fun getDigitMoney(money: Double, counter: Int): Int {
        if (money >= 1_000) {
            return getDigitMoney(money / 1_000, counter) + 1
        }
        return counter
    }

    private fun setClickListeners(currentFilm: FilmEntity) {
        binding.favoriteToggleButton.setOnClickListener {
            presenter!!.onClickHeart()
        }
        binding.deleteNoteButton.setOnClickListener {
            presenter?.deleteNote(currentFilm.id)
        }
    }

    private fun buildDialog() {
        val editText = EditText(context)
        AlertDialog.Builder(context)
            .setTitle(context?.getString(R.string.edit_note))
            .setView(editText)
            .setPositiveButton(
                context?.getString(R.string.ok_button)
            ) { _, _ ->
                presenter?.updateNote(NoteDTO(null, film!!.id, editText.text.toString()))
            }
            .setNegativeButton(
                context?.getString(R.string.cancel_button)
            ) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun renderHeart(check: Boolean) {
        binding.favoriteToggleButton.isChecked = check
    }

    override fun renderNote(note: NoteDTO?) {
        note?.let {
            this.note = it
            binding.noteTextView.text = it.text
            setVisibility(View.VISIBLE)
        }
    }

    override fun updateInfoAboutFilm(film: FilmEntity) {
        initializeFields(film)
    }

    override fun deleteNote() {
        binding.noteTextView.text = ""
        setVisibility(View.INVISIBLE)
        note = null
    }

    private fun setVisibility(visibility: Int) {
        binding.apply {
            deleteNoteButton.visibility = visibility
            noteTextView.visibility = visibility
        }
    }

}
package ru.malinoil.films.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import ru.malinoil.films.MyApplication
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentFilmBinding
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.model.repositories.impls.FilmsContract
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
            presenter = FilmPresenterImpl(app.database, it)
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
        _binding = FragmentFilmBinding.bind(view)

        initializeInfo(film)
        presenter?.getNote(film!!.id)

        binding!!.addNoteButton.setOnClickListener {
            buildDialog()
        }
        binding!!.deleteNoteButton.setOnClickListener {
            film?.id?.let { filmId -> presenter?.deleteNote(filmId) }
        }
    }

    private fun initializeInfo(film: FilmEntity?) {
        film?.let {
            val year = it.releaseDate.split("-")[0]
            binding.apply {
                filmTitleTextView.text = it.name
                filmRateTextView.text = it.rate.toString()
                originalTitleTextView.text = "${it.original} ($year)"
                budgetTextView.text = context?.getString(R.string.fake_budget)
                feesTextView.text = context?.getString(R.string.fake_fees)
                descriptionTextView.text = it.description
                genresAndYearTextView.text = "$year,"
                favoriteToggleButton.setOnClickListener {
                    presenter!!.onClickHeart()
                }
                renderHeart(it.isFavorite)
            }
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
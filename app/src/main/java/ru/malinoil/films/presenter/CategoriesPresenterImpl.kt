package ru.malinoil.films.presenter

import android.os.Handler
import android.os.Looper
import ru.malinoil.films.CategoriesContract
import ru.malinoil.films.model.CategoriesRepository
import ru.malinoil.films.model.JSONParser
import ru.malinoil.films.model.Loader
import ru.malinoil.films.model.TitleType
import ru.malinoil.films.model.entities.CategoryEntity
import javax.net.ssl.HttpsURLConnection

class CategoriesPresenterImpl : CategoriesContract.Presenter {
    private var view: CategoriesContract.View? = null
    private val categoriesRepo: CategoriesRepository = CategoriesRepository.getInstance()

    override fun attach(view: CategoriesContract.View) {
        this.view = view
    }

    override fun initializeCategories() {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val connection: HttpsURLConnection? = null
            try {
                val listTypes: List<TitleType> = listOf(TitleType.HIGH_RATE, TitleType.NOW)
                listTypes.forEach {
                    categoriesRepo.addCategory(
                        CategoryEntity(
                            it,
                            JSONParser.parseFilms(Loader.getResponseString(it))
                        )
                    )
                }
                handler.post {
                    categoriesRepo.getCategories()?.let { view?.updateData(it) }
                }
            } finally {
                connection?.disconnect()
            }

        }.start()

    }

    override fun detach() {
        view = null
    }
}
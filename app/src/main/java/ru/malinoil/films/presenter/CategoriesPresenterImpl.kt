package ru.malinoil.films.presenter

import android.util.Log
import retrofit2.Retrofit
import ru.malinoil.films.model.entities.CategoryEntity
import ru.malinoil.films.model.entities.TitleType
import ru.malinoil.films.model.repositories.FilmsRepository
import ru.malinoil.films.model.repositories.impls.CategoriesContract
import ru.malinoil.films.model.repositories.impls.network.NetworkFilmsRepositoryImpl

class CategoriesPresenterImpl(private val retrofit: Retrofit) : CategoriesContract.Presenter {
    private var view: CategoriesContract.View? = null
    private val filmsRepository: FilmsRepository by lazy { NetworkFilmsRepositoryImpl(retrofit) }

    override fun attach(view: CategoriesContract.View) {
        this.view = view
    }

    override fun getCategories(): List<CategoryEntity> {
        val list = emptyList<CategoryEntity>().toMutableList()
        val listTypes: List<TitleType> = listOf(TitleType.HIGH_RATE, TitleType.NOW)
        listTypes.forEach { type ->
            filmsRepository.getFilmsForType(type, {
                list.add(CategoryEntity(type, it.results))
                view?.updateData(list)
            }, { thr ->
                thr.message?.let { msg -> Log.d("@@@", msg) }
            })
        }
        return list
    }

    override fun detach() {
        view = null
    }
}
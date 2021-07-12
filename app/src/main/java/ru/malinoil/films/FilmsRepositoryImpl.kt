package ru.malinoil.films

import androidx.lifecycle.MutableLiveData

class FilmsRepositoryImpl {
    var newFilmsList = MutableLiveData<MutableList<Film>>()
    var comingFilmsList = MutableLiveData<MutableList<Film>>()

    init {
        newFilmsList.value = ArrayList()
        comingFilmsList.value = ArrayList()
        initializeExamplesLists()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun initializeExamplesLists() {
        newFilmsList.value?.run {
            add(Film("Форсаж 9", 2021, 6.2f, 100, null))
            add(Film("Интерстеллар", 2014, 8.9f, 110, null))
            add(Film("Марсианин", 2013, 8.6f, 120, null))
            add(Film("Знамение", 2007, 7.1f, 100, null))
        }
        comingFilmsList.value?.run {
            add(Film("Форсаж 10", 2022, null, null, null))
            add(Film("Интерстеллар 2", 2024, null, null, null))
            add(Film("Звонок 4", 2077, null, null, null))
            add(Film("Шкатулка проклятия", 2022, 5.2f, 105, null))
        }
    }

}
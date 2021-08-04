package ru.malinoil.films.model

import ru.malinoil.films.model.entities.CategoryEntity

object CategoriesRepository {

    private var list: MutableList<CategoryEntity>? = null

    fun getInstance(): CategoriesRepository {
        if (list == null) {
            list = emptyList<CategoryEntity>().toMutableList()
        }
        return this
    }

    fun addCategory(category: CategoryEntity) {
        list?.add(category)
    }

    fun getCategories(): MutableList<CategoryEntity>? {
        return list
    }

}
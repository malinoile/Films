package ru.malinoil.films.model.contracts

import ru.malinoil.films.model.entities.CategoryEntity

interface CategoriesContract {
    interface View {
        fun updateData(list: List<CategoryEntity>)
    }

    interface Presenter {
        fun attach(view: View)
        fun getCategories(): List<CategoryEntity>
        fun detach()
    }
}
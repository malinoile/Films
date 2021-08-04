package ru.malinoil.films

import ru.malinoil.films.model.entities.CategoryEntity

interface CategoriesContract {
    interface View {
        fun updateData(list: MutableList<CategoryEntity>)
    }

    interface Presenter {
        fun attach(view: View)
        fun initializeCategories()
        fun detach()
    }
}
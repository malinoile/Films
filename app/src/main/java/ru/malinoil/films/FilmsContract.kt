package ru.malinoil.films

interface FilmsContract {
    interface View {
        fun renderHeart(check: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun onClickHeart()
        fun detach()
    }
}
package ru.malinoil.films.model

object ComponentsRepository {

    private var list: MutableList<HomeComponent>? = null

    fun getInstance(): ComponentsRepository {
        if (list == null) {
            list = emptyList<HomeComponent>().toMutableList()
            val filmsRepo = FilmsRepository.getInstance()
            list!!.run {
                add(HomeComponent(TitleType.NEWEST, filmsRepo.getFilmsByType(TitleType.NEWEST)))
                add(
                    HomeComponent(
                        TitleType.COMING_SOON,
                        filmsRepo.getFilmsByType(TitleType.COMING_SOON)
                    )
                )
                add(
                    HomeComponent(
                        TitleType.HIGH_RATE,
                        filmsRepo.getFilmsByType(TitleType.HIGH_RATE)
                    )
                )
                add(HomeComponent(TitleType.NOW, filmsRepo.getFilmsByType(TitleType.NOW)))
            }
        }
        return this
    }

    fun addComponent(component: HomeComponent) {
        list?.add(component)
    }

    fun getComponents(): MutableList<HomeComponent>? {
        return list
    }

}
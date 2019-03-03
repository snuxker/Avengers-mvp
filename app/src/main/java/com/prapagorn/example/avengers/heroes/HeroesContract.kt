package com.prapagorn.example.avengers.heroes

import com.prapagorn.example.avengers.BasePresenter
import com.prapagorn.example.avengers.BaseScreen
import com.prapagorn.example.avengers.entities.Hero

interface HeroesContract {

    interface Screen : BaseScreen<Presenter> {
        fun showLoading(active: Boolean)
        fun showNoHeroes(active: Boolean)
        fun startHeroBio(heroId: String)
        fun showHeroes(heroes: List<Hero>)
        fun showLoadingError()
    }

    interface Presenter : BasePresenter {
        fun loadHeroes()
        fun openHeroBio(hero: Hero)
    }
}
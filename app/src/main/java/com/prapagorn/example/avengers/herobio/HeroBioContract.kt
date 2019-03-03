package com.prapagorn.example.avengers.herobio

import com.prapagorn.example.avengers.BasePresenter
import com.prapagorn.example.avengers.BaseScreen
import com.prapagorn.example.avengers.entities.HeroBio

interface HeroBioContract {

    interface Screen : BaseScreen<Presenter> {
        fun showLoading(active: Boolean)
        fun showHeroBio(heroBio: HeroBio)
        fun showLoadingError()
    }

    interface Presenter : BasePresenter {
        fun loadHeroBio(heroId: String)
    }
}
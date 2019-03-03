package com.prapagorn.example.avengers.heroes

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.prapagorn.example.avengers.BaseActivity
import com.prapagorn.example.avengers.R
import com.prapagorn.example.avengers.entities.Hero
import com.prapagorn.example.avengers.herobio.HeroBioActivity
import com.prapagorn.example.avengers.util.showSnackBar
import kotlinx.android.synthetic.main.activity_hero.*



class HeroesActivity : BaseActivity(), HeroesContract.Screen {

    override lateinit var presenter: HeroesContract.Presenter

    override val layoutResId = R.layout.activity_hero

    private lateinit var heroesAdapter: HeroesAdapter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setupView() {
        presenter = HeroPresenter(this
            , FirebaseDatabase.getInstance().reference.child("data"))
        setupList()
    }

    override fun showHeroes(heroes: List<Hero>) {
        heroesAdapter.let {
            it.heroList = heroes
            it.notifyDataSetChanged()
        }
    }

    override fun showLoading(active: Boolean) {
        pbLoading.visibility = if (active) {
            View.VISIBLE
        }else {
            View.GONE
        }
    }

    override fun showNoHeroes(active: Boolean) {
        tvShowNoHeroes.visibility = if (active) {
            View.VISIBLE
        }else {
            View.GONE
        }
    }

    override fun startHeroBio(heroId: String) {
        val intent = Intent(this, HeroBioActivity::class.java).apply {
            putExtra(HeroBioActivity.EXTRA_HERO_ID, heroId)
        }
        startActivity(intent)
    }

    override fun showLoadingError() {
        clRootView.showSnackBar(getString(R.string.common_loading_error))
    }

    private fun setupList() {
        heroesAdapter = HeroesAdapter().apply {
            onClick = {
                presenter.openHeroBio(it)
            }
        }
        rvHeroes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = heroesAdapter
        }
    }
}

package com.prapagorn.example.avengers.herobio

import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.prapagorn.example.avengers.BaseActivity
import com.prapagorn.example.avengers.R
import com.prapagorn.example.avengers.entities.HeroBio
import com.prapagorn.example.avengers.util.showSnackBar
import kotlinx.android.synthetic.main.activity_hero_bio.*

class HeroBioActivity : BaseActivity(), HeroBioContract.Screen {

    override val layoutResId = R.layout.activity_hero_bio

    override lateinit var presenter: HeroBioContract.Presenter

    companion object {
        const val EXTRA_HERO_ID = "HERO_ID"
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setupView() {
        super.setupView()
        val heroId = intent.getStringExtra(EXTRA_HERO_ID) ?: ""
        presenter = HeroBioPresenter(this,
            FirebaseDatabase.getInstance().reference.child("data").child(heroId),
            heroId)
    }

    override fun showLoading(active: Boolean) {
        pbLoading.visibility = if (active) {
            View.VISIBLE
        }else {
            View.GONE
        }
    }

    override fun showHeroBio(heroBio: HeroBio) {
        heroBio.bind()
    }

    override fun showLoadingError() {
        nsRootView.showSnackBar(getString(R.string.common_loading_error))
    }

    private fun HeroBio.bind() {
        Glide.with(this@HeroBioActivity)
            .load(this.imgUrl)
            .into(ivHero)
        tvName.text = this.name
        tvBioShort.text = this.bioShort
        tvBio.text = this.bio
    }
}
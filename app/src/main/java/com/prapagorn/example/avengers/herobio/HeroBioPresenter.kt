package com.prapagorn.example.avengers.herobio

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.prapagorn.example.avengers.entities.HeroBio
import com.prapagorn.example.avengers.entities.HeroData

class HeroBioPresenter(val screen: HeroBioContract.Screen,
                       private val database: DatabaseReference,
                       private val heroId: String)
    : HeroBioContract.Presenter {

    override fun start() {
        if (heroId.isNotEmpty()) {
            loadHeroBio(heroId)
        }else {
            screen.showLoadingError()
        }
    }

    override fun loadHeroBio(heroId: String) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                screen.showLoadingError()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val heroData = dataSnapshot.getValue<HeroData>(HeroData::class.java)
                val heroBio = heroData?.let { HeroBio.fromHeroData(it) }
                screen.showLoading(false)
                if (heroBio != null) {
                    screen.showHeroBio(heroBio)
                }else {
                    screen.showLoadingError()
                }
            }
        })
    }
}
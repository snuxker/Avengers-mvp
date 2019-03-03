package com.prapagorn.example.avengers.heroes

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.prapagorn.example.avengers.entities.Hero
import com.prapagorn.example.avengers.entities.HeroData

class HeroPresenter(val screen: HeroesContract.Screen, private val database: DatabaseReference)
    : HeroesContract.Presenter {

    override fun start() {
        loadHeroes()
    }

    override fun loadHeroes() {
        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                screen.showLoadingError()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val heroList = mutableListOf<Hero>()
                dataSnapshot.children.mapNotNullTo(heroList) {
                    val hero = it.getValue<HeroData>(HeroData::class.java)
                    hero?.let { Hero.fromHeroData(it) }
                }
                screen.showLoading(false)
                if (heroList.isNotEmpty()) {
                    screen.showNoHeroes(false)
                    screen.showHeroes(heroList)
                }else {
                    screen.showNoHeroes(true)
                }
            }
        })
    }

    override fun openHeroBio(hero: Hero) {
        screen.startHeroBio(hero.id)
    }
}
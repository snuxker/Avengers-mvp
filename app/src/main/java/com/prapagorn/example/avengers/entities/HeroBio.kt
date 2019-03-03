package com.prapagorn.example.avengers.entities

data class HeroBio(
    var id: String,
    var name: String,
    var bio: String,
    var bioShort: String,
    var imgUrl: String
) {
    companion object {

        fun fromHeroData(heroData: HeroData) = HeroBio(
            id = heroData.id,
            name = heroData.name,
            bio = heroData.bio,
            bioShort = heroData.bioShort ?: heroData.bio,
            imgUrl = heroData.imgUrl
        )
    }
}

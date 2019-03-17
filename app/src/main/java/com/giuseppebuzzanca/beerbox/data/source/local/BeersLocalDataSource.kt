package com.giuseppebuzzanca.beerbox.data.source.local

import com.giuseppebuzzanca.beerbox.data.Beer
import com.giuseppebuzzanca.beerbox.data.source.BeersDataSource
import com.giuseppebuzzanca.beerbox.util.BeerType
import io.reactivex.Single
import javax.inject.Inject

class BeersLocalDataSource @Inject constructor(private val beersDao: BeersDao) : BeersDataSource {

    override fun getBeers(page: Int, perPage: Int): Single<List<Beer>>? = null

    override fun getBeers() = beersDao.getBeers()

    override fun insertBeers(beers: List<Beer>) {
        beersDao.insertBeers(beers)
    }

    override fun getBeerTypes() = BeerType.values()
        .filter { it != BeerType.UNKNOWN }
        .sortedBy { it.displayName }

}
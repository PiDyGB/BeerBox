package com.giuseppebuzzanca.beerbox.data.source

import androidx.lifecycle.LiveData
import com.giuseppebuzzanca.beerbox.data.Beer
import com.giuseppebuzzanca.beerbox.util.BeerType
import io.reactivex.Single

interface BeersDataSource {

    fun getBeers(page: Int, perPage: Int): Single<List<Beer>>?

    fun insertBeers(beers: List<Beer>)

    fun getBeers(): LiveData<List<Beer>>?

    fun getBeerTypes(): List<BeerType>?
}
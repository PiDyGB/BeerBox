package com.giuseppebuzzanca.beerbox.data.source.remote

import androidx.lifecycle.LiveData
import com.giuseppebuzzanca.beerbox.data.Beer
import com.giuseppebuzzanca.beerbox.data.source.BeersDataSource
import com.giuseppebuzzanca.beerbox.util.BeerType
import io.reactivex.Single
import javax.inject.Inject

class BeersRemoteDataSource @Inject constructor(private val punkServices: PunkServices) : BeersDataSource {

    override fun insertBeers(beers: List<Beer>) {}

    override fun getBeers(): LiveData<List<Beer>>? = null

    override fun getBeers(page: Int, perPage: Int): Single<List<Beer>> = punkServices.getBeers(page, perPage)

    override fun getBeerTypes(): List<BeerType>? = null
}
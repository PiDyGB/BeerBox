package com.giuseppebuzzanca.beerbox.data.source.remote

import com.giuseppebuzzanca.beerbox.data.Beer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkServices {

    @GET("beers")
    fun getBeers(@Query("page") page: Int, @Query("per_page") perPage: Int): Single<List<Beer>>

}
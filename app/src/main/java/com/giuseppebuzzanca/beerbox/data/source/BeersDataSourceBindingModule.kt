package com.giuseppebuzzanca.beerbox.data.source

import com.giuseppebuzzanca.beerbox.data.source.local.BeersLocalDataSource
import com.giuseppebuzzanca.beerbox.data.source.remote.BeersRemoteDataSource
import com.giuseppebuzzanca.beerbox.di.qualifier.LocalDataSource
import com.giuseppebuzzanca.beerbox.di.qualifier.RemoteDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class BeersDataSourceBindingModule {

    @Binds
    @RemoteDataSource
    abstract fun bindsBeersRemoteDataSource(beersRemoteDataSource: BeersRemoteDataSource): BeersDataSource


    @Binds
    @LocalDataSource
    abstract fun bindsBeersLocalDataSource(beersRemoteDataSource: BeersLocalDataSource): BeersDataSource

}
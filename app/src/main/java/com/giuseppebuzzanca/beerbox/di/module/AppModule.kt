package com.giuseppebuzzanca.beerbox.di.module

import android.content.Context
import com.giuseppebuzzanca.beerbox.BeersBoxApplication
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindsContext(application: BeersBoxApplication): Context
}
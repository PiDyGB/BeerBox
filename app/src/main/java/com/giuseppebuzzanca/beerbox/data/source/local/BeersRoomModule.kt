package com.giuseppebuzzanca.beerbox.data.source.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BeersRoomModule {

    @Singleton
    @Provides
    fun providesBeersDatabase(context: Context) =
        Room.databaseBuilder(context, BeersDatabase::class.java, "beers-database").build()

    @Singleton
    @Provides
    fun providesBeersDao(beersDatabase: BeersDatabase) = beersDatabase.beersDao()

}
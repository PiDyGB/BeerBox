package com.giuseppebuzzanca.beerbox.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giuseppebuzzanca.beerbox.data.Beer

@Database(entities = [Beer::class], version = 1)
abstract class BeersDatabase : RoomDatabase() {

    abstract fun beersDao(): BeersDao

}
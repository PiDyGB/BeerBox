package com.giuseppebuzzanca.beerbox.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.giuseppebuzzanca.beerbox.data.Beer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeersDaoTest {

    private val theBeer = Beer(
        id = 1,
        name = "Test",
        tagLine = "Description",
        description = "Description",
        imageUrl = "http://fake.url.com/fakepath",
        ebc = 16f
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var database: BeersDatabase? = null

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            BeersDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        database?.close()
    }

    @Test
    fun insertAndGetBeer() {
        database?.beersDao()?.insertBeers(listOf(theBeer))

        database?.beersDao()?.getBeers()?.observeForever {
            assertThat(it.size, `is`(1))
            val beer = it[0]
            assertEquals(beer, theBeer)
        }

    }

}
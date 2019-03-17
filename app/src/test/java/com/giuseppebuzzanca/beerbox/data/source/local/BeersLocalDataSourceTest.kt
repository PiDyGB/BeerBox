package com.giuseppebuzzanca.beerbox.data.source.local

import com.giuseppebuzzanca.beerbox.data.Beer
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BeersLocalDataSourceTest {

    private val beers = listOf<Beer>(mock(Beer::class.java), mock(Beer::class.java))
    @Mock
    private lateinit var beersDao: BeersDao

    @Test
    fun getBeers_dao() {
        val beersLocalDataSource = BeersLocalDataSource(beersDao)
        beersLocalDataSource.getBeers()
        verify(beersDao, times(1)).getBeers()
    }

    @Test
    fun insertBeers_dao() {
        val beersLocalDataSource = BeersLocalDataSource(beersDao)
        beersLocalDataSource.insertBeers(beers)
        verify(beersDao, times(1)).insertBeers(beers)
    }
}
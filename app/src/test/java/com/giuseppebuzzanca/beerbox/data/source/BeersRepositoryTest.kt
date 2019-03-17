package com.giuseppebuzzanca.beerbox.data.source

import com.giuseppebuzzanca.beerbox.data.Beer
import com.giuseppebuzzanca.beerbox.data.source.local.BeersLocalDataSource
import com.giuseppebuzzanca.beerbox.data.source.remote.BeersRemoteDataSource
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BeersRepositoryTest {

    private val beers = listOf<Beer>(Mockito.mock(Beer::class.java), Mockito.mock(Beer::class.java))

    @Mock
    private lateinit var localDataSource: BeersLocalDataSource
    @Mock
    private lateinit var remoteDataSource: BeersRemoteDataSource

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { TestScheduler() }
        RxJavaPlugins.setSingleSchedulerHandler { TestScheduler() }
        RxJavaPlugins.setNewThreadSchedulerHandler { TestScheduler() }
        RxJavaPlugins.setComputationSchedulerHandler { TestScheduler() }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
    }

    @Test
    fun getBeers_LocalDataSource() {
        val beersRepository = BeersRepository(remoteDataSource, localDataSource)
        beersRepository.getBeers(20) {}
        verify(localDataSource, times(1)).getBeers()
    }

    @Test
    fun getBeers_RemoteDataSourceAndInsert() {
        val beersRepository = BeersRepository(remoteDataSource, localDataSource)
        whenever(remoteDataSource.getBeers(1, 20)).thenReturn(Single.just(beers))
        beersRepository.getBeers(20) {}
        assertThat(beersRepository.isLoading, `is`(false))
        assertThat(beersRepository.reachedLastBear, `is`(false))
        assertThat(beersRepository.currentPage, `is`(1))
        verify(remoteDataSource, times(1)).getBeers(1, 20)
        verify(localDataSource, times(1)).insertBeers(beers)
    }

    @Test
    fun getBeerTypes_LocalDataSource() {
        val beersRepository = BeersRepository(remoteDataSource, localDataSource)
        beersRepository.getBeerTypes()
        verify(localDataSource, times(1)).getBeerTypes()
    }
}
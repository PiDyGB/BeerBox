package com.giuseppebuzzanca.beerbox.ui.beers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.giuseppebuzzanca.beerbox.data.source.BeersDataSource
import com.giuseppebuzzanca.beerbox.data.source.BeersRepository
import com.giuseppebuzzanca.beerbox.data.source.local.BeersDao
import com.giuseppebuzzanca.beerbox.data.source.local.BeersLocalDataSource
import com.giuseppebuzzanca.beerbox.util.BeerType
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BeersViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<UIBeerItem>>
    @Mock
    private lateinit var beersRepository: BeersRepository

    private val beers = listOf(
        UIBeerItem(1, "Test1", "tagLine1", "desc1", "imageUri1", BeerType.BLONDE_ALE),
        UIBeerItem(2, "Test2", "tagLine2", "desc2", "imageUri2", BeerType.AMBER_ALE)
    )
    private var beerSource = MutableLiveData<List<UIBeerItem>>()

    @Test
    fun viewState_ListOfUIBeerItem() {
        whenever(beersRepository.getBeers<UIBeerItem>(any(), any())).doReturn(beerSource)
        beerSource.value = beers

        val viewModel = BeersViewModel(beersRepository)
        viewModel.beers.observeForever(observer)

        verify(observer).onChanged(beers)
    }

    @Test
    fun viewState_ListOfBeerTypes() {
        val dataSource = Mockito.mock(BeersDataSource::class.java)
        val dao = Mockito.mock(BeersDao::class.java)
        val repo = BeersRepository(dataSource, BeersLocalDataSource(dao))
        val filteredBeerTypes = BeerType.values().filter { it != BeerType.UNKNOWN }.sortedBy { it.displayName }

        val viewModel = BeersViewModel(repo)
        val beerTypes = viewModel.beerTypes

        assert(filteredBeerTypes == beerTypes)
    }

    @Test
    fun addFilter_UpdateViewState() {
        whenever(beersRepository.getBeers<UIBeerItem>(any(), any())).doReturn(beerSource)
        beerSource.value = beers

        val viewModel = BeersViewModel(beersRepository)
        viewModel.beers.observeForever(observer)
        viewModel.addFilter(BeerType.AMBER_ALE)

        assert(viewModel.beers.value?.getOrNull(0)?.id == 2)
    }

    @Test
    fun removeFilter_UpdateViewState() {
        whenever(beersRepository.getBeers<UIBeerItem>(any(), any())).doReturn(beerSource)
        beerSource.value = beers

        val viewModel = BeersViewModel(beersRepository)
        viewModel.beers.observeForever(observer)
        viewModel.addFilter(BeerType.AMBER_ALE)
        viewModel.removeFilter(BeerType.AMBER_ALE)

        assert(viewModel.beers.value?.count() == beers.count())
    }

    @Test
    fun onQuery_UpdateViewState() {
        whenever(beersRepository.getBeers<UIBeerItem>(any(), any())).doReturn(beerSource)
        beerSource.value = beers

        val viewModel = BeersViewModel(beersRepository)
        viewModel.beers.observeForever(observer)
        viewModel.onQuery("Test2")

        verify(observer).onChanged(beers)
        assert(viewModel.beers.value?.getOrNull(0)?.id == 2)
    }

    @Test
    fun getBeers_CallRepository() {
        whenever(beersRepository.getBeers<UIBeerItem>(any(), any())).doReturn(beerSource)
        beerSource.value = beers

        val viewModel = BeersViewModel(beersRepository)
        viewModel.beers.observeForever(observer)
        viewModel.getBeers(5, 11, 20)

        verify(observer).onChanged(beers)
    }
}
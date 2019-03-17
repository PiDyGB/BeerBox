package com.giuseppebuzzanca.beerbox.util

import androidx.lifecycle.ViewModel
import com.giuseppebuzzanca.beerbox.ui.beers.BeersViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import javax.inject.Provider

class ViewModelsFactoryTest {

    @Mock
    private lateinit var viewModel: ViewModel
    @Mock
    private lateinit var beersViewModel: BeersViewModel
    @Mock
    private lateinit var viewModelProvider: Provider<ViewModel>
    @Mock
    private lateinit var beersViewModelProvider: Provider<ViewModel>
    private var viewModelsFactory: ViewModelsFactory? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val mapOf =
            mapOf(ViewModel::class.java to viewModelProvider, BeersViewModel::class.java to beersViewModelProvider)
        viewModelsFactory = ViewModelsFactory(mapOf)
    }

    @Test
    fun create() {
        `when`(viewModelProvider.get()).thenReturn(viewModel)
        val model = viewModelsFactory?.create(ViewModel::class.java)
        assertEquals(model, viewModel)

        `when`(beersViewModelProvider.get()).thenReturn(beersViewModel)
        val bViewModel = viewModelsFactory?.create(BeersViewModel::class.java)
        assertEquals(bViewModel, beersViewModel)
    }
}
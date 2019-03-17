package com.giuseppebuzzanca.beerbox.ui.beers

import androidx.lifecycle.ViewModel
import com.giuseppebuzzanca.beerbox.di.qualifier.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class BeersViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(BeersViewModel::class)
    abstract fun providesBeersViewModel(marketSelectorViewModel: BeersViewModel): ViewModel
}
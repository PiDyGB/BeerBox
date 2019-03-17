package com.giuseppebuzzanca.beerbox.ui

import com.giuseppebuzzanca.beerbox.di.qualifier.FragmentScope
import com.giuseppebuzzanca.beerbox.ui.beers.BeersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentsBindingModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun beersFragment(): BeersFragment

}
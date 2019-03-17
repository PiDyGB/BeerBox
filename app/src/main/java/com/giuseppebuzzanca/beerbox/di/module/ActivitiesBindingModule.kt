package com.giuseppebuzzanca.beerbox.di.module

import com.giuseppebuzzanca.beerbox.di.qualifier.ActivityScoped
import com.giuseppebuzzanca.beerbox.ui.MainActivity
import com.giuseppebuzzanca.beerbox.ui.MainFragmentsBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainFragmentsBindingModule::class])
    abstract fun mainActivity(): MainActivity
}
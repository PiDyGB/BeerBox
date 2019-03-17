package com.giuseppebuzzanca.beerbox.di

import com.giuseppebuzzanca.beerbox.BeersBoxApplication
import com.giuseppebuzzanca.beerbox.data.source.BeersDataSourceBindingModule
import com.giuseppebuzzanca.beerbox.data.source.local.BeersRoomModule
import com.giuseppebuzzanca.beerbox.di.module.ActivitiesBindingModule
import com.giuseppebuzzanca.beerbox.di.module.AppModule
import com.giuseppebuzzanca.beerbox.di.module.NetModule
import com.giuseppebuzzanca.beerbox.ui.beers.BeersViewModelBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetModule::class,
        BeersDataSourceBindingModule::class,
        BeersRoomModule::class,
        BeersDataSourceBindingModule::class,
        BeersViewModelBindingModule::class,
        ActivitiesBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<BeersBoxApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BeersBoxApplication): Builder

        fun build(): AppComponent
    }

}
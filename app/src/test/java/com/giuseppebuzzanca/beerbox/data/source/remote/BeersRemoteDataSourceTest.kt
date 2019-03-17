package com.giuseppebuzzanca.beerbox.data.source.remote

import com.giuseppebuzzanca.beerbox.Utils
import com.giuseppebuzzanca.beerbox.data.Beer
import com.giuseppebuzzanca.beerbox.di.module.NetModule
import io.reactivex.functions.Consumer
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class BeersRemoteDataSourceTest {

    private val netModule = NetModule()
    private val mockWebServer = MockWebServer()
    private val retrofit =
        netModule.providesRetrofit(mockWebServer.url("").toString(), OkHttpClient(), RxJava2CallAdapterFactory.create())
    private val punkServices = netModule.providesPunkServices(retrofit)
    private val beersDataSource = BeersRemoteDataSource(punkServices)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getBeers_PageCalled_Success() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Utils.getJson("two_beers.json")))

        var results: List<Beer>? = null
        beersDataSource.getBeers(1, 2).subscribe(Consumer { results = it })
        assertThat(results, notNullValue())
        assertThat(results?.size, `is`(2))

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/beers?page=1&per_page=2"))
        assertThat(request.requestUrl.queryParameter("page"), `is`("1"))
        assertThat(request.requestUrl.queryParameter("per_page"), `is`("2"))
    }


    @Test
    fun getBeers_PageCalled_Fail() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        var results: List<Beer>? = null
        beersDataSource.getBeers(1, 2).subscribe({ results = it }, { })
        assertThat(results, nullValue())

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/beers?page=1&per_page=2"))
        assertThat(request.requestUrl.queryParameter("page"), `is`("1"))
        assertThat(request.requestUrl.queryParameter("per_page"), `is`("2"))
    }
}
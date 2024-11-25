package com.sukajee.cryptotracker.di

import com.sukajee.cryptotracker.core.data.networking.HttpClientFactory
import com.sukajee.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.sukajee.cryptotracker.crypto.domain.CoinDataSource
import com.sukajee.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single { HttpClientFactory.create(CIO.create()) }

    single { RemoteCoinDataSource(get()) }.bind<CoinDataSource>()
    // Or this below one is better because we don't need to change here if we add more
    // parameters in the RemoteCoinDataSource later.
    // singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    // This means when we request the for the abstraction (CoinDataSource) then please inject
    // this implementation which is RemoteCoinDataSource.

    viewModelOf(::CoinListViewModel)
}
package com.sukajee.cryptotracker.crypto.domain

import com.sukajee.cryptotracker.core.domain.util.NetworkError
import com.sukajee.cryptotracker.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}
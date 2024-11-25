package com.sukajee.cryptotracker.crypto.data.networking

import com.sukajee.cryptotracker.core.data.networking.constructUrl
import com.sukajee.cryptotracker.core.data.networking.safeCall
import com.sukajee.cryptotracker.core.domain.util.NetworkError
import com.sukajee.cryptotracker.core.domain.util.Result
import com.sukajee.cryptotracker.core.domain.util.map
import com.sukajee.cryptotracker.crypto.data.mappers.toCoin
import com.sukajee.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.sukajee.cryptotracker.crypto.domain.Coin
import com.sukajee.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map {response ->
            response.data.map {
                it.toCoin()
            }
        }
    }
}
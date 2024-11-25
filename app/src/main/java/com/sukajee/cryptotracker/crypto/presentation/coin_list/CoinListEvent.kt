package com.sukajee.cryptotracker.crypto.presentation.coin_list

import com.sukajee.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}
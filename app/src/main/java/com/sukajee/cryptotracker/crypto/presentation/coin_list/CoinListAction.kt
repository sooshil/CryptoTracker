package com.sukajee.cryptotracker.crypto.presentation.coin_list

import com.sukajee.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
    data object OnRefresh: CoinListAction
}
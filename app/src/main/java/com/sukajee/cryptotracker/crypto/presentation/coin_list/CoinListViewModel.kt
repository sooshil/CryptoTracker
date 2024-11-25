package com.sukajee.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.cryptotracker.core.domain.util.onError
import com.sukajee.cryptotracker.core.domain.util.onSuccess
import com.sukajee.cryptotracker.crypto.domain.CoinDataSource
import com.sukajee.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            loadCoins()     //Why calling this here?
            /** This can be called in init block as well. But doing that, there will be less control when we test
             * viewmodel as every time viewmodel is created the data is already loaded. So, to achieve the control
             * over when the data should be loaded, it is called here. This will keep on executing the flow as long
             * as there is no more subscriber present plus 5 seconds. This way, if we go out of the screen, then the
             * flow stops after 5 seconds but if we just do the configuration change, then the another subscriber
             * appears within 5 seconds hence not triggering the network call again.
             * There is debate going on this topic: Learn more from here:
             * https://www.youtube.com/watch?v=mNKQ9dc1knI
             * https://proandroiddev.com/loading-initial-data-in-launchedeffect-vs-viewmodel-f1747c20ce62
             * */
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {

            }

            CoinListAction.OnRefresh -> loadCoins()
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { oldState ->
                oldState.copy(
                    isLoading = true
                )
            }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update { oldState ->
                        oldState.copy(
                            coins = coins.map { it.toCoinUi() },
                            isLoading = false
                        )
                    }
                }
                .onError { error ->
                    _state.update { oldState ->
                        oldState.copy(
                            isLoading = false
                        )
                    }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}
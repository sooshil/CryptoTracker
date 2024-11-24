package com.sukajee.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.cryptotracker.crypto.domain.Coin
import com.sukajee.cryptotracker.crypto.presentation.models.CoinUi
import com.sukajee.cryptotracker.crypto.presentation.models.toCoinUi
import com.sukajee.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(85.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = coinUi.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            PriceChange(
                change = coinUi.changePercent24Hr,
            )
        }
    }
}

@Composable
@Preview
@PreviewLightDark
@PreviewDynamicColors
private fun CoinListItemPreview() {
    CryptoTrackerTheme {
        CoinListItem(
            coinUi = previewCoin(),
            onClick = { },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}

internal fun previewCoin(): CoinUi {
    return Coin(
        id = "bitcoin",
        rank = 1,
        name = "Bitcoin",
        symbol = "BTC",
        marketCapUsd = 1234567894452.75,
        priceUsd = 123454.75,
        changePercent24Hr = 0.1
    ).toCoinUi()
}
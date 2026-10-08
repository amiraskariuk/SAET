package com.amir.askari.saet.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amir.askari.saet.R
import com.amir.askari.saet.shared.domain.Product
import com.amir.askari.saet.shared.domain.formatPrice
import com.amir.askari.saet.ui.components.ErrorState
import com.amir.askari.saet.ui.components.HtmlText
import com.amir.askari.saet.ui.components.LabelBadge
import com.amir.askari.saet.ui.components.ProductImage

@Composable
fun ProductDetailDestination(
    onBack: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ProductDetailScreen(state = state, onBack = onBack, onRetry = viewModel::retry)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    state: ProductDetailUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val contentModifier = Modifier.fillMaxSize().padding(innerPadding)
        when (state) {
            ProductDetailUiState.Loading -> Box(modifier = contentModifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            ProductDetailUiState.Error -> ErrorState(
                message = stringResource(R.string.product_detail_error),
                onRetry = onRetry,
                modifier = contentModifier,
            )
            is ProductDetailUiState.Content -> ProductDetailContent(state.product, contentModifier)
        }
    }
}

@Composable
private fun ProductDetailContent(product: Product, modifier: Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ProductImage(
            url = product.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = product.title, style = MaterialTheme.typography.headlineSmall)
            product.colour?.let { colour ->
                Text(
                    text = colour,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(text = formatPrice(product.price), style = MaterialTheme.typography.titleLarge)
            if (product.labels.isNotEmpty()) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    product.labels.forEach { label ->
                        LabelBadge(text = label.text, style = label.style)
                    }
                }
            }
            if (!product.inStock) {
                Text(
                    text = stringResource(R.string.sold_out),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            if (product.sizes.isNotEmpty()) {
                SectionTitle(stringResource(R.string.sizes))
                SizeChips(product.sizes)
            }
            if (product.descriptionHtml.isNotBlank()) {
                SectionTitle(stringResource(R.string.description))
                HtmlText(html = product.descriptionHtml)
            }
            product.type?.let { DetailRow(stringResource(R.string.detail_type), it) }
            product.fit?.let { DetailRow(stringResource(R.string.detail_fit), it) }
            product.sku?.let { DetailRow(stringResource(R.string.detail_sku), it) }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp),
    )
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall)
    }
}

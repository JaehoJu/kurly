package com.jj.kurly.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        homeUiState = homeUiState,
        modifier = modifier
    )
}

@Composable
internal fun HomeScreen(
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.semantics { contentDescription = "Start Screen" }
    ) {
        when (homeUiState) {
            is HomeUiState.Loading -> {
                Loading()
            }

            is HomeUiState.Success -> {
                LazyColumn {
                    sections(homeUiState.sections)
                }
            }
        }
    }
}

private fun LazyListScope.sections(
    sections: List<Section>
) {
    items(sections) { section ->
        val title = section.info.title
        val products = section.products

        when (section.info.type) {
            SectionType.VERTICAL ->
                Section(title = title) {
                    VerticalSection(products)
                }

            SectionType.HORIZONTAL ->
                Section(title = title) {
                    HorizontalSection(products)
                }

            SectionType.GRID ->
                Section(title = title) {
                    GridSection(products)
                }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun VerticalSection(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Text(text = "vertical", modifier = modifier)
}

@Composable
private fun HorizontalSection(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Text(text = "horizontal", modifier = modifier)
}

@Composable
private fun GridSection(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Text(text = "grid", modifier = modifier)
}

@Composable
private fun Section(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Title(text = title)
        content()
    }
}

@Composable
private fun Title(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SectionPreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    Section("title") {
        VerticalSection(listOf(product))
    }
}

@Preview(showBackground = true)
@Composable
private fun VerticalSectionPreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    VerticalSection(listOf(product, product, product))
}

@Preview(showBackground = true)
@Composable
private fun HorizontalSectionPreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    HorizontalSection(listOf(product, product, product))
}

@Preview(showBackground = true)
@Composable
private fun GridSectionPreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    GridSection(listOf(product, product, product))
}
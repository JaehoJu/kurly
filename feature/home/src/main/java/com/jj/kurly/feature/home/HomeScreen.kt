package com.jj.kurly.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
internal fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        homeUiState = homeUiState,
        onWishButtonClick = {}, // TODO: 구현
        modifier = modifier
    )
}

@Composable
internal fun HomeScreen(
    homeUiState: HomeUiState,
    onWishButtonClick: (Product) -> Unit,
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
                    sections(
                        sections = homeUiState.sections,
                        onWishButtonClick = onWishButtonClick
                    )
                }
            }
        }
    }
}

private fun LazyListScope.sections(
    sections: List<Section>,
    onWishButtonClick: (Product) -> Unit
) {
    items(sections) { section ->
        val title = section.info.title
        val products = section.products

        when (section.info.type) {
            SectionType.VERTICAL ->
                Section(title = title) {
                    VerticalSectionList(
                        products = products,
                        onWishButtonClick = onWishButtonClick
                    )
                }

            SectionType.HORIZONTAL ->
                Section(title = title) {
                    HorizontalSectionList(
                        products = products,
                        onWishButtonClick = onWishButtonClick
                    )
                }

            SectionType.GRID ->
                Section(title = title) {
                    GridSectionList(
                        products = products,
                        onWishButtonClick = onWishButtonClick
                    )
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
private fun VerticalSectionList(
    products: List<Product>,
    onWishButtonClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        products.forEach {
            VerticalProductListItem(
                product = it,
                onWishButtonClick = { onWishButtonClick(it) }
            )
        }
    }
}

@Composable
private fun VerticalProductListItem(
    product: Product,
    onWishButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: 저장소 data 와 연결
    var isWished by rememberSaveable { mutableStateOf(false) }

    // TODO: 다른 Composable 로 변경
    ListItem(
        overlineContent = {
            Box {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5F) // 비율 6 : 4
                        .clip(RoundedCornerShape(6.dp))
                )
                IconButton(
                    onClick = {
                        isWished = !isWished
                        onWishButtonClick()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    val painter = when (isWished) {
                        true -> painterResource(id = R.drawable.ic_btn_heart_on)
                        false -> painterResource(id = R.drawable.ic_btn_heart_off)
                    }
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        },
        headlineContent = {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            Row {
                if (product.discountRate < 100) {
                    Text(
                        text = "${product.discountRate}%",
                        color = colorResource(id = R.color.discount_rate),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(R.string.price, product.discountedPrice),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(R.string.price, product.originalPrice),
                        style = MaterialTheme.typography.bodySmall
                            .copy(textDecoration = TextDecoration.LineThrough),
                        modifier = Modifier.alignByBaseline()
                    )
                } else {
                    Text(
                        text = stringResource(R.string.price, product.originalPrice),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun HorizontalSectionList(
    products: List<Product>,
    onWishButtonClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = "horizontal", modifier = modifier)
}

@Composable
private fun GridSectionList(
    products: List<Product>,
    onWishButtonClick: (Product) -> Unit,
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
        VerticalSectionList(
            products = listOf(product),
            onWishButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VerticalSectionListPreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    VerticalSectionList(
        products = listOf(product, product, product),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun HorizontalSectionListPreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    HorizontalSectionList(
        products = listOf(product, product, product),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun GridSectionListPreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    GridSectionList(
        products = listOf(product, product, product),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun VerticalProductListItemPreview() {
    VerticalProductListItem(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 1000,
            isSoldOut = false
        ),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun VerticalProductListItemLongNamePreview() {
    VerticalProductListItem(
        product = Product(
            id = 1,
            name = "name name name name name name name name name name name name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 1000,
            isSoldOut = false
        ),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun VerticalProductListItemDiscountedPreview() {
    VerticalProductListItem(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 500,
            isSoldOut = false
        ),
        onWishButtonClick = {}
    )
}

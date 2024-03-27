package com.jj.kurly.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
        Section(
            section = section,
            onWishButtonClick = onWishButtonClick
        )
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

    Column(modifier = modifier.padding(16.dp)) {
        ProductImage(
            product = product,
            isWished = isWished,
            onWishButtonClick = {
                isWished = !isWished
                onWishButtonClick()
            },
            modifier = Modifier.aspectRatio(1.5F) // 비율 6 : 4
        )
        ProductName(
            product = product,
            maxLines = 1
        )
        ProductPriceOneLine(
            product = product
        )
    }
}

@Composable
private fun HorizontalSectionList(
    products: List<Product>,
    onWishButtonClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(products) {
            HorizontalProductListItem(
                product = it,
                onWishButtonClick = { onWishButtonClick(it) }
            )
        }
    }
}

@Composable
private fun GridSectionList(
    products: List<Product>,
    onWishButtonClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    // row 2, col 3
    val chunkedProducts = products.take(6).chunked(3)
    Column(modifier = modifier) {
        chunkedProducts.forEach { products ->
            Row {
                products.forEach {
                    HorizontalProductListItem(
                        product = it,
                        onWishButtonClick = { onWishButtonClick(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalProductListItem(
    product: Product,
    onWishButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: 저장소 data 와 연결
    var isWished by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
    ) {
        ProductImage(
            product = product,
            isWished = isWished,
            onWishButtonClick = {
                isWished = !isWished
                onWishButtonClick()
            },
            modifier = Modifier.size(150.dp, 200.dp)
        )
        ProductName(
            product = product,
            maxLines = 2
        )
        ProductPriceTwoLines(
            product = product
        )
    }
}

@Composable
private fun Section(
    section: Section,
    onWishButtonClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val title = section.info.title
    val products = section.products

    Section(
        title = title,
        modifier = modifier
    ) {
        when (section.info.type) {
            SectionType.VERTICAL ->
                VerticalSectionList(
                    products = products,
                    onWishButtonClick = onWishButtonClick
                )

            SectionType.HORIZONTAL ->
                HorizontalSectionList(
                    products = products,
                    onWishButtonClick = onWishButtonClick
                )

            SectionType.GRID ->
                GridSectionList(
                    products = products,
                    onWishButtonClick = onWishButtonClick
                )
        }
    }
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

@Composable
private fun ProductImage(
    product: Product,
    isWished: Boolean,
    onWishButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(6.dp))
        )
        IconButton(
            onClick = onWishButtonClick,
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
}

@Composable
private fun ProductName(
    product: Product,
    maxLines: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = product.name,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
private fun ProductPriceOneLine(
    product: Product,
    modifier: Modifier = Modifier
) {
    val isDiscounted = product.discountedPrice != null
            && product.discountedPrice < product.originalPrice

    Row(modifier = modifier) {
        if (isDiscounted) {
            DiscountRate(
                discountRate = product.discountRate,
                modifier = Modifier.alignByBaseline()
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
        SalePrice(
            salePrice = product.discountedPrice ?: product.originalPrice,
            modifier = Modifier.alignByBaseline()
        )
        if (isDiscounted) {
            Spacer(modifier = Modifier.width(2.dp))
            OriginalPrice(
                originalPrice = product.originalPrice,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

@Composable
private fun ProductPriceTwoLines(
    product: Product,
    modifier: Modifier = Modifier
) {
    val isDiscounted = product.discountedPrice != null
            && product.discountedPrice < product.originalPrice

    Column(modifier = modifier) {
        Row {
            if (isDiscounted) {
                DiscountRate(
                    discountRate = product.discountRate,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(modifier = Modifier.width(2.dp))
            }
            SalePrice(
                salePrice = product.discountedPrice ?: product.originalPrice,
                modifier = Modifier.alignByBaseline()
            )
        }
        if (isDiscounted) {
            OriginalPrice(
                originalPrice = product.originalPrice
            )
        }
    }
}

@Composable
private fun DiscountRate(
    discountRate: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.discount_rate, discountRate),
        color = colorResource(id = R.color.discount_rate),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

@Composable
private fun SalePrice(
    salePrice: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.price, salePrice),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

@Composable
private fun OriginalPrice(
    originalPrice: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.price, originalPrice),
        style = MaterialTheme.typography.bodySmall
            .copy(textDecoration = TextDecoration.LineThrough),
        color = Color.Gray,
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
        products = listOf(product, product),
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
        products = List(6) { product },
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun GridSectionListOverSizePreview() {
    val product = Product(
        id = 1,
        name = "name",
        image = "image",
        originalPrice = 1000,
        discountedPrice = 900,
        isSoldOut = false
    )
    GridSectionList(
        products = List(100) { product },
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun VerticalProductListItemPreview() {
    VerticalProductListItem(
        product = Product(
            id = 1,
            name = "name name name name name name name name name name name name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 500,
            isSoldOut = false
        ),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun HorizontalProductListItemPreview() {
    HorizontalProductListItem(
        product = Product(
            id = 1,
            name = "name name name name name name name name name name name name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 500,
            isSoldOut = false
        ),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductPriceOneLinePreview() {
    ProductPriceOneLine(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = null,
            isSoldOut = false
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductPriceOneLineDiscountedPreview() {
    ProductPriceOneLine(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 500,
            isSoldOut = false
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductPriceTwoLinesLinePreview() {
    ProductPriceTwoLines(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = null,
            isSoldOut = false
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductPriceTwoLinesDiscountedPreview() {
    ProductPriceTwoLines(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 500,
            isSoldOut = false
        )
    )
}
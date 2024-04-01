package com.jj.kurly.feature.home

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.Section
import com.jj.kurly.core.model.SectionType
import com.jj.kurly.core.model.WishableProduct

@Composable
internal fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val sectionPagingItems = viewModel.sectionPagingData.collectAsLazyPagingItems()

    HomeScreen(
        sectionPagingItems = sectionPagingItems,
        onWishButtonClick = viewModel::wishProduct,
        onRefresh = { sectionPagingItems.refresh() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen(
    sectionPagingItems: LazyPagingItems<Section>,
    onWishButtonClick: (Int, Boolean) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() {
        refreshing = true
        onRefresh()
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(
        modifier = modifier
            .semantics { contentDescription = "Start Screen" }
            .pullRefresh(state)
    ) {
        when (sectionPagingItems.loadState.refresh) {
            is LoadState.Loading ->
                Loading()

            is LoadState.NotLoading ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 64.dp)
                ) {
                    if (!refreshing) {
                        sections(
                            sectionPagingItems = sectionPagingItems,
                            onWishButtonClick = onWishButtonClick
                        )
                    }
                }

            is LoadState.Error ->
                Text(text = stringResource(id = R.string.home_refresh_error))
        }

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

private fun LazyListScope.sections(
    sectionPagingItems: LazyPagingItems<Section>,
    onWishButtonClick: (Int, Boolean) -> Unit
) {
    items(sectionPagingItems.itemCount) {
        sectionPagingItems[it]?.let { section ->
            Section(
                section = section,
                onWishButtonClick = onWishButtonClick
            )

            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }

    when (sectionPagingItems.loadState.append) {
        is LoadState.Loading ->
            item { Loading(modifier = Modifier.padding(32.dp)) }

        is LoadState.Error ->
            item { Text(text = stringResource(R.string.home_append_error)) }

        is LoadState.NotLoading -> Unit
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
    products: List<WishableProduct>,
    onWishButtonClick: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        products.forEach { wishableProduct ->
            VerticalProductListItem(
                wishableProduct = wishableProduct,
                onWishButtonClick = { onWishButtonClick(wishableProduct.product.id, it) }
            )
        }
    }
}

@Composable
private fun VerticalProductListItem(
    wishableProduct: WishableProduct,
    onWishButtonClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        ProductImage(
            wishableProduct = wishableProduct,
            onWishButtonClick = onWishButtonClick,
            modifier = Modifier.aspectRatio(1.5F) // 비율 6 : 4
        )
        ProductName(
            product = wishableProduct.product,
            maxLines = 1
        )
        ProductPriceOneLine(
            product = wishableProduct.product
        )
    }
}

@Composable
private fun HorizontalSectionList(
    products: List<WishableProduct>,
    onWishButtonClick: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(16.dp)
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        products.forEach { wishableProduct ->
            HorizontalProductListItem(
                wishableProduct = wishableProduct,
                onWishButtonClick = { onWishButtonClick(wishableProduct.product.id, it) },
                modifier = Modifier.width(150.dp)
            )
        }
    }
}

@Composable
private fun GridSectionList(
    products: List<WishableProduct>,
    onWishButtonClick: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val row = 2
    val col = 3
    val chunkedProducts = products.take(row * col).chunked(col)
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chunkedProducts.forEach { products ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                products.forEach { wishableProduct ->
                    HorizontalProductListItem(
                        wishableProduct = wishableProduct,
                        onWishButtonClick = { onWishButtonClick(wishableProduct.product.id, it) },
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(col - products.size) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun HorizontalProductListItem(
    wishableProduct: WishableProduct,
    onWishButtonClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
    ) {
        ProductImage(
            wishableProduct = wishableProduct,
            onWishButtonClick = onWishButtonClick,
            modifier = Modifier.aspectRatio(0.75F) // 비율 150 : 200
        )
        ProductName(
            product = wishableProduct.product,
            maxLines = 2
        )
        ProductPriceTwoLines(
            product = wishableProduct.product
        )
    }
}

@Composable
private fun Section(
    section: Section,
    onWishButtonClick: (Int, Boolean) -> Unit,
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

            SectionType.UNKNOWN -> Unit
        }
    }
}

@Composable
private fun Section(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.padding(vertical = 24.dp)) {
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
        modifier = modifier.padding(16.dp)
    )
}

@Composable
private fun ProductImage(
    wishableProduct: WishableProduct,
    onWishButtonClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = wishableProduct.product.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(6.dp))
        )
        IconButton(
            onClick = { onWishButtonClick(!wishableProduct.isWished) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            val painter = when (wishableProduct.isWished) {
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
    Row(modifier = modifier) {
        if (product.isDiscounted) {
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
        if (product.isDiscounted) {
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
    Column(modifier = modifier) {
        Row {
            if (product.isDiscounted) {
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
        if (product.isDiscounted) {
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
    val product = WishableProduct(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 900,
            isSoldOut = false
        ),
        isWished = false
    )
    Section("title") {
        VerticalSectionList(
            products = listOf(product),
            onWishButtonClick = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VerticalSectionListPreview() {
    val product = WishableProduct(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 900,
            isSoldOut = false
        ),
        isWished = false
    )
    VerticalSectionList(
        products = listOf(product, product),
        onWishButtonClick = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun HorizontalSectionListPreview() {
    val product = WishableProduct(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 900,
            isSoldOut = false
        ),
        isWished = false
    )
    HorizontalSectionList(
        products = listOf(product, product, product),
        onWishButtonClick = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun GridSectionListPreview() {
    val product = WishableProduct(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 900,
            isSoldOut = false
        ),
        isWished = false
    )
    GridSectionList(
        products = List(6) { product },
        onWishButtonClick = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun GridSectionListUnderSizePreview() {
    val product = WishableProduct(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 900,
            isSoldOut = false
        ),
        isWished = false
    )
    GridSectionList(
        products = List(5) { product },
        onWishButtonClick = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun GridSectionListOverSizePreview() {
    val product = WishableProduct(
        product = Product(
            id = 1,
            name = "name",
            image = "image",
            originalPrice = 1000,
            discountedPrice = 900,
            isSoldOut = false
        ),
        isWished = false
    )
    GridSectionList(
        products = List(100) { product },
        onWishButtonClick = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun VerticalProductListItemPreview() {
    VerticalProductListItem(
        wishableProduct = WishableProduct(
            product = Product(
                id = 1,
                name = "name name name name name name name name name name name name",
                image = "image",
                originalPrice = 1000,
                discountedPrice = 500,
                isSoldOut = false
            ),
            isWished = false
        ),
        onWishButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun HorizontalProductListItemPreview() {
    HorizontalProductListItem(
        wishableProduct = WishableProduct(
            product = Product(
                id = 1,
                name = "name name name name name name name name name name name name",
                image = "image",
                originalPrice = 1000,
                discountedPrice = 500,
                isSoldOut = false
            ),
            isWished = false
        ),
        onWishButtonClick = {},
        modifier = Modifier.width(150.dp)
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
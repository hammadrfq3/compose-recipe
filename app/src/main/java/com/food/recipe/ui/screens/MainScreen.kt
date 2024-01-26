package com.food.recipe.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.food.recipe.R
import com.food.recipe.data.model.Category
import com.food.recipe.ui.viewmodel.RecipeViewModel
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun MainScreen(selectCategory: (String) -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg))
    ) {
        Log.e("TAG", "MainScreen")
        Text(
            modifier = Modifier.padding(20.dp, 30.dp, 0.dp, 0.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 32.sp
                    )
                ) {
                    append("Cook")
                }
                append(" ")
                append("your favourite\nFood at ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 32.sp
                    )
                ) {
                    append("Home")
                }
            },
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 35.sp
        )

        MyEdittext()

        SetData(selectCategory = selectCategory)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEdittext(viewModel: RecipeViewModel = viewModel()) {

    var text by remember { mutableStateOf(viewModel.searchText) }
    var isShowBottomSheet by remember { mutableStateOf(false) }

    /*if (isShowBottomSheet){
        ShowBottomSheet{
            isShowBottomSheet = false
        }
    }*/

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                viewModel.searchText = it
                text = it
                viewModel.getCategoriesByFilter(viewModel.searchText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color.White
            ),
            placeholder = {
                Text(text = "Search food by category...", color = colorResource(R.color.text_dim))
            },
            leadingIcon = @Composable {
                IconButton(
                    onClick = {
                        text = ""
                    },
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        tint = colorResource(id = R.color.text_dim)
                    )
                }
            },
            trailingIcon = @Composable {
                Image(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false)
                    ) {
                        isShowBottomSheet = true
                    },
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "Contact profile picture",
                )
            }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheet(onDismiss: () -> Unit){

    ModalBottomSheet(
        onDismissRequest = { onDismiss.invoke() },
       /* sheetState = rememberFlexibleBottomSheetState(
           *//* flexibleSheetSize = FlexibleSheetSize(
                fullyExpanded = 0.9f,
                intermediatelyExpanded = 0.5f,
                slightlyExpanded = 0.15f,
            ),*//*
            isModal = true,
           // skipSlightlyExpanded = false,
            //skipIntermediatelyExpanded = false,
            confirmValueChange = {false}
        ),*/
        sheetState = rememberModalBottomSheetState(
            confirmValueChange = {false}, skipPartiallyExpanded = true
        ),
        containerColor = Color.Black,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "This is Flexible Bottom Sheet",
            textAlign = TextAlign.Center,
            color = Color.White,
        )
    }

}

@Composable
fun SetData(viewModel: RecipeViewModel = viewModel(), selectCategory: (String) -> Unit) {

    // val recipes by viewModel.recipes.observeAsState(emptyList())
    val data by viewModel.categories.observeAsState()

    val filteredList by viewModel.categoriesLocal.collectAsState(data?.categories)

    LaunchedEffect(Unit) {
        Log.e("TAG", "fetchCategories API request")
        viewModel.fetchCategoriesIfNeeded()
    }

    Log.e("TAG", "Recipes : ${filteredList?.size}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 10.dp, 15.dp, 0.dp)
    ) {
        if (filteredList.isNullOrEmpty()) {
            // Show loading indicator or placeholder
            //Text(text = "Loading...")
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            // Display the list of credit cards
            LazyVerticalGrid(columns = GridCells.Adaptive(160.dp), content = {
                items(filteredList!!) { recipe ->
                    SetUi(recipe, selectCategory = selectCategory)
                }
            })
        }
    }

}

@Composable
fun SetUi(
    category: Category,
    selectCategory: (String) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .padding(10.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                selectCategory.invoke(category.strCategory)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp),
                imageModel = { category.strCategoryThumb }, // loading a network image using an URL.
                component = rememberImageComponent {
                    // shows a shimmering effect when loading an image.
                    +ShimmerPlugin(
                        baseColor = colorResource(id = R.color.box_bg_grey),
                        highlightColor = colorResource(id = R.color.white),
                    )
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center,
                )
            )
        }
        Text(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
            text = category.strCategory,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
    }


}
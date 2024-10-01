package com.food.recipe.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.food.recipe.R
import com.food.recipe.data.model.Meal
import com.food.recipe.data.model.MealResponse
import com.food.recipe.ui.viewmodel.RecipeViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin


@Composable
fun MealScreen(
    viewModel: RecipeViewModel,
    category:String,selectMeal: (Meal) -> Unit,
    onBackPress: () -> Unit
){

    val recipes by viewModel.meals.observeAsState()

    LaunchedEffect(Unit) {
        Log.e("TAG", "fetchMeals API request")
        viewModel.fetchMealsByCategory(category)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg)))
    {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box {
                Image(
                    modifier = Modifier
                        .padding(16.dp, 20.dp, 0.dp, 20.dp)
                        .align(Alignment.CenterStart)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false)
                        ) {
                            onBackPress.invoke()
                        }
                    ,
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription =""
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = category,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            }
        }

        GetMealsDataByCategory(recipes,selectMeal = selectMeal)

    }

}

@Composable
fun GetMealsDataByCategory(recipes: MealResponse?,selectMeal: (Meal) -> Unit){


    Log.e("TAG", "meals : ${recipes?.meals?.size}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 10.dp, 15.dp, 0.dp)
    ) {
        if (recipes?.meals.isNullOrEmpty()) {
            // Show loading indicator or placeholder
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            // Display the list of credit cards
            LazyColumn( content ={
                recipes?.meals?.let {
                    items(it.size) { pos ->
                        SetMealsData(it[pos], selectMeal = selectMeal )
                    }
                }
            } )
        }
    }

}

@Composable
fun SetMealsData(meal: Meal, selectMeal: (Meal) -> Unit = {}){

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                selectMeal.invoke(meal)
            }
            .padding(0.dp),
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp)
        ) {
            GlideImage(
                modifier = Modifier.size(140.dp),
                imageModel = { meal.strMealThumb }, // loading a network image using an URL.
                component = rememberImageComponent {
                    // shows a shimmering effect when loading an image.
                    +ShimmerPlugin(
                        baseColor = colorResource(id = R.color.box_bg_grey),
                        highlightColor = colorResource(id = R.color.white)
                    )
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
            )
        }
        Column {
            Text(
                modifier = Modifier.padding(10.dp,10.dp,0.dp,0.dp),
                text = meal.strMeal,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 2 ,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(10.dp,5.dp,8.dp,0.dp),
                text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.",
                color = colorResource(id = R.color.text_light),
                fontSize = 12.sp,
                maxLines = 4 ,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
        }
    }

}
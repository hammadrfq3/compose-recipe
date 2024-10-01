package com.food.recipe.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.food.recipe.R
import com.food.recipe.data.model.MealRecipeResponse
import com.food.recipe.data.model.RecipeModel
import com.food.recipe.ui.viewmodel.RecipeViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MealDetailScreen(
    viewModel: RecipeViewModel,
    meal: String,
    onBackPress: () -> Unit
) {

    val mealItem by viewModel.mealItem.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMealByMealId(meal)
    }

    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg))
    )
    {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                modifier = Modifier
                    .padding(16.dp, 20.dp, 0.dp, 20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false)
                    ) {
                        onBackPress.invoke()
                    },
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = ""
            )

            Image(
                modifier = Modifier
                    .padding(0.dp, 20.dp, 16.dp, 20.dp),
                painter = painterResource(id = R.drawable.notification_alert),
                contentDescription = ""
            )


        }

        GetMealDataByMeal(mealItem) {
            title = it
        }

    }

}

@Composable
fun GetMealDataByMeal(
    mealItem: MealRecipeResponse?,
    onDataFetched: (String) -> Unit
) {

    Log.e("TAG", "mealItem : ${mealItem?.meals?.size}")


    if (mealItem?.meals.isNullOrEmpty()) {
        // Show loading indicator or placeholder
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        mealItem?.meals?.get(0)?.let {
            onDataFetched.invoke(it.strMeal)
            SetMealItem(it)
        }
    }


}

@Composable
fun SetMealItem(mealItem: RecipeModel) {


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 20.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = mealItem.strMeal,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Card(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .requiredHeight(300.dp),
                imageModel = { mealItem.strMealThumb }, // loading a network image using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center,
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(0.dp, 20.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "",
                Modifier.size(20.dp)
            )
            Text(
                modifier = Modifier
                    .padding(3.dp, 0.dp, 0.dp, 0.dp),
                text = mealItem.strArea,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_light)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.star_24),
                contentDescription = "",
                Modifier.size(20.dp)
            )
            Text(
                modifier = Modifier
                    .padding(3.dp, 0.dp, 0.dp, 0.dp),
                text = "Reviews (200k)",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = colorResource(id = R.color.text_light)
            )
        }

        Column(
            modifier = Modifier
                .requiredHeight(180.dp)
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                .background(
                    color = colorResource(id = R.color.box_bg_grey),
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp, 10.dp, 10.dp, 0.dp),
                text = "Instructions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(10.dp, 4.dp, 10.dp, 10.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f),
                text = mealItem.strInstructions
            )
        }

        Button(
            modifier = Modifier
                .padding(0.dp, 15.dp, 0.dp, 0.dp)
                .requiredHeight(50.dp)
                .fillMaxWidth(),
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700))
        ) {
            Text(
                "Ingredients",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }


        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient1,
            mealItem.strMeasure1
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient2,
            mealItem.strMeasure2
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient3,
            mealItem.strMeasure3
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient4,
            mealItem.strMeasure4
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient5,
            mealItem.strMeasure5
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient6,
            mealItem.strMeasure6
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient7,
            mealItem.strMeasure7
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient8,
            mealItem.strMeasure8
        )
        IngredientsItem(
            image = mealItem.strMealThumb,
            mealItem.strIngredient9,
            mealItem.strMeasure9
        )

    }


}

@Composable
fun IngredientsItem(image: String, ingredient: String, measure: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
            .background(
                color = colorResource(id = R.color.box_bg_grey1),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .size(50.dp),
                imageModel = { image }, // loading a network image using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center,
                )
            )
        }

        Text(
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
            text = ingredient,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = measure,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.text_light),
            fontSize = 13.sp
        )

    }

}
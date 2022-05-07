package com.jonathankalonga.jokaltemplaite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jonathankalonga.jokaltemplaite.models.Shop
import com.jonathankalonga.jokaltemplaite.models.shoesProduct
import com.jonathankalonga.jokaltemplaite.ui.AppBarCollapsedHeight
import com.jonathankalonga.jokaltemplaite.ui.AppBarExpendedHeight
import com.jonathankalonga.jokaltemplaite.ui.theme.JokalTemplaiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokalTemplaiteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp(shoesProduct)
                }
            }
        }
    }
}

@Composable
fun MyApp(shop: Shop) {
    Box() {
        Content(shop)
        ParalaxToolBar(shop)
    }
}

@Composable
fun ParalaxToolBar(shop: Shop) {
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight
    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier.height(AppBarExpendedHeight)
    ) {
        Column {
            Box(modifier = Modifier.height(imageHeight)){
                Image(painter = painterResource(id =R.drawable.shop ),
                    contentDescription ="shop image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .height(AppBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = shop.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }

}

@Composable
fun Content(shop: Shop){

}

@Preview(showBackground = true, widthDp = 300, heightDp = 1400)
@Composable
fun DefaultPreview() {
    JokalTemplaiteTheme {
        MyApp(shoesProduct)
    }
}
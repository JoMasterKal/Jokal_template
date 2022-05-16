package com.jonathankalonga.jokaltemplaite


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.jonathankalonga.jokaltemplaite.models.Shop
import com.jonathankalonga.jokaltemplaite.models.linaBoutique
import com.jonathankalonga.jokaltemplaite.ui.AppBarCollapsedHeight
import com.jonathankalonga.jokaltemplaite.ui.AppBarExpendedHeight
import com.jonathankalonga.jokaltemplaite.ui.theme.*
import kotlin.math.max
import kotlin.math.min


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            JokalTemplaiteTheme {
                // A surface container using the 'background' color from the theme
                ProvideWindowInsets {
                    Surface(
                        //modifier = Modifier.fillMaxSize(),
                        color = White
                    ) {
                        MyApp(linaBoutique)
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(shop: Shop) {

    val scrollState = rememberLazyListState()

    Box() {
        Content(shop, scrollState)
        ParalaxToolBar(shop, scrollState)
    }
}

@Composable
fun ParalaxToolBar(shop: Shop, scrollState: LazyListState) {
    //add offset logics
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    val maxOffset = with(LocalDensity.current) {imageHeight.roundToPx()} - LocalWindowInsets.current.systemBars.layoutInsets.top

    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)

    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset


    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(AppBarExpendedHeight)
            .offset { IntOffset(x=0,y = -offset) },
        elevation = if(offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            //put background image
            Box(modifier = Modifier.height(imageHeight)){
                Image(painter = painterResource(id =R.drawable.shop ),
                    contentDescription ="shop image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                //add transparent space
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                Pair(0.4f, Color.Transparent),
                                Pair(1f, Color.White)
                            )
                        )
                    )
                )

                //add category name
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Bottom
                ){
                    Text(text = shop.category,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(Shapes.small)
                        .background(LightGray)
                        .padding(vertical = 6.dp, horizontal = 16.dp))

                }
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
                    modifier = Modifier.
                    padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress))
            }
        }
    }

    //add navigation button to topAppbar
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppBarCollapsedHeight)
            .statusBarsPadding()
            .padding(16.dp)

    ) {
        CircularButton(R.drawable.ic_arrow_back)
        CircularButton(R.drawable.ic_shopping)

    }


}

@Composable
fun CircularButton(
    @DrawableRes iconRes : Int,
    color: Color = Color.Gray,
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(onClick = onClick,
    contentPadding = PaddingValues(),
    shape = Shapes.small,
    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        contentColor = Color.Gray),
    elevation = elevation,
    modifier = Modifier
        .width(38.dp)
        .height(38.dp)) {
       Icon(painter =  painterResource(id = iconRes), contentDescription = null)
        
    }
}

@Composable
fun Content(shop: Shop, scrollState: LazyListState){
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpendedHeight), state = scrollState){
        item{
            BasicInfo(shop)
            DescriptionShop(shop)
            MenuHedear()
            ProductList(linaBoutique)
            AllProducts()
            SpaceImage()
        }
    }

}

@Composable
fun SpaceImage() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)) {
        Image(painter = painterResource(
            id = R.drawable.bottom),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(Shapes.small)

        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        Pair(0.4f, Color.Transparent),
                        Pair(1f, Green)
                    )
                )
            )
        )
    }
}

@Composable
fun AllProducts() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween) {
        
        Column() {
            Text(text = "Shoes", fontWeight = FontWeight.Bold)
            Text(text = "450", color = DarkGray)
        }
        
        Button(onClick = { /*TODO*/ }, elevation = null, colors = ButtonDefaults.buttonColors(
            contentColor = Green, backgroundColor = Transparent
        )) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "See All")
                Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = null)
            }
            
        }

    }
}

@Composable
fun ProductList(shop:Shop) {
    ProductGrid(nColumn = 3, item = shop.shoes,) {
        ProductCard(it.image,it.name,it.price,Modifier)



    }
}

@Composable
fun ProductCard(
    @DrawableRes imageRes:Int,
    title: String,
    price: Double,
    modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ){
        Card(
            backgroundColor = LightGray,
            shape = Shapes.large,
            modifier = Modifier
                .padding(8.dp)
                .width(100.dp)
                .height(100.dp)
        ) {
            Image(painter = painterResource(
                id = imageRes),
                contentDescription = null,
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight())
        }
        Text(text = title,modifier.width(100.dp), fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Text(text = price.toString(),modifier.width(100.dp), color = Gray, fontSize = 14.sp)

    }
}

@Composable
fun <T>ProductGrid(nColumn:Int,item:List<T>, content: @Composable (T)-> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        for (i in item.indices step nColumn) {
            Row {
                for (j in 0 until nColumn) {
                    if(i+j < item.size) {
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f)
                        ){
                            content(item[i+j])
                        }
                    }else {
                        Spacer(modifier = Modifier.weight(1f, fill = true))
                    }
                }
            }
        }

    }
}

@Composable
fun MenuHedear() {
    LazyRow(
       // horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .clip(shape = Shapes.medium)
            .background(LightGray)
            .fillMaxWidth()
            .height(44.dp)
    ){
        item {
            tabButton("chaussure",true, Modifier)
            tabButton("chemises",false, Modifier)
            tabButton("pantantallon",false, Modifier)
        }
    }
}

@Composable
fun tabButton(text: String, active: Boolean, modifier: Modifier) {
    Button(onClick = { /*TODO*/ },
        shape = Shapes.medium,
        modifier = modifier.fillMaxHeight(),
        elevation = null,
        colors = if(active) ButtonDefaults.buttonColors(
            backgroundColor = Green,
            contentColor = White
        ) else ButtonDefaults.buttonColors(
            backgroundColor = LightGray,
            contentColor = DarkGray
        )

        ) {
        Text(text = text)
    }
}

@Composable
fun DescriptionShop(shop: Shop) {
    Text(
        text = shop.description,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)

    )
}

//info row
@Composable
fun BasicInfo(shop: Shop) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.ic_shipping_car,shop.livrateTIme)
        InfoColumn(R.drawable.ic_location,shop.city)
        InfoColumn(R.drawable.ic_star,shop.rating)
    }
}

//content of info
@Composable
fun InfoColumn(@DrawableRes iconRes: Int, text:String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Icon(painter = painterResource(id = iconRes), 
            contentDescription = null, 
            tint = Green,
            modifier = Modifier.height(24.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 1400)
@Composable
fun DefaultPreview() {
    JokalTemplaiteTheme {
        MyApp(linaBoutique)
    }
}
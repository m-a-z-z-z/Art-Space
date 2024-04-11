package com.maz.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maz.artspace.ui.theme.ArtSpaceTheme

/*
 * TODO:
 *  1. Add landscape orientation
 *  2. Cycle back to the beginning when last art piece is reached, need to fix logic
 *  3. Night mode
 * 
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtSpaceLayout() {
    var currentImage by remember { mutableStateOf(1) }
    val (imageResourceID, imageDescriptionID, imageTitle) = when (currentImage) {
        1 -> Triple(R.drawable.hand, R.string.hand, "Trippy Hand")
        2 -> Triple(R.drawable.fred, R.string.fred, "Fred Absolutely Smacked")
        3 -> Triple(R.drawable.tupac, R.string.tupac, "Tupac")
        4 -> Triple(R.drawable.shawty, R.string.shawty, "Some Shawty")
        5 -> Triple(R.drawable.mc_ride, R.string.mc_ride, "MC Ride")
        6 -> Triple(R.drawable.me_basically, R.string.me_basically, "Me, Basically")
        else -> Triple(R.drawable.hand, R.string.hand, "Trippy Hand") // Default case
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Art Space", fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ){
            LazyColumn(
                modifier = Modifier
                   .fillMaxSize()
                   .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    ArtPieceAndDescription(imageResource = imageResourceID, imageDescription = imageDescriptionID, imageTitle = imageTitle)
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Button(
                    onClick = {
                        if (currentImage == 1) { // If you click previous on the first art piece, loop back to the last art piece
                            currentImage = 6
                        }
                        else
                            currentImage--
                    },
                    modifier = Modifier.padding(16.dp)
                ) { Text(text = "Previous") }

                Button(
                    onClick = {
                        if (currentImage == 6) { // If you click next on the last art piece, loop back to the first art piece
                            currentImage = 1
                        }
                        else
                            currentImage++
                    },
                    modifier = Modifier.padding(16.dp)
                ) { Text(text = "Next", Modifier.padding(12.dp,0.dp)) }
            }
        }
    }
}

@Composable
fun ArtPieceAndDescription(imageResource: Int, imageDescription: Int, imageTitle: String, modifier: Modifier = Modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(    // creates surface the image lies inside
                shadowElevation = 16.dp,
                border = BorderStroke(3.dp, color = Color.DarkGray)
            ) {
                Image(painter = painterResource(imageResource), contentDescription = stringResource(imageDescription),
                    modifier = modifier
                        .padding(16.dp) // padding to stop the border cutting into the image.
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Surface() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                       .padding(16.dp)
                ) {
                    Text(text = imageTitle, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(text = stringResource(id = imageDescription), textAlign = TextAlign.Justify, modifier = modifier.padding(top = 8.dp, start = 50.dp, end = 50.dp))
                }

            }

        }

}

@Preview(name = "Main")
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}

@Preview(
    name = "ArtSpace Landscape Preview",
    showBackground = true,
    widthDp = 640, // Assuming a landscape width
    heightDp = 360, // Assuming a landscape height
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ArtSpaceLandscapePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}

package com.example.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceApp()
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentImageIndex by remember { mutableStateOf(0) }

    ArtSpaceScreen(
        imageData = images[currentImageIndex],
        onPreviousClicked = { currentImageIndex = (currentImageIndex - 1 + images.size) % images.size },
        onNextClicked = { currentImageIndex = (currentImageIndex + 1) % images.size }
    )
}

@Composable
fun ArtSpaceScreen(
    imageData: ImageData,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val titleFontSize = if (isLandscape) 20.sp else 20.sp
    val bodyFontSize = if (isLandscape) 14.sp else 14.sp

    Column(
        modifier = Modifier
            .background(Brush.verticalGradient(
                colors = listOf(Color(0xFF422222), Color(0xFF2C2C2C))
            ))
            .padding(16.dp)
            .run {
                if (isLandscape) {
                    this.fillMaxSize()
                } else {
                    this.fillMaxSize().padding(bottom = 64.dp)
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .run {
                    if (isLandscape) {
                        this.width(400.dp).height(200.dp)
                    } else {
                        this.fillMaxWidth().height(300.dp)
                    }
                }
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(Color(0xFFCECECE))
                .padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = imageData.imageRes),
                contentDescription = "Art Image",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column (
            modifier = Modifier
                .run {
                    if (isLandscape) {
                        this.width(400.dp).align(Alignment.CenterHorizontally)
                    } else {
                        this.fillMaxWidth()
                    }
                }
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(Color(0xFFCECECE))
                .padding(16.dp)
        ) {
            Text(
                text = imageData.title,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = titleFontSize),
                color = Color.Black
            )
            Row ()
            {
                Text(
                    text = imageData.author,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = bodyFontSize),
                    color = Color.Black
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Button(onClick = onPreviousClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B3939),
                contentColor = Color.White)
        ) {
            Text("Prev")

        }
        Button(onClick = onNextClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B3939),
                contentColor = Color.White)
        ) {
            Text("Next")
        }
    }
}

@Preview(name = "Portait", showBackground = true, uiMode = Configuration.ORIENTATION_PORTRAIT, widthDp = 360, heightDp = 640)
@Composable
fun PortaitPreview() {
    ArtSpaceApp()
}

@Preview(name = "Landscape", showBackground = true, uiMode = Configuration.ORIENTATION_LANDSCAPE, widthDp = 640, heightDp = 360)
@Composable
fun LandscapePreview() {
    ArtSpaceApp()
}
package com.kiko.kige.samplekige

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.kiko.kige.data.remembers.rememberKigeState
import com.kiko.kige.samplekige.ui.theme.KigeTheme
import com.kiko.kige.ui.components.KigePicker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KigeTheme {
                val rememberKigeState = rememberKigeState()
                KigePicker(rememberKigeState)

                AsyncImage(model = rememberKigeState.photoUri.value, contentDescription = null)


                Button({
                    rememberKigeState.expand()
                }) {
                    Text("Got")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KigeTheme {
        Greeting("Android")
    }
}
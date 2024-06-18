package com.hyqoo.ipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hyqoo.ipapp.ui.rememberAppState
import com.hyqoo.ipapp.ui.theme.IpAppTheme
import com.hyqoo.ipapp.ui.view.IpAppApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IpAppTheme {
                IpAppApp(
                    appState = rememberAppState()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun IpAppPreview() {
    IpAppTheme {
//        IpAppApp()
    }
}
package com.jj.kurly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jj.kurly.ui.KurlyApp
import com.jj.kurly.ui.theme.KurlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KurlyTheme {
                KurlyApp()
            }
        }
    }
}
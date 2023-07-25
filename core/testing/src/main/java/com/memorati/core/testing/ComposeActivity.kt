package com.memorati.core.testing

import android.os.Bundle
import androidx.activity.ComponentActivity

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
    }
}

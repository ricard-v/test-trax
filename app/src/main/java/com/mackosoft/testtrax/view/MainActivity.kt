package com.mackosoft.testtrax.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.network.NetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = NetworkApi(this)

        lifecycleScope.launch(Dispatchers.IO) {
            val result = test.getMovies()
        }
    }
}

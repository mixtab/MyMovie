package com.mtabarkevych.mymovie.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mtabarkevych.mymovie.core.presentation.theme.MyMovieTheme
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import com.mtabarkevych.mymovie.movies.presentation.home.HomeScreenRoute
import kotlin.reflect.KProperty

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMovieTheme {
                Box{
                    HomeScreenRoute()
                    val test: String by Delegate()
                }
            }
        }
    }

    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, thank you for delegating '${property.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$value has been assigned to '${property.name}' in $thisRef.")
        }
    }
}

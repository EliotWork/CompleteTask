package com.completetaskapp.pksq.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.completetaskapp.pksq.utils.NavRoutes
import com.completetaskapp.pksq.utils.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CompleteTaskActivity : ComponentActivity() {

    private var activityViewModel: StartViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        activityViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[StartViewModel::class.java]

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = NavRoutes.SPLASH_SCREEN
            ) {
                composable(NavRoutes.SPLASH_SCREEN) {
                    SplashScreen(
                        viewModel = activityViewModel!!,
                        navController = navController
                    )
                }
                composable(NavRoutes.ALTER_SCREEN) {
                    AlterScreen(viewModel = activityViewModel!!)
                }
            }
        }
    }
}

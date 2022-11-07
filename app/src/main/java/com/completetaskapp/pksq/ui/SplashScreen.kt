package com.completetaskapp.pksq.ui

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.completetaskapp.pksq.utils.Constants
import com.completetaskapp.pksq.data.repository.Repository
import com.completetaskapp.pksq.utils.NavRoutes
import com.completetaskapp.pksq.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SplashScreen(
    viewModel: StartViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    val imageLogoId = "image_logo"
    val progressIndicatorId = "progress_indicator"

    val constraints = ConstraintSet {

        val imageLogoRef = createRefFor(imageLogoId)
        val progressIndicatorRef = createRefFor(progressIndicatorId)

        constrain(imageLogoRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.value(80.dp)
            height = Dimension.value(70.dp)
        }

        constrain(progressIndicatorRef) {
            bottom.linkTo(anchor = parent.bottom, margin = 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.value(170.dp)
            height = Dimension.value(9.dp)
        }
    }

    ConstraintLayout(
        constraints,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(
                    id = R.color.primary
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "",
            modifier = Modifier.layoutId(imageLogoId)
        )

        LinearProgressIndicator(
            modifier = Modifier
                .layoutId(progressIndicatorId),
            color = colorResource(id = R.color.primary_dark)
        )
    }

    LaunchedEffect(true) {

        viewModel.fetchSettings.collect { resource ->

            when (resource.status) {
                Repository.Status.SUCCESS -> {
                    resource.data?.let { data ->
                        data.probe?.let { probe ->
                            when (probe) {
                                true -> {
                                    viewModel
                                        .link
                                        .collect { link ->

                                            if (link.isNotEmpty())
                                                navController.navigate(NavRoutes.ALTER_SCREEN) {
                                                    popUpTo(NavRoutes.SPLASH_SCREEN) { inclusive = true }
                                                }
                                            else openGame(context = context)
                                        }
                                }
                                false -> {
                                    openGame(context = context)
                                }
                            }
                        } ?: openGame(context = context)
                    } ?: openGame(context = context)
                }
                Repository.Status.ERROR -> {
                    openGame(context = context)
                }
                Repository.Status.LOADING -> {}
            }
        }
    }
}

private fun openGame(context: Context) {
    val activity = context.findActivity()
    val intent = Intent(context, Constants.GAME_CLASS)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    activity?.startActivity(intent)
    activity?.finish()
}

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
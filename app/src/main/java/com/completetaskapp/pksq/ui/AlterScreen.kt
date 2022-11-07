package com.completetaskapp.pksq.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import com.completetaskapp.pksq.R
import android.webkit.*
import android.widget.ProgressBar
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.ExperimentalCoroutinesApi

var mFilePathCallback: ValueCallback<Array<Uri>>? = null

@ExperimentalCoroutinesApi
@Composable
fun AlterScreen(
    viewModel: StartViewModel
) {

    var progressBar by remember { mutableStateOf<ProgressBar?>(null) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    val backEnabled = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data: Intent? = result.data
        var results: Array<Uri>? = null

        when(result.resultCode) {
            Activity.RESULT_OK -> {

                data?.let {
                    val dataString = it.dataString
                    dataString?.let { string ->
                        results = arrayOf(Uri.parse(string))
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
                // The user canceled the operation.
            }
        }
        mFilePathCallback?.onReceiveValue(results)
        mFilePathCallback = null
    }

    BackHandler(enabled = true) {
        if (backEnabled.value) webView?.goBack()
    }

    AndroidView(
        factory = {
            WebView(it).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                setupWebView()

                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)

                        view?.let { v -> backEnabled.value = v.canGoBack() }
                        progressBar?.visibility = View.VISIBLE

                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)

                        progressBar?.visibility = View.GONE
                    }
                }

                webChromeClient = object : WebChromeClient() {

                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {

                        if (mFilePathCallback != null) {
                            mFilePathCallback?.onReceiveValue(null)
                        }
                        mFilePathCallback = filePathCallback

                        val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                        contentSelectionIntent.type = "*/*"

                        viewModel.setLaunchedIntentState(state = true)

                        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                        chooserIntent.putExtra(Intent.EXTRA_TITLE, "File Chooser")
                        launcher.launch(chooserIntent)
                        return true
                    }
                }

                val link = viewModel.path

                loadUrl(link)
                webView = this
            }
        },
        update = {
            webView = it
        }
    )

    AndroidView(
        factory = { progressBarContext ->
            ProgressBar(progressBarContext, null, android.R.attr.progressBarStyleHorizontal).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    16
                )
                progressDrawable.setColorFilter(android.graphics.Color.BLUE, PorterDuff.Mode.SRC_IN)
                indeterminateTintList = ColorStateList.valueOf(progressBarContext.getColor(R.color.primary))
                isIndeterminate = true

            }.also { progressBar = it }
        }
    )
}

@SuppressLint("SetJavaScriptEnabled")
fun WebView.setupWebView() {

    CookieManager
        .getInstance()
        .setAcceptThirdPartyCookies(
            this,
            true
        )

    this.settings.apply {
        javaScriptEnabled = true
        loadWithOverviewMode = true
        useWideViewPort = true
        builtInZoomControls = true
        displayZoomControls = false
        domStorageEnabled = true
        databaseEnabled = true
        allowContentAccess = true
        allowFileAccess = true
        javaScriptCanOpenWindowsAutomatically = true
        mediaPlaybackRequiresUserGesture = false
    }
}
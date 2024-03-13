package space.rozlach.testtaskproject.presentation.items_detail

import android.annotation.SuppressLint
import android.net.http.SslError
import android.util.Log
import android.webkit.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.snackbar.Snackbar
import com.ramcosta.composedestinations.annotation.Destination
import space.rozlach.testtaskproject.core.Constants

@Composable
@Destination
fun ItemDetailScreen(
    popisk: String,
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            state.items?.let { item ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Popis: " + item.popisk,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Begda: " + item.begda,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "End date: " + item.endda,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "SCHKZ: " + item.schkz,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Schkz text: " + item.schkz_text,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "File name: " + item.filename,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Exported: " + item.exported,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Type: " + item.typk,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp// Change the text size here
                                ),
                                modifier = Modifier.weight(8f)
                            )
                        }
                    }
                }
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        // WebView occupies half of the screen
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {

            WebView()
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView() {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                clearCache(true)

                settings.javaScriptEnabled = true
                settings.allowContentAccess = true
                settings.domStorageEnabled = true

                settings.setSupportZoom(true)
                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        Log.e("WebView Error", "Error loading webpage: $error")
                        Snackbar.make(
                            view!!,
                            "Error loading webpage",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    override fun onReceivedSslError(
                        view: WebView?,
                        handler: SslErrorHandler?,
                        error: SslError?
                    ) {
                        // Ignore SSL errors
                        handler?.proceed()
                    }
                }
            }
        },
        update = { webView ->
            webView.loadUrl(Constants.URL)
        }
    )
}
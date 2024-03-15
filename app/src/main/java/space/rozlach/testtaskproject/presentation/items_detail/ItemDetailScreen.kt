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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
import space.rozlach.testtaskproject.presentation.date_parse.DateViewModel
import java.util.Date

@Composable
@Destination
fun ItemDetailScreen(
    popisk: String,
    position:Int,
    viewModel: ItemDetailViewModel = hiltViewModel(),
    dateViewModel: DateViewModel = hiltViewModel()
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
                        showItemDetail(title = "Popis: ", value = item.popisk)
                    }
                    item {
                        val begda =
                            dateViewModel.parseDate(item.begda) ?: "Error parsing date"
                        showItemDetail(
                            title = "Begda: ",
                            value = begda
                        )
                    }
                    item {
                        val endda =
                            dateViewModel.parseDate(item.endda) ?: "Error parsing date"
                        showItemDetail(
                            title = "End date: ",
                            value = endda
                        )
                    }
                    item {
                        showItemDetail(
                            title = "Exported: ",
                            dateViewModel.parseDateAndTime(item.exported)
                                ?: "Error parsing date and time "
                        )
                    }
                    item {
                        showItemDetail(title = "Schkz: ", value = item.schkz)
                    }
                    item {
                        showItemDetail(title = "Schkz text: ", value = item.schkz_text)
                    }
                    item {
                        showItemDetail(title = "File name : ", value = item.filename)
                    }
                    item {
                        showItemDetail(title = "Typk: ", value = item.typk)
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

@Composable
fun showItemDetail(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 25.sp // Change the text size here
            ),
            modifier = Modifier.weight(1f) // Adjust weight as needed
        )
        Text(
            text = value,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 20.sp // Change the text size here
            ),
            modifier = Modifier.weight(1f) // Adjust weight as needed
        )
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
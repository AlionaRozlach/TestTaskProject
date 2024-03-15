package space.rozlach.testtaskproject.presentation.items_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import space.rozlach.testtaskproject.presentation.destinations.ItemDetailScreenDestination
import space.rozlach.testtaskproject.presentation.items_list.components.ItemListElement

@Composable
@Destination(start = true)
fun ItemListScreen(
    navigator: DestinationsNavigator,
    viewModel: ItemsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "List of Items",
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(
                color = Color.Black,
                fontSize = 30.sp // Change the text size here
            )
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(state.items) { index, item ->
                ItemListElement(
                    item = item,
                    position = index,
                    onItemClick = { clickedItem, clickedPosition ->
                        navigator.navigate(
                            ItemDetailScreenDestination(
                                clickedItem.popisk,
                                clickedPosition
                            )
                        )
                    }
                )
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
                    .align(Alignment.CenterHorizontally)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
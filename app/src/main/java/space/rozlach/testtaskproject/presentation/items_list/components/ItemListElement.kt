package space.rozlach.testtaskproject.presentation.items_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import space.rozlach.testtaskproject.domain.model.Item

@Composable
fun ItemListElement(
    item: Item,
    onItemClick: (Item) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(25.dp)
    ){

    }
}
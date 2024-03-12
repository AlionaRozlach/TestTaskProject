package space.rozlach.testtaskproject.presentation

sealed class Screen(val route:String){
    object ItemListScreen: Screen("item_list_screen")
    object ItemDetailScreen: Screen("item_detail_screen")
}

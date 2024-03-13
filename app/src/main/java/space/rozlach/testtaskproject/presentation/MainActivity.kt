package space.rozlach.testtaskproject.presentation

import android.content.res.Resources.Theme
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import space.rozlach.testtaskproject.R
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.FirebaseApp
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import space.rozlach.testtaskproject.presentation.items_detail.ItemDetailScreen
import space.rozlach.testtaskproject.presentation.items_list.ItemListScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
//                    val navController = rememberNavController()
//                    NavHost(
//                        navController = navController,
//                        startDestination = Screen.ItemListScreen.route
//                    ) {
//                        composable(
//                            route = Screen.ItemListScreen.route
//                        ) {
//                            ItemListScreen(navController)
//                        }
//                        composable(
//                            route = Screen.ItemDetailScreen.route + "/{itemPopisk}"
//                        ) {
//                            ItemDetailScreen()
//                        }
                DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }
//    }
}
package pt.iefp.appnavegation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class Pessoa(val nome: String, val idade: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var myNavController = rememberNavController()

            NavHost(navController = myNavController, startDestination = "home", builder = {

                composable("home") {
                    HomeActivity(myNavController)
                }

                composable(
                    route = "infos/{nome}/{idade}",
                    arguments = listOf(
                        navArgument("nome") { type = NavType.StringType },
                        navArgument("idade") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val nome = backStackEntry.arguments?.getString("nome") ?: "Sem nome"
                    val idade = backStackEntry.arguments?.getInt("idade") ?: -1
                    infoActivity(Pessoa(nome, idade))
                }



            })

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}
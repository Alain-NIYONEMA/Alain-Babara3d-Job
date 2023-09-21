package alain.niyonema.babara3d.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import alain.niyonema.babara3d.theme.AlainBabara3dTheme
import alain.niyonema.babara3d.screens.FirstScreen
import alain.niyonema.babara3d.screens.SecondScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlainBabara3dTheme {

                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = "first") {
                    composable("first") {
                        FirstScreen {
                            navController.navigate("second")
                        }
                    }
                    composable("second") {
                        SecondScreen()
                    }
                }
                
            }
        }
    }

}
package com.hninor.pruebameli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hninor.pruebameli.ui.screens.SearchScreen
import com.hninor.pruebameli.ui.theme.PruebaMeliTheme
import com.hninor.pruebameli.utils.CustomizedExceptionHandler
import com.hninor.pruebameli.viewmodel.MeliViewModel
import com.hninor.vassprueba.screens.ResultDetails
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MeliViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaMeliTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar({
                            Text(stringResource(R.string.app_name))
/*                            Button(onClick = { throw NullPointerException("Prueba Henry") }) {
                                
                            }*/

                        })
                    },
                    snackbarHost = { SnackbarHost(viewModel.snackbarHostState) },
                ) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
                        MainNavHost(viewModel)
                    }
                }
            }
        }

        Thread.setDefaultUncaughtExceptionHandler(
            CustomizedExceptionHandler(
                this,
                getpathCrashReport()
            )
        )
    }

    private fun getpathCrashReport(): String {
        val name = File(applicationContext.getExternalFilesDir(null).toString() + "/Crashes")
        if (!name.exists()) {
            name.mkdirs()
        }
        return name.toString()
    }
}


@Composable
private fun MainNavHost(viewModel: MeliViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationDestinations.SEARCH_RESULTS_LIST) {
        composable(route = NavigationDestinations.SEARCH_RESULTS_LIST) {
            SearchScreen(
                viewModel = viewModel,
                onResultClick = { result ->
                    viewModel.searchResultSelected = result
                    navController.navigate(NavigationDestinations.SEARCH_RESULT_DETAILS)
                }
            )
        }

        composable(route = NavigationDestinations.SEARCH_RESULT_DETAILS) { navBackStackEntry ->
            ResultDetails(viewModel.searchResultSelected)
        }

    }
}
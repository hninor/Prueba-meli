package com.hninor.pruebameli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hninor.pruebameli.ui.screens.SearchScreen
import com.hninor.pruebameli.ui.theme.PruebaMeliTheme
import com.hninor.pruebameli.utils.CustomizedExceptionHandler
import com.hninor.pruebameli.viewmodel.MeliViewModel
import com.hninor.vassprueba.screens.MeliAppBar
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
                PruebaMeliApp(viewModel = viewModel)
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
fun PruebaMeliApp(
    viewModel: MeliViewModel,
    navController: NavHostController = rememberNavController()
) {


    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MeliScreen.valueOf(
        backStackEntry?.destination?.route ?: MeliScreen.Busqueda.name
    )
    Scaffold(
        topBar = {
            MeliAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        },
        snackbarHost = { SnackbarHost(viewModel.snackbarHostState) },
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = MeliScreen.Busqueda.name
            ) {
                composable(route = MeliScreen.Busqueda.name) {
                    SearchScreen(
                        viewModel = viewModel,
                        onResultClick = { result ->
                            viewModel.searchResultSelected = result
                            navController.navigate(MeliScreen.Detalle.name)
                        }
                    )
                }

                composable(route = MeliScreen.Detalle.name) { navBackStackEntry ->
                    ResultDetails(viewModel.searchResultSelected)
                }

            }
        }
    }
}

enum class MeliScreen(@StringRes val title: Int) {
    Busqueda(title = R.string.app_name),
    Detalle(title = R.string.detalle),

}


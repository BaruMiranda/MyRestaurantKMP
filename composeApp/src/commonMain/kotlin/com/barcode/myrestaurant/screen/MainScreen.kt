package com.barcode.myrestaurant.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.barcode.myrestaurant.screen.bottom_bar.FavTab
import com.barcode.myrestaurant.screen.bottom_bar.HomeTab
import com.barcode.myrestaurant.screen.bottom_bar.MenuTab

class MainScreen : Screen {
    @OptIn(ExperimentalVoyagerApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(
            HomeTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(HomeTab, FavTab, MenuTab)
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(it.current.options.title) })
                },
                bottomBar = {
                    NavigationBar {
                        val tabNavigator = LocalTabNavigator.current

                        NavigationBarItem(
                            selected = tabNavigator.current.key == key,
                            label = { Text(HomeTab.options.title) },
                            icon = {
                                Icon(
                                    painter = HomeTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = HomeTab }
                        )


                        NavigationBarItem(
                            selected = tabNavigator.current.key == key,
                            label = { Text(FavTab.options.title) },
                            icon = {
                                Icon(
                                    painter = FavTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = FavTab }
                        )


                        NavigationBarItem(
                            selected = tabNavigator.current.key == key,
                            label = { Text(MenuTab.options.title) },
                            icon = {
                                Icon(
                                    painter = MenuTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = MenuTab }
                        )

                    }
                },
                content = {
                    CurrentTab()
                }
            )
        }
    }
}
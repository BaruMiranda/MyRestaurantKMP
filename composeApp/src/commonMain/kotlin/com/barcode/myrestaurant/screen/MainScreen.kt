package com.barcode.myrestaurant.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.barcode.myrestaurant.screen.cart.CartTab
import com.barcode.myrestaurant.screen.home.HomeTab
import com.barcode.myrestaurant.screen.profile.ProfileTab
import com.barcode.myrestaurant.ui.theme.RestaurantColors

class MainScreen : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val tabs = listOf(HomeTab, CartTab, ProfileTab)

        TabNavigator(
            HomeTab,
            tabDisposable = { TabDisposable(it, tabs) },
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBar(containerColor = RestaurantColors.Surface) {
                        tabs.forEach { tab -> TabNavigationItem(tab) }
                    }
                },
                content = { innerPadding ->
                    Box(Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                        CurrentTab()
                    }
                },
            )
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val selected = tabNavigator.current.key == tab.key

    NavigationBarItem(
        selected = selected,
        label = { Text(tab.options.title) },
        icon = {
            tab.options.icon?.let {
                Icon(painter = it, contentDescription = tab.options.title)
            }
        },
        onClick = { tabNavigator.current = tab },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = RestaurantColors.Primary,
            selectedTextColor = RestaurantColors.Primary,
            indicatorColor = RestaurantColors.PrimaryContainer,
            unselectedIconColor = RestaurantColors.OnSurfaceVariant,
            unselectedTextColor = RestaurantColors.OnSurfaceVariant,
        ),
    )
}

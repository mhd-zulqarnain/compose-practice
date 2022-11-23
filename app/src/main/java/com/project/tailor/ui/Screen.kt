package com.project.tailor.ui
sealed class Screen(val route: String) {
    object Home: Screen("home"){
        override fun toString(): String {
            return "home"
        }
    }
    object Details: Screen("details"){
        override fun toString(): String {
            return "details"
        }
    }
}
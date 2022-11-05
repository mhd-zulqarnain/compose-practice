package com.project.tailor.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Activity.navigateToActivity(name: Class<*>?) {
    val intent = Intent(this, name)
    startActivity(intent)
    finish()
}

fun Fragment.navigateUsingAction(actionDirection: Int) {
    this.findNavController().navigate(actionDirection)
}

fun Fragment.hideKeyboard() {
    val imm = this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity?.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.setVisible(visible: Boolean) {
    if (visible)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE

}
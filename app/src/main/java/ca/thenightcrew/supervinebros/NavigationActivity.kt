package ca.thenightcrew.supervinebros

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class NavigationActivity : AppCompatActivity(), Controller {
    val navController by lazy { findNavController(R.id.nav_host_fragment) }
    override val backButton: View by lazy { findViewById(R.id.controller_back_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigator)
        backButton.setOnClickListener { goHome() }
    }
}

interface Controller {
    val backButton: View

    fun loadFragment(destination: Fragment) {}

    fun goHome() {

    }

    fun hideControls() {
        backButton.visibility = View.INVISIBLE
    }

    fun showControls() {
        backButton.visibility = View.VISIBLE
    }
}
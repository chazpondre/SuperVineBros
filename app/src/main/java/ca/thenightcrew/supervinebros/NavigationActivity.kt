package ca.thenightcrew.supervinebros

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

class NavigationActivity : AppCompatActivity() /*, Controller */{
    val navController by lazy { findNavController(R.id.nav_host_fragment) }
//    override val backButton: View by lazy { findViewById(R.id.controller_back_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigator)
//        backButton.setOnClickListener { goHome()
//            Snackbar.make(it, "Clicked", Snackbar.LENGTH_SHORT).show()
//        }
    }
}

//interface Controller {
//    val backButton: View
//    val navController: NavController
//
//    fun loadFragment(destination: Fragment) {}
//
//    fun goHome() {
//        navController.navigateUp()
//
//    }
//
//    fun hideControls() {
//        backButton.visibility = View.INVISIBLE
//    }
//
//    fun showControls() {
//        backButton.visibility = View.VISIBLE
//    }
//}
package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.game_engine.AppInfo
import ca.thenightcrew.supervinebros.game_engine.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_splash, container, false)


    override fun onStart() {
        super.onStart()

        Utils.Threads.runOnDBThread {
            runBlocking {
                delay(400)
                gotoNextScreen()
            }
        }
    }

    private fun gotoNextScreen() {
        this@SplashFragment.context?.also {
            Utils.Threads.runOnMainThread(it) {
                if (AppInfo.player == null) {
                    val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    requireView().findNavController().navigate(action)
                } else {
                    val action = SplashFragmentDirections.actionSplashFragmentToMenuFragment()
                    requireView().findNavController().navigate(action)
                }
            }
        }
    }
}
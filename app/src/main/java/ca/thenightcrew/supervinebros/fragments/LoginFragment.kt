package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.database.Player
import ca.thenightcrew.supervinebros.db
import ca.thenightcrew.supervinebros.game_engine.AppInfo
import ca.thenightcrew.supervinebros.game_engine.Utils
import kotlinx.coroutines.runBlocking


class LoginFragment : Fragment() {
    private val loginButton: View by lazy { requireView().findViewById(R.id.login_button) }
    private val createUserButton: View by lazy { requireView().findViewById(R.id.login_to_create_user_button) }
    private val usernameInput: EditText by lazy { requireView().findViewById(R.id.loginUserName) }
    private val passwordInput: EditText by lazy { requireView().findViewById(R.id.loginPassword) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            Utils.Threads.runOnDBThread {
                runBlocking {
                    val user = db.player()
                        .login(usernameInput.text.toString(), passwordInput.text.toString())
                    loginUser(user, view)
                }
            }
        }

        createUserButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToCreateNewUserFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun loginUser(user: Player?, view: View) {
        if (user != null) {
            AppInfo.player = user
            val action = LoginFragmentDirections.actionLoginFragmentToMenuFragment()
            Utils.Threads.runOnMainThread(view.context) {
                view.findNavController().navigate(action)
            }
        } else Utils.Threads.runOnMainThread(view.context) {
            usernameInput.error = "Incorrect Username/Password"
            passwordInput.error = "Incorrect Username/Password"
        }
    }
}

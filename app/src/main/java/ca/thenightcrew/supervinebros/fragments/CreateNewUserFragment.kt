package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.findNavController
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.database.Player
import ca.thenightcrew.supervinebros.db
import ca.thenightcrew.supervinebros.game_engine.AppInfo
import ca.thenightcrew.supervinebros.game_engine.Utils
import kotlinx.coroutines.runBlocking

class CreateNewUserFragment : Fragment() {
    private val backButton: View by lazy { requireView().findViewById(R.id.create_back_button) }
    private val createUserButton: View by lazy { requireView().findViewById(R.id.create_user_button) }
    private val usernameInput: EditText by lazy { requireView().findViewById(R.id.createUserName) }
    private val passwordInput: EditText by lazy { requireView().findViewById(R.id.createPassword) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create_new_user, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            val action =
                CreateNewUserFragmentDirections.actionCreateNewUserFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }
        createUserButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.length < 3 && password.length < 3) {
                usernameInput.error = "Username/Password must have 3 or more characters"
                passwordInput.error = "Username/Password must have 3 or more characters"
                return@setOnClickListener
            }

            Utils.Threads.runOnDBThread {
                runBlocking {
                    if (db.player().userExists(username)) Utils.Threads
                        .runOnMainThread(view.context) {
                            usernameInput.error = "Username Already Taken"
                        }

                    val player = Player(username, 0, 0, password, 3)
                    db.player().add(player)
                    AppInfo.player = player

                    Utils.Threads.runOnMainThread(view.context) {
                        val action =
                            CreateNewUserFragmentDirections.actionCreateNewUserFragmentToMenuFragment()
                        view.findNavController().navigate(action)
                    }
                }
            }
        }
    }
}
package com.artelsv.petprojectsecond.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ActivityAuthBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthActivity : AppCompatActivity() {

    private lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var navHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        appNavigator = AppNavigator(this, R.id.container)

        setListeners(binding)
        setObservers(binding)
    }

    private fun setListeners(binding: ActivityAuthBinding) {
        binding.btnLoginGuest.setOnClickListener {
            viewModel.authAsGuest()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.authAsUser()
        }

        binding.etLogin.addTextChangedListener {
            binding.tilLogin.error = null
            viewModel.loginError.postValue(null)
        }

        binding.etPassword.addTextChangedListener {
            binding.tilPassword.error = null
            viewModel.passwordError.postValue(null)
        }
    }

    private fun setObservers(binding: ActivityAuthBinding) {
        viewModel.auth.observe(this, {
            if (it != null && it) {
                router.newRootScreen(Screens.mainActivity(this))
            }
        })

        viewModel.error.observe(this, {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.guestSession.observe(this, {
            it?.let {
                if (it) router.newRootScreen(Screens.mainActivity(this))
            }
        })

        viewModel.session.observe(this, {
            it?.let {
                if (it) router.newRootScreen(Screens.mainActivity(this))
            }
        })

        viewModel.loginError.observe(this, {
            if (!it.isNullOrEmpty()) {
                binding.tilLogin.error = it
            }
        })

        viewModel.passwordError.observe(this, {
            if (!it.isNullOrEmpty()) {
                binding.tilPassword.error = it
            }
        })
    }

    override fun onStart() {
        super.onStart()
        navHolder.setNavigator(appNavigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    override fun onBackPressed() {
        router.exit()
    }
}
package com.artelsv.petprojectsecond.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ActivityAuthBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthActivity : AppCompatActivity(R.layout.activity_auth) {

    private lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var navHolder: NavigatorHolder

    private val binding: ActivityAuthBinding by viewBinding(ActivityAuthBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        appNavigator = AppNavigator(this, R.id.container)

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.apply {
            btnLoginGuest.setOnClickListener {
                this@AuthActivity.viewModel.authAsGuest()
            }

            btnLogin.setOnClickListener {
                this@AuthActivity.viewModel.authAsUser(applicationContext)
            }

            etLogin.addTextChangedListener {
                binding.tilLogin.error = null
                this@AuthActivity.viewModel.loginError.postValue(null)
            }

            etPassword.addTextChangedListener {
                binding.tilPassword.error = null
                this@AuthActivity.viewModel.passwordError.postValue(null)
            }
        }
    }

    private fun setObservers() {
        viewModel.auth.observe(this, {
            if (it != null && it) {
                viewModel.navigateToMain(this)
            }
        })

        viewModel.error.observe(this, {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.guestSession.observe(this, {
            it?.let {
                if (it) viewModel.navigateToMain(this)
            }
        })

        viewModel.session.observe(this, {
            it?.let {
                if (it) viewModel.navigateToMain(this)
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
        viewModel.navigateBack()
    }
}
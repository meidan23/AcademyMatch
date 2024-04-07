package com.cropx.academymatch

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.cropx.academymatch.databinding.ActivityWelcomeBinding
import com.google.gson.Gson

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        PreferencesHelper.init(this)

        val userData = Gson().fromJson(
            PreferencesHelper.userData, Match::class.java
        )

        if (userData != null) {
            UserDataService.currentUser = userData
            startActivity(
                Intent(
                    this, MainActivity::class.java
                )
            )
        }

        binding.registerButton.setOnClickListener {
            startActivity(
                Intent(
                    this, RegistrationActivity::class.java
                )
            )
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
    }
}
package com.psi.caffeine.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.psi.caffeine.R
import com.psi.caffeine.ui.auth.login.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        
        supportFragmentManager.commit {
            add(R.id.fragment_container_auth, LoginFragment())
        }
    }
}
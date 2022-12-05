package com.psi.caffeine.ui.auth.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.psi.caffeine.R
import com.psi.caffeine.databinding.FragmentRegisterBinding
import com.psi.caffeine.model.User
import com.psi.caffeine.ui.main.MainActivity
import io.reactivex.Observable
import java.util.UUID

class RegisterFragment : Fragment() {
    
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    
    private var isFromSuccessRegister = false
    
    private lateinit var viewModel: RegisterViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        
        validateInput()
        
        binding?.apply {
            btnRegister.setOnClickListener {
                val username = tilUsername.text.toString()
                val email = tilEmail.text.toString()
                val password = tilPassword.text.toString()
                
                val user = User(
                    UUID.randomUUID().toString(),
                    username,
                    email,
                    password,
                    "https://picsum.photos/200/300?random=1",
                    false
                )
    
                viewModel.isEmailExist(email).observe(viewLifecycleOwner) { isExists ->
                    if(isExists) {
                        if (!isFromSuccessRegister)
                            Toast.makeText(requireContext(), "Email already exists", Toast.LENGTH_SHORT).show()
                    } else {
                        isFromSuccessRegister = true
                        viewModel.register(user)
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.putExtra(MainActivity.EXTRA_USERNAME, user.username)
                        intent.putExtra(MainActivity.EXTRA_AVATAR, user.avatarUrl)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        }
    }
    
    @SuppressLint("CheckResult")
    private fun validateInput() {
        val emptyUsername = RxTextView.textChanges(binding?.edtUsername?.editText!!)
            .map { it.isEmpty() }
        
        emptyUsername.subscribe {
            binding?.tilUsername?.error = if (it) "Username cannot be empty" else null
        }
        
        val emptyEmail = RxTextView.textChanges(binding?.edtEmail?.editText!!)
            .map { it.isEmpty() }
        
        emptyEmail.subscribe {
            binding?.tilEmail?.error = if (it) "Email cannot be empty" else null
        }
        
        val invalidEmail = RxTextView.textChanges(binding?.edtEmail?.editText!!)
            .map { !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() }
        
        invalidEmail.subscribe {
            binding?.tilEmail?.error = if (it) "Email is not valid" else null
        }
        
        val invalidPassword = RxTextView.textChanges(binding?.edtPassword?.editText!!)
            .map { it.length < 6 }
        
        invalidPassword.subscribe {
            binding?.tilPassword?.error = if (it) "Password must be at least 6 characters" else null
        }
        
        Observable.combineLatest(
            emptyUsername,
            emptyEmail,
            invalidEmail,
            invalidPassword
        ) { username, email, invalidEmail, invalidPassword ->
            !username && !email && !invalidEmail && !invalidPassword
        }.subscribe {
            binding?.btnRegister?.isEnabled = it
        }
    }
    
}
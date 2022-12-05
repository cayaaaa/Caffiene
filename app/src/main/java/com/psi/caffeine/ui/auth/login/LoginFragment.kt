package com.psi.caffeine.ui.auth.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.widget.RxTextView
import com.psi.caffeine.R
import com.psi.caffeine.admin_ui.home.AdminHomeActivity
import com.psi.caffeine.databinding.FragmentLoginBinding
import com.psi.caffeine.ui.auth.register.RegisterFragment
import com.psi.caffeine.ui.main.MainActivity
import io.reactivex.Observable

class LoginFragment : Fragment() {
    
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    
    private lateinit var viewModel: LoginViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        
        validateInput()
        
        binding?.apply {
            btnLogin.setOnClickListener {
                val username = tilUsername.text.toString()
                val password = tilPassword.text.toString()
                viewModel.login(username, password)?.observe(viewLifecycleOwner) {
                    if (it != null) {
                        if (it.isAdmin) {
                            val intent = Intent(activity, AdminHomeActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra(MainActivity.EXTRA_USERNAME, it.username)
                            intent.putExtra(MainActivity.EXTRA_AVATAR, it.avatarUrl)
                            startActivity(intent)
                            activity?.finish()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            
            tvDontHaveAnAccount.setOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.fragment_container_auth, RegisterFragment())
                    addToBackStack(null)
                }
            }
        }
    }
    
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
    
    @SuppressLint("CheckResult")
    private fun validateInput() {
        val emptyUsername = RxTextView.textChanges(binding?.edtUsername?.editText!!)
            .map { it.isEmpty() }
        
        val invalidPassword = RxTextView.textChanges(binding?.edtPassword?.editText!!)
            .map { it.length < 6 }
        
        emptyUsername.subscribe {
            binding?.tilUsername?.error = if (it) "Username cannot be empty" else null
        }
        
        invalidPassword.subscribe {
            binding?.tilPassword?.error = if (it) "Password must be at least 6 characters" else null
        }
        
        Observable.combineLatest(emptyUsername, invalidPassword) { a, b -> !a && !b }
            .subscribe {
                binding?.btnLogin?.isEnabled = it
            }
    }
    
}
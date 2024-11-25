package com.example.noteapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.noteapp.databinding.FragmentProfileBinding
import com.example.noteapp.ui.login.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // ViewModel instance (assume AuthViewModel is responsible for fetching user data)
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Load the user profile data
        authViewModel.loadUserProfile()

        // Observe the LiveData that contains user information
        authViewModel.userLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                // Update UI with user profile details
                binding.txtUsername.setText(user?.username)
                binding.txtEmail.setText(user?.email)
            } else {
                Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        })
        binding.btnUpdateProfile.setOnClickListener {
            Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
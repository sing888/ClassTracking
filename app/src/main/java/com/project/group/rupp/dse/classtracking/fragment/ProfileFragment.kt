package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.SigninActivity
import com.project.group.rupp.dse.classtracking.databinding.FragmentProfileBinding
import com.project.group.rupp.dse.classtracking.models.Profile
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.ProfileViewModel
import com.squareup.picasso.Picasso

class ProfileFragment: Fragment(){

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.logoutButton.setOnClickListener{
            // start sign in activity
            startActivity(Intent(requireContext(), SigninActivity::class.java))
        }

        profileViewModel.profileModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when(uiState.status){
                UiStateStatus.loading -> {
                    binding.loadingLayout.visibility = View.VISIBLE
                    binding.loadingProgress.visibility = View.VISIBLE
                }
                UiStateStatus.success -> {
                    binding.loadingLayout.visibility = View.GONE
                    binding.loadingProgress.visibility = View.GONE
                    loadData(uiState.data!!)
                }
                UiStateStatus.error -> {
                    binding.loadingLayout.visibility = View.GONE
                    binding.loadingProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        profileViewModel.getProfile()
    }

    private fun loadData(data: Profile){
        Picasso.get().load(data.profilePicture).into(binding.profilePicture)
        Picasso.get().load(data.coverPicture).into(binding.coverPicture)
        binding.username.text = data.firstName + " " + data.lastName
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
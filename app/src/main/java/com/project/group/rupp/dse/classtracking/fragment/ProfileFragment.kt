package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.SigninActivity
import com.project.group.rupp.dse.classtracking.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(){

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
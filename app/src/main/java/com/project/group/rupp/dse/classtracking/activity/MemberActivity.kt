package com.project.group.rupp.dse.classtracking.activity

import android.os.Bundle
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.adapter.MemberAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityMemberBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.MemberViewModel

class MemberActivity: AppCompatActivity(){
    private var _binding: ActivityMemberBinding? = null
    private val binding get() = _binding!!
    private val memberViewModel: MemberViewModel by viewModels()
    private val adapter = MemberAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val room_id = intent.getStringExtra("room_id")

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvName.text = "Members"

        memberViewModel.memberUiState.observe(this, Observer {uiState ->
            when(uiState.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    adapter.setDataset(uiState.data?.data!!)
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
                }
            }

        })
        memberViewModel.getMember(this, room_id!!)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvMember.layoutManager = layoutManager
        binding.rvMember.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
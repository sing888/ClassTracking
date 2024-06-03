package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.CreateNewsActivity
import com.project.group.rupp.dse.classtracking.adapter.TeacherNewsAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentTeacherNewsBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherNews
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.MainViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherNewsViewModel

class TeacherNewsFragment: Fragment() {
    private var _binding: FragmentTeacherNewsBinding? = null
    private val binding get() = _binding!!

    private val teacherNewsViewModel: TeacherNewsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val adapter = TeacherNewsAdapter()

    private var account_id: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTeacherNewsBinding.bind(view)

        var data: List<GetTeacherNews>
        var adapter = TeacherNewsAdapter()
        var roomtype = "teacher"

        teacherNewsViewModel.teacherNewsModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status){
                UiStateStatus.loading -> {
                    binding.progressLayout.visibility = View.VISIBLE
                    binding.textViewTeacherNews.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.progressLayout.visibility = View.GONE
                    data = uiState.data?.data!!
                    if (data.isEmpty()){
                        binding.textViewTeacherNews.visibility = View.VISIBLE
                        binding.textViewTeacherNews.text = "No news found"
                    } else {
                        adapter.setDataset(data)
                    }
                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    binding.textViewTeacherNews.visibility = View.VISIBLE
                    binding.textViewTeacherNews.text = "Something went wrong!"
                    Log.e("TeacherNewsFragment", "Error fetching teacher news: ${uiState.message}")

                }
            }
        })

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewNews.layoutManager = layoutManager
        binding.recyclerViewNews.adapter = adapter

        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(requireContext(), CreateNewsActivity::class.java)
            intent.putExtra("header", "Create News")
            startActivity(intent)
        }

        mainViewModel.account_id.observe(viewLifecycleOwner, {
            account_id = it
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
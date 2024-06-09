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
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.adapter.NewsAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentStudentNewsBinding
import com.project.group.rupp.dse.classtracking.models.GetNews
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.NewsViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel

class StudentNewsFragment: Fragment() {
    private var _binding: FragmentStudentNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by viewModels()
    private val roomMainViewModel: RoomMainViewModel by activityViewModels()

    private val adapter = NewsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStudentNewsBinding.bind(view)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewNews.layoutManager = layoutManager
        binding.recyclerViewNews.adapter = adapter

        newsViewModel.getStudentNews(requireContext(), roomMainViewModel.roomId.value!!)

        var data: List<GetNews>
        newsViewModel.newsModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status){
                UiStateStatus.loading -> {
                    binding.progressLayout.visibility = View.VISIBLE
                    binding.textViewStudentNews.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.progressLayout.visibility = View.GONE
                    data = uiState.data?.data!!
                    if (data.isEmpty()){
                        binding.textViewStudentNews.visibility = View.VISIBLE
                        binding.textViewStudentNews.text = "No news found"
                    } else {
                        adapter.setDataset(data)
                    }
                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    binding.textViewStudentNews.visibility = View.VISIBLE
                    binding.textViewStudentNews.text = "News not found"
                }
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
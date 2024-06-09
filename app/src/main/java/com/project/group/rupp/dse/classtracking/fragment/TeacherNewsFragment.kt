package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.CreateNewsActivity
import com.project.group.rupp.dse.classtracking.adapter.NewsAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentTeacherNewsBinding
import com.project.group.rupp.dse.classtracking.models.GetNews
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.NewsViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel

class TeacherNewsFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var _binding: FragmentTeacherNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by viewModels()
    private val roomMainViewModel: RoomMainViewModel by activityViewModels()

    private val adapter = NewsAdapter()

    private var isRefreshing = false

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

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewNews.layoutManager = layoutManager
        binding.recyclerViewNews.adapter = adapter

        newsViewModel.getTeacherNews(requireContext(), roomMainViewModel.roomId.value!!)

        binding.swipeRefreshContainer.setOnRefreshListener(this)

        var data: List<GetNews>
        newsViewModel.newsModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status){
                UiStateStatus.loading -> {
                    binding.progressLayout.visibility = View.VISIBLE
                    binding.textViewNews.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.progressLayout.visibility = View.GONE
                    data = uiState.data?.data!!
                    if (data.isEmpty()){
                        binding.textViewNews.visibility = View.VISIBLE
                        binding.textViewNews.text = "No news found"
                    } else {
                        adapter.setDataset(data)
                    }
                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    binding.textViewNews.visibility = View.VISIBLE
                    binding.textViewNews.text = "This room has no news at the moment."
                    binding.textViewNews.textSize = 16F
                }
            }
        })


        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(requireContext(), CreateNewsActivity::class.java)
            intent.putExtra("header", "Create News")
            intent.putExtra("classroom_id", roomMainViewModel.roomId.value!!)
            startActivity(intent)
        }


    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            newsViewModel.getTeacherNews(requireContext(), roomMainViewModel.roomId.value!!)
            Toast.makeText(requireContext(), "Refreshed", Toast.LENGTH_LONG).show()
            binding.swipeRefreshContainer.isRefreshing = false
        }, 300)

    }
}
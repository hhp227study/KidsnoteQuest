package com.hhp227.kidsnotequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import com.google.android.material.appbar.MaterialToolbar
import com.hhp227.kidsnotequest.adapters.ImageLoadStateAdapter
import com.hhp227.kidsnotequest.adapters.ImagePagingAdapter
import com.hhp227.kidsnotequest.data.Image
import com.hhp227.kidsnotequest.databinding.FragmentMainBinding
import com.hhp227.kidsnotequest.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private val adapter = ImagePagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel
        binding.contentMain.recyclerView.adapter = adapter.withLoadStateFooter(ImageLoadStateAdapter())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavAppbar(binding.toolbar)
        observeLiveData()
        adapter.setOnItemClickListener(object : ImagePagingAdapter.OnItemClickListener {
            override fun onItemClick(item: Image) {
                val array = adapter.snapshot().filterNotNull().toTypedArray()
                val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(item, array)

                findNavController().navigate(direction)
            }

            override fun onLikeClick(item: Image) {
                mainViewModel.postFavorite(item)
            }
        })
        binding.contentMain.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
    }

    private fun setNavAppbar(toolbar: MaterialToolbar) {
        toolbar.setupWithNavController(findNavController())
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun observeLiveData() {
        adapter.loadState.observe(viewLifecycleOwner) { loadStates ->
            binding.contentMain.swipeRefreshLayout.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            binding.contentMain.isLoading = loadStates.refresh is LoadState.Loading
        }
    }
}
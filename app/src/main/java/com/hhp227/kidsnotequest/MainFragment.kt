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
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.hhp227.kidsnotequest.adapters.ImageLoadStateAdapter
import com.hhp227.kidsnotequest.adapters.ImagePagingAdapter
import com.hhp227.kidsnotequest.databinding.FragmentMainBinding
import com.hhp227.kidsnotequest.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel
        binding.contentMain.recyclerView.adapter = ImagePagingAdapter {
            val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(it ?: "")

            findNavController().navigate(direction)
        }.withLoadStateFooter(ImageLoadStateAdapter())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavAppbar(binding.toolbar)
        with(binding.contentMain) {
            swipeRefreshLayout.setOnRefreshListener {
                val adapter = recyclerView.adapter
                swipeRefreshLayout.isRefreshing = false

                if (adapter is ConcatAdapter) {
                    (adapter.adapters.first() as? ImagePagingAdapter)?.refresh()
                }
            }
        }
    }

    private fun setNavAppbar(toolbar: MaterialToolbar) {
        toolbar.setupWithNavController(findNavController())
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }
}
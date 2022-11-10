package com.hhp227.kidsnotequest

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private val adapter = ImagePagingAdapter()

    private val connectivityManager by lazy {
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MainLifecycleObserver())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel
        binding.contentMain.recyclerView.adapter = adapter.withLoadStateFooter(ImageLoadStateAdapter(adapter::retry))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavAppbar(binding.toolbar)
        observeLiveData()
        networkConnectionCheck()
        adapter.setOnItemClickListener(object : ImagePagingAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: Image) {
                val array = adapter.snapshot().items.toTypedArray()
                val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(item, position, array)

                findNavController().navigate(direction)
            }

            override fun onLikeClick(item: Image) {
                mainViewModel.postFavorite(item)
            }
        })
        binding.contentMain.swipeRefreshLayout.setOnRefreshListener(adapter::refresh)
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

    private fun networkConnectionCheck() {
        if (connectivityManager.isActiveNetworkMetered) {
            lifecycleScope.launch {
                showAlert()
            }
        }
    }

    private fun showAlert() {
        AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(getString(R.string.connection_error_title))
            .setMessage(getString(R.string.connection_error_message))
            .setPositiveButton(getString(R.string.connection_error_positive_button)) { _, _ ->
                networkConnectionCheck()
            }
            .setNegativeButton(getString(R.string.connection_error_negative_button)) { _, _ ->
                exitProcess(0)
            }
            .create()
            .show()
    }

    inner class NetworkCallback : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            networkConnectionCheck()
        }
    }

    inner class MainLifecycleObserver : DefaultLifecycleObserver {
        private val callback = NetworkCallback()

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            connectivityManager.registerDefaultNetworkCallback(callback)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            connectivityManager.unregisterNetworkCallback(callback)
            lifecycle.removeObserver(this)
        }
    }
}
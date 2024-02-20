package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.room.entities.StoreEntity
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.detail.DetailBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding :FragmentHomeBinding? = null
       private val binding get() = _binding!!

    private val homeViewModel:HomeViewModel by viewModels()
    private lateinit var mAdapter: StoresAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.onCreateViewModel()
        initUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initUI(){
        initRv()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.stores.collect{
                    mAdapter.subscribeToFlow(it)
                    if (it.isEmpty()){
                        binding.tvNoStores.isVisible = true
                        binding.rvStores.isVisible = false
                    }
                    else {
                        binding.tvNoStores.isVisible = false
                        binding.rvStores.isVisible = true
                    }
                }
            }
        }
    }

    private fun initRv(){
        mAdapter = StoresAdapter(emptyList()){ store -> navigateToStore(store) }
        binding.rvStores.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun navigateToStore(store:StoreEntity){
        val detailBottomSheet = DetailBottomSheet.newInstance(store.id)
        detailBottomSheet.show(requireActivity().supportFragmentManager, detailBottomSheet.tag)

        /*requireActivity().supportFragmentManager.commit {
            add(detailBottomSheet,detailBottomSheet.tag)
        }*/
    }

}
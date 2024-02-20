package com.example.myapplication.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.room.entities.StoreEntity
import com.example.myapplication.databinding.FragmentDetailBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailBottomSheet : BottomSheetDialogFragment() {

    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var binding: FragmentDetailBottomSheetBinding
    private lateinit var storeState:StoreEntity
    private var id:Long? = null



    companion object {
        @JvmStatic
        fun newInstance(id:Long):DetailBottomSheet =
            DetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putLong("id", id)
                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong("id")
        }
        /*with(arguments){
            id = this?.getLong("id")
        }
        id = arguments?.getLong("id")*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getStore(id!!)
        initUI()
        initListeners()
    }

    private fun initUI(){
        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.store.collect {store ->
                storeState = store
                Glide.with(requireContext())
                    .load(store.image)
                    .centerCrop()
                    .error(R.drawable.baseline_image_24)
                    .into(binding.imgDetail)
                binding.tvTitleDetail.text = store.name
            }
        }
    }

    private fun initListeners(){
        binding.btnWeb.setOnClickListener {
            navigateToWeb(storeState.web)
        }
        binding.btnPhone.setOnClickListener {
            phoneStore(storeState.phone)
        }
        binding.btnEdit.setOnClickListener {
            editStore()
            this.dismiss()
        }
        binding.btnDelete.setOnClickListener {
            deleteStore(storeState)
        }
    }

    private fun deleteStore(store: StoreEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Store")
            .setMessage("Are you sure you want to delete this store?")
            .setPositiveButton("Delete"){ _, _ ->
                this.dismiss()
                detailViewModel.deleteStore(store)
            }
            .setNegativeButton("No", null)
            .create()
            .show()

    }

    private fun navigateToWeb(url:String) {
        Log.println(Log.ASSERT, "URL", url)
        if (url.isBlank()) {
            val snackbar = Snackbar.make(
                requireView(),
                "This store has no website", Snackbar.LENGTH_LONG
            )
            snackbar.setAction("OK") { snackbar.dismiss() }
            snackbar.show()
        }
        else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            requireContext().startActivity(intent)
        }
    }

    private fun phoneStore(phone:Long){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        if (intent.resolveActivity(requireActivity().packageManager) != null){
            requireActivity().startActivity(intent)
        }
        else {
            val snackbar =Snackbar.make(requireView(),
                "Have not found any compatible app", Snackbar.LENGTH_LONG)
            snackbar.setAction("OK") { snackbar.dismiss() }
            snackbar.show()
        }
    }
    private fun editStore(){
        val navController = findNavController()
        detailViewModel.editStore(storeState.id,navController)
    }

}
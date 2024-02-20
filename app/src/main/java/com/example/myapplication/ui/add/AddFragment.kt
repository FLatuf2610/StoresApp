package com.example.myapplication.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.room.entities.StoreEntity
import com.example.myapplication.databinding.FragmentAddBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddFragment : Fragment() {

    private val addViewModel by viewModels<AddViewModel>()
    private var _binding :FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val args:AddFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(args.editMode)
        val activity = requireActivity()
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            //findNavController().navigateUp()
        }


    }

    private fun initUI(editMode: Boolean) {
        addViewModel.getStoreById(args.id)
        initListeners()
        if (editMode) {
            viewLifecycleOwner.lifecycleScope.launch {
                addViewModel.store.collect {edStore ->
                    binding.toolbar.title = getString(R.string.edit_store)
                    binding.etName.setText(edStore.name)
                    binding.etPhone.setText(edStore.phone.toString())
                    binding.etImageURL.setText(edStore.image)
                    binding.etWebURL.setText(edStore.web)
                    binding.btnAdd.text = getString(R.string.edit_store)
                }
            }
        }
    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener { insertStore() }
        binding.etImageURL.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && binding.etImageURL.text?.isNotBlank()!!){
                Glide.with(this)
                    .load(binding.etImageURL.text)
                    .centerCrop()
                    .error(R.drawable.baseline_image_24)
                    .centerCrop()
                    .into(binding.ivPreview)
            }
        }
    }

    private fun insertStore() {
        val name = binding.etName.text.toString()
        val phone = binding.etPhone.text.toString()
        val web = binding.etWebURL.text.toString()
        val image = binding.etImageURL.text.toString()

        if (name.trim().isBlank() || phone.trim().isBlank()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Complete Required Inputs")
                .setMessage("Please complete all required inputs to add the store")
                .setPositiveButton("OK", null)
                .show()
        }
        else {
            val store = StoreEntity(id = args.id, name = name, phone = phone.toLong(), web = web, image = image)
            addViewModel.insertStore(store)
            binding.etName.setText("")
            binding.etPhone.text = null
            binding.etImageURL.setText("")
            binding.etWebURL.setText("")
            findNavController().navigateUp()
        }

    }




}
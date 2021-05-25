package com.example.kotlinchannelsforevents.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kotlinchannelsforevents.R
import com.example.kotlinchannelsforevents.databinding.FragmentOneBinding
import com.example.kotlinchannelsforevents.showSnackBar
import kotlinx.coroutines.flow.collect

private const val TAG = "FragmentOne"

class FragmentOne : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentOneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        binding.apply {
            btnGetResult.setOnClickListener { mainViewModel.getDataForFragmentOne() }
            btnGetError.setOnClickListener { mainViewModel.getFailedRequestForFragmentOne("One") }
            btnNextScreen.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentOne_to_fragmentTwo)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.eventFlow.collect { event ->
                Log.d(TAG, "Received : $event")
                if (event is MainViewModel.Events.ResultOne) {
                    showSnackBar(binding.root, event.dataOne, anchor = binding.btnNextScreen)
                } else if (event is MainViewModel.Events.ErrorForOne) {
                    showSnackBar(binding.root, event.errorMessage, anchor = binding.btnNextScreen)
                }
            }
        }
    }
}
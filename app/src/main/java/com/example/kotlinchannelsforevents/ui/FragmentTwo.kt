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
import com.example.kotlinchannelsforevents.databinding.FragmentTwoBinding
import com.example.kotlinchannelsforevents.showSnackBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

private const val TAG = "FragmentTwo"
class FragmentTwo: Fragment() {

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTwoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnGetResult.setOnClickListener { mainViewModel.getDataForFragmentTwo() }
            btnGetError.setOnClickListener { mainViewModel.getFailedRequestForFragmentTwo("Two") }
            btnNextScreen.setOnClickListener { findNavController().navigate(R.id.action_fragmentTwo_to_fragmentThree) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.eventFlow.collect { event ->
                Log.d(TAG, "Received : $event")
                if (event is MainViewModel.Events.ResultTwo) {
                    showSnackBar(binding.root, event.dataTwo.toString(), anchor = binding.btnNextScreen)
                } else if (event is MainViewModel.Events.ErrorForTwo) {
                    showSnackBar(binding.root, event.errorMessageTwo, anchor = binding.btnNextScreen )
                }
            }
        }

    }

}
package com.example.kotlinchannelsforevents.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.kotlinchannelsforevents.databinding.FragmentThreeBinding
import com.example.kotlinchannelsforevents.showSnackBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

private const val TAG = "FragmentThree"
class FragmentThree: Fragment() {

    private var _binding: FragmentThreeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentThreeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        binding.apply {
            btnGetResult.setOnClickListener {
                Log.d(TAG, "Button three Clicked")
                mainViewModel.getDataForFragmentThree()
            }
            btnGetError.setOnClickListener { mainViewModel.getFailedRequestForFragmentThree("Three") }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.eventFlow.collect { event ->
                Log.d(TAG, "Received : $event")
                if (event is MainViewModel.Events.ResultThree) {
                    showSnackBar(binding.root, event.dataThree.toString())
                } else if (event is MainViewModel.Events.ErrorForThree) {
                    showSnackBar(binding.root, event.errorMessageThree)
                }
            }
        }

    }

}
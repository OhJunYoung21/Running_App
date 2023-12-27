package com.test.running_beta.UI

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.running_beta.databinding.BottomsheetdialogfragmentBinding

class RunBottomSheetFragment(context: Context) : BottomSheetDialogFragment() {


    private lateinit var binding:BottomsheetdialogfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomsheetdialogfragmentBinding.inflate(layoutInflater)

        val view = binding.root

        return view
    }


    companion object {

        private const val tag = "123"

    }


}
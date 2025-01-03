package com.learn.nasho.ui.alerts

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.learn.nasho.databinding.FragmentAlertLogoutBinding
import com.learn.nasho.ui.viewmodels.user.LogoutViewModel


class LogoutAlert(logoutViewModel: LogoutViewModel) : DialogFragment() {

    private var _binding: FragmentAlertLogoutBinding? = null
    private val binding get() = _binding!!
    private var viewModel = logoutViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertLogoutBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNo.setOnClickListener {
            dismiss()
        }

        binding.btnYes.setOnClickListener {
            viewModel.logoutUser()
        }
    }

    fun dismissDialog() {
        dismiss()
    }
}
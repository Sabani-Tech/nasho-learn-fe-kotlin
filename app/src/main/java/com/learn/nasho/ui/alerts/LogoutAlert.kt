package com.learn.nasho.ui.alerts

import android.app.Dialog
import android.content.Context
import android.content.Intent
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
import com.learn.nasho.ui.views.LoginActivity


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

/*            // Dismiss the dialog
            dismiss()

            // Clear access token from SharedPreferences
            clearAccessToken()

            // Start login activity
            startLoginActivity()*/
        }
    }

    fun dismissDialog() {
        dismiss()
    }

    private fun clearAccessToken() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("accessToken")
        editor.apply()
    }

    private fun startLoginActivity() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }
}
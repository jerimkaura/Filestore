package com.jerimkaura.filestore.presentation.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jerimkaura.filestore.MainActivity
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)

        val pref: SharedPreferences = activity!!.getSharedPreferences(
            getString(R.string.profile_preference_key),
            Context.MODE_PRIVATE
        )


        binding!!.txtName.setText(
            pref.getString(
                getString(R.string.profile_name_key),
                getString(R.string.profile_name_default)
            )
        )

        binding!!.btnSave.setOnClickListener {
            pref.edit()
                .putString(getString(R.string.profile_name_key), binding!!.txtName.text.toString())
                .apply()

            (activity as MainActivity).hideKeyboard()
            Snackbar.make(view, getString(R.string.profile_update_success), Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}

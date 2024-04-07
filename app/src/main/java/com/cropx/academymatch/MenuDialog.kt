package com.cropx.academymatch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class MenuDialog(
    private val interaction: IMenuDialog
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewMatchesButton = view.findViewById<TextView>(R.id.view_matches_button)
        viewMatchesButton.setOnClickListener {
            interaction.onViewMatchesClicked()
            dismiss()
        }

        val findNewMatchesButton = view.findViewById<TextView>(R.id.find_new_matches_button)
        findNewMatchesButton.setOnClickListener {
            interaction.onFindNewMatchesClicked()
            dismiss()
        }

        val myProfileButton = view.findViewById<TextView>(R.id.my_profile_button)
        myProfileButton.setOnClickListener {
            interaction.onMyProfileClicked()
            dismiss()
        }

        val disconnectButton = view.findViewById<TextView>(R.id.disconnect_button)
        disconnectButton.setOnClickListener {
            UserDataService.currentUser = null
            PreferencesHelper.userData = null
            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return android.R.style.Theme_Translucent_NoTitleBar_Fullscreen
    }
}

interface IMenuDialog {
    fun onViewMatchesClicked()

    fun onFindNewMatchesClicked()

    fun onMyProfileClicked()
}
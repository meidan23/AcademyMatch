package com.cropx.academymatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.cropx.academymatch.databinding.MatchSummaryBinding

class MatchSummaryDialog(
    private val match: Match,
    private val courseNumber: Int? = null,
    private val interaction: IMatchSummaryDialog? = null
) : DialogFragment() {

    private val binding by lazy {
        MatchSummaryBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.matchSummary.text = match.userGeneralData.fullName

        binding.matchDescription.text = match.personalPreferences?.description
        binding.assignmentWorkType.text =
            "He like to work: ${match.personalPreferences?.workType?.name}"
        binding.workDays.text = match.personalPreferences?.workDays?.joinToString { it.name }
        binding.spokenLanguages.text = match.personalPreferences?.mainLanguage?.name
        binding.studyingType.text = match.personalPreferences?.studyPreference?.name

        if (interaction == null || MatchDataHelper.isLinkedByCourseNumber(
                courseNumber,
                match.uuid
            )
        ) {
            binding.matchDecisionLayout.isVisible = false
        }

        binding.linkMatch.setOnClickListener {
            Toast.makeText(
                requireContext(), "${match.userGeneralData.fullName} Linked!", Toast.LENGTH_LONG
            ).show()
            interaction?.onLinkMatch(match)
            dismiss()
        }

        binding.unlinkMatch.setOnClickListener {
            Toast.makeText(
                requireContext(), "${match.userGeneralData.fullName} Removed!", Toast.LENGTH_LONG
            ).show()

            interaction?.onUnlinkMatch(match)
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return android.R.style.Theme_Translucent_NoTitleBar_Fullscreen
    }
}

interface IMatchSummaryDialog {
    fun onUnlinkMatch(match: Match)
    fun onLinkMatch(match: Match)
}
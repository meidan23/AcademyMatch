package com.cropx.academymatch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.cropx.academymatch.databinding.FragmentExploreMatchesBinding
import com.google.gson.Gson

class ExploreMatchesFragment : Fragment() {

    private lateinit var dataUpdateListener: DataUpdateListener

    private val binding by lazy {
        FragmentExploreMatchesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            UserDataService.currentUser!!.userGeneralData.coursesNumber
        )
        binding.courseSpinner.adapter = adapter

        binding.courseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                setupPossibleMatchesList(UserDataService.currentUser!!.userGeneralData.coursesNumber[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.courseSpinner.setSelection(0)
    }

    private fun setupPossibleMatchesList(courseNumber: Int) {
        val possibleMatches = MatchDataHelper.generatePossibleMatches(courseNumber)

        binding.possibleMatchesRv.adapter = UsersAdapter(requireContext(), object : IMyAdapter {
            override fun onMatchClicked(match: Match) {
                showMatchSummaryDialog(match)
            }

            override fun onChatBubbleClicked(match: Match) {
                MatchChatDialog(match).show(parentFragmentManager, "MatchSummaryDialog")
            }
        }, possibleMatches, courseNumber)
    }

    fun showMatchSummaryDialog(match: Match) {
        val courseNumber =
            UserDataService.currentUser!!.userGeneralData.coursesNumber[binding.courseSpinner.selectedItemPosition]
        MatchSummaryDialog(match, courseNumber, object : IMatchSummaryDialog {
            override fun onUnlinkMatch(match: Match) {
                UserDataService.currentUser!!.matches.remove(Pair(match.uuid, courseNumber))
                match.matches.remove(Pair(UserDataService.currentUser!!.uuid, courseNumber))
                dataUpdateListener.onDataUpdated()
                setupPossibleMatchesList(courseNumber)
                PreferencesHelper.userData = Gson().toJson(UserDataService.currentUser)
            }

            override fun onLinkMatch(match: Match) {
                UserDataService.currentUser!!.matches.add(Pair(match.uuid, courseNumber))
                match.matches.add(Pair(UserDataService.currentUser!!.uuid, courseNumber))
                dataUpdateListener.onDataUpdated()
                setupPossibleMatchesList(courseNumber)
                PreferencesHelper.userData = Gson().toJson(UserDataService.currentUser)
            }

        }).show(parentFragmentManager, "MatchSummaryDialog")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataUpdateListener) {
            dataUpdateListener = context
        } else {
            throw RuntimeException("$context must implement DataUpdateListener")
        }
    }
}
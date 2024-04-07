package com.cropx.academymatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cropx.academymatch.databinding.FragmentViewMatchesBinding

class ViewMatchesFragment : Fragment() {

    private val binding by lazy {
        FragmentViewMatchesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMyMatchesList()
    }

    private fun setupMyMatchesList() {
        binding.myMatchesRv.adapter = UsersAdapter(requireContext(), object : IMyAdapter {
            override fun onMatchClicked(match: Match) {
                MatchSummaryDialog(match).show(parentFragmentManager, "MatchSummaryDialog")
            }

            override fun onChatBubbleClicked(match: Match) {
                MatchChatDialog(match).show(parentFragmentManager, "MatchSummaryDialog")
            }
        }, MatchDataHelper.getLinkedMatches())
    }

    fun dataUpdated() {
        setupMyMatchesList()
    }
}
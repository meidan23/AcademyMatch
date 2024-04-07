package com.cropx.academymatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cropx.academymatch.databinding.MatchChatBinding

class MatchChatDialog(
    private val match: Match
) : DialogFragment() {

    private val binding by lazy {
        MatchChatBinding.inflate(layoutInflater)
    }

    private val responses = listOf("hey", "nice to meet you, would you like to work together?", "cool, text me: 05*-*******")
    private var responseIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userName.text = match.userGeneralData.fullName

        val messagesList: ArrayList<ChatMessage> = arrayListOf()
        val adapter = ChatMessagesAdapter(requireContext(), messagesList)
        binding.chatMessagesRv.adapter = adapter

        binding.sendMessageButton.setOnClickListener {
            if (binding.userInputEditText.text.isEmpty()) return@setOnClickListener

            val message = binding.userInputEditText.text
            val chatMessage = ChatMessage(
                UserDataService.currentUser!!.userGeneralData.fullName, message = message.toString()
            )

            binding.userInputEditText.setText("")
            messagesList.add(chatMessage)
            adapter.notifyItemInserted(messagesList.size - 1)

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    messagesList.add(
                        ChatMessage(
                            match.userGeneralData.fullName, responses[responseIndex]
                        )
                    )
                    adapter.notifyItemInserted(messagesList.size - 1)
                    responseIndex += 1
                }, 3000
            )
        }
    }

    override fun getTheme(): Int {
        return android.R.style.Theme_Translucent_NoTitleBar_Fullscreen
    }
}

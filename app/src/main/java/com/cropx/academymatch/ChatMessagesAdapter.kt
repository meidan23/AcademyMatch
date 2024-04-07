package com.cropx.academymatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ChatMessagesAdapter(
    private val context: Context, private val dataSet: List<ChatMessage>
) : RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageTv: TextView = view.findViewById(R.id.message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = dataSet[position]
        holder.messageTv.text = message.message

        if (message.senderName == UserDataService.currentUser?.userGeneralData?.fullName) {
            val params: RelativeLayout.LayoutParams =
                holder.messageTv.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_END)
            holder.messageTv.setLayoutParams(params)
        } else {
            val params: RelativeLayout.LayoutParams =
                holder.messageTv.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_START)
            holder.messageTv.setLayoutParams(params)
        }
    }

    override fun getItemCount() = dataSet.size
}

data class ChatMessage(val senderName: String, val message: String)
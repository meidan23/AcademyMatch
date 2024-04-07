package com.cropx.academymatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class UsersAdapter(
    private val context: Context,
    private val interaction: IMyAdapter,
    private val dataSet: List<Match>,
    private val courseNumber: Int? = null
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.match_name)
        val matchPercentage: TextView = view.findViewById(R.id.match_percentage)
        val matchImg: ImageView = view.findViewById(R.id.match_img)
        val chatButton: ImageView = view.findViewById(R.id.chat_button)
        val linkedIcon: ImageView = view.findViewById(R.id.linked_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = dataSet[position]
        holder.textView.text = match.userGeneralData.fullName

        if (MatchDataHelper.isLinkedByCourseNumber(courseNumber, match.uuid)) {
            holder.matchPercentage.isVisible = false
            holder.chatButton.isVisible = true
            holder.linkedIcon.isVisible = true

            holder.chatButton.setOnClickListener {
                interaction.onChatBubbleClicked(match)
            }
        } else {
            holder.linkedIcon.isVisible = false
            holder.chatButton.isVisible = false
            holder.matchPercentage.isVisible = true
            holder.matchPercentage.text = "${match.matchPercentage ?: 0}%"
        }

        val options: RequestOptions = RequestOptions().error(R.mipmap.ic_launcher_round)
            .transform(FitCenter(), RoundedCorners(24))

        Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(match.userGeneralData.imgUrl).apply(options).into(holder.matchImg)

        holder.itemView.setOnClickListener {
            interaction.onMatchClicked(match)
        }
    }

    override fun getItemCount() = dataSet.size
}

interface IMyAdapter {
    fun onMatchClicked(match: Match)
    fun onChatBubbleClicked(match: Match)
}

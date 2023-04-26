package edu.quinnipiac.ser210.witswarzone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter: ListAdapter<HighScore, ScoreAdapter.MyViewHolder>(DiffCallback)
{
    var scoreList: List<HighScore> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreAdapter.MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return scoreList.size;
    }

    override fun onBindViewHolder(holder: ScoreAdapter.MyViewHolder, position: Int)
    {
        holder.bind(position)
    }

    fun setScores(highScores: List<HighScore>?)
    {
        if(highScores != null)
            scoreList = highScores
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val userNameLabel: TextView = itemView.findViewById(R.id.userName_label)
        private val userRankLabel: TextView = itemView.findViewById(R.id.userRank_label)
        private val scoreLabel: TextView = itemView.findViewById(R.id.userScore_label)

        fun bind(position: Int)
        {
            val score = scoreList[position]
            userNameLabel.text = score.name
            userRankLabel.text = (position + 1).toString()
            scoreLabel.text = score.score.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<HighScore>() {
            override fun areItemsTheSame(oldItem: HighScore, newItem: HighScore): Boolean
            {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: HighScore, newItem: HighScore): Boolean
            {
                return (oldItem.name == newItem.name && oldItem.score == newItem.score)
            }
        }
    }
}
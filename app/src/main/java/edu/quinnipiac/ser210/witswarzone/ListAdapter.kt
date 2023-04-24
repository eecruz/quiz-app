package edu.quinnipiac.ser210.witswarzone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView

var categoryList : ArrayList<Category> = ArrayList()
var user_name: String = "Guest"

class ListAdapter(var navController: NavController, username: String)
    :RecyclerView.Adapter<ListAdapter.MyViewHolder>()
{
    init {
        user_name = username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.bind(position)
    }

    fun setCategoryListItems()
    {
        categoryList.add(Category.GENERAL)
        categoryList.add(Category.ARTLITERATURE)
        categoryList.add(Category.LANGUAGE)
        categoryList.add(Category.SCIENCENATURE)
        categoryList.add(Category.FOODDRINK)
        categoryList.add(Category.PEOPLEPLACES)
        categoryList.add(Category.GEOGRAPHY)
        categoryList.add(Category.HISTORYHOLIDAYS)
        categoryList.add(Category.ENTERTAINMENT)
        categoryList.add(Category.TOYSGAMES)
        categoryList.add(Category.MUSIC)
        categoryList.add(Category.MATHEMATICS)
        categoryList.add(Category.RELIGIONMYTHOLOGY)
        categoryList.add(Category.SPORTSLEISURE)

        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val categoryLabel: TextView = itemView.findViewById(R.id.category_name)
        private val categoryImage: ImageView = itemView.findViewById(R.id.category_image)
        private var pos:Int = 0

        init {
            itemView.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToGameFragment(categoryList[pos].apiInput, user_name)
                navController.navigate(action)

            }
        }
        fun bind(position:Int)
        {
            pos = position
            val category = categoryList[position]
            categoryLabel.text = category.display
            categoryImage.setImageResource(category.drawableID)
        }

    }
}
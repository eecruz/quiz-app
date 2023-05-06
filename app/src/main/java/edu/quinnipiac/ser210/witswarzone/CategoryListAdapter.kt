/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

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
var quiz_length: Int = 10

// recycle adapter for ListFragment
class CategoryListAdapter(var navController: NavController, username: String, length: Int)
    :RecyclerView.Adapter<CategoryListAdapter.MyViewHolder>()
{
    init {
        user_name = username
        quiz_length = length
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item,parent,false)
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
        // populates list with categories
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
                val action = ListFragmentDirections.actionListFragmentToGameFragment(categoryList[pos].apiInput, user_name, quiz_length)
                navController.navigate(action)

            }
        }
        fun bind(position:Int)
        {
            // sets UI properties of each category card view
            pos = position
            val category = categoryList[position]
            categoryLabel.text = category.display
            categoryImage.setImageResource(category.drawableID)
        }

    }
}
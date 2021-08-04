package com.ayushi.foodycookbook

import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter(val context: Context, val itemList: ArrayList<Meal>):
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>(), android.widget.ListAdapter {

    class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtViewStrMealIcon:TextView = view.findViewById(R.id.txtViewStrMealIcon)
        val imgStrMealThumbIcon:ImageView = view.findViewById(R.id.imgStrMealThumbIcon)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_custom_icon, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val meal = itemList[position]
        holder.txtViewStrMealIcon.text = meal.strMeal
        Picasso.with(context).load(meal.strMealThumb).into(holder.imgStrMealThumbIcon)


        holder.llContent.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("idMeal", meal.idMeal)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun areAllItemsEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(p0: Int): Boolean {
        TODO("Not yet implemented")
    }

}
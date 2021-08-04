package com.ayushi.foodycookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ayushi.foodycookbook.RecyclerAdapter

class FavouriteActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: RecyclerAdapter
    val prefConfig:PrefConfig = object :PrefConfig(){}

    lateinit var favInfoList: ArrayList<Meal>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.title = "My Favorite list"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)

        favInfoList = prefConfig.readListFromPref(this)

        val intent = intent
        val idMeal = intent.getStringExtra("idMeal")

        val s = intent.getStringExtra("type")
        if(s=="add" || s=="remove"){
            //fetch data from id
            val queue = Volley.newRequestQueue(this)
            val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=${idMeal}"
            val json_Object_Request = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                val meals = it.getJSONArray("meals")
                val mealJSONObject = meals.getJSONObject(0)
                val mealObj = Meal(mealJSONObject.getString("strMeal"),
                    mealJSONObject.getString("strMealThumb"),
                    mealJSONObject.getString("idMeal")
                )
                if(s=="add"){
                    favInfoList.add(mealObj)
                }else{
                    favInfoList.remove(mealObj)
                }
                //saving favInfoList in sharedpreferences
                prefConfig.writeListInPref(getApplicationContext(), favInfoList)

                recyclerAdapter = RecyclerAdapter(this, favInfoList)
                recyclerView.adapter = recyclerAdapter
                recyclerView.layoutManager = layoutManager
                recyclerView.addItemDecoration(
                    DividerItemDecoration(recyclerView.context, (layoutManager as LinearLayoutManager).orientation)
                )
            }, Response.ErrorListener {
                Toast.makeText(this,"Some error occurred", Toast.LENGTH_SHORT).show()
            }){

            }
            queue.add(json_Object_Request)

        }
        else{
            //to view the favList
            recyclerAdapter = RecyclerAdapter(this, favInfoList)
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = layoutManager
            recyclerView.addItemDecoration(
                DividerItemDecoration(recyclerView.context, (layoutManager as LinearLayoutManager).orientation)
            )
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
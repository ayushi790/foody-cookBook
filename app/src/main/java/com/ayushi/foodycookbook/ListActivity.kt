package com.ayushi.foodycookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.lang.reflect.InvocationTargetException

class ListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: RecyclerAdapter

    var mealInfoList = arrayListOf<Meal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val intent = intent
        val strMeal = intent.getStringExtra("strMeal").toString()

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)

        supportActionBar?.title = "Search results for '".plus(strMeal).plus("'")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=${strMeal}"
        val json_object_request = object: JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {

                if(it.getString("meals")!="null"){
                    val meals = it.getJSONArray("meals")
                    for(i in 0 until meals.length()) {
                        val mealJSONObject = meals.getJSONObject(i)
                        val mealObj = Meal(mealJSONObject.getString("strMeal"),
                            mealJSONObject.getString("strMealThumb"),
                            mealJSONObject.getString("idMeal")
                        )
                        mealInfoList.add(mealObj)
                        recyclerAdapter = RecyclerAdapter(this, mealInfoList)
                        recyclerView.adapter = recyclerAdapter
                        recyclerView.layoutManager = layoutManager
                        recyclerView.addItemDecoration(
                            DividerItemDecoration(recyclerView.context, (layoutManager as LinearLayoutManager).orientation)
                        )
                    }
                }else{
                    recyclerAdapter = RecyclerAdapter(this, mealInfoList)
                    recyclerView.adapter = recyclerAdapter
                    recyclerView.layoutManager = layoutManager
                    recyclerView.addItemDecoration(
                        DividerItemDecoration(recyclerView.context, (layoutManager as LinearLayoutManager).orientation)
                    )
                    Toast.makeText(this, "Sorry! no meal by this name", Toast.LENGTH_SHORT).show()
                } }, Response.ErrorListener {
                Toast.makeText(this,"Some error occurred", Toast.LENGTH_SHORT).show()
            }){

            }
                queue.add(json_object_request)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

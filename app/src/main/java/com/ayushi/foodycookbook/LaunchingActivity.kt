package com.ayushi.foodycookbook

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.leo.simplearcloader.ArcConfiguration
import com.leo.simplearcloader.SimpleArcDialog
import com.squareup.picasso.Picasso
import com.android.volley.toolbox.Volley.newRequestQueue as newRequestQueue1

class LaunchingActivity : AppCompatActivity() {
    lateinit var imgStrMealThumb:ImageView
    lateinit var txtStrMeal:TextView
    lateinit var favBtn: ImageView
    lateinit var vidBtn:ImageView
    lateinit var strSource:ImageView
    lateinit var btnIngredients:Button
    lateinit var btnInstructions:Button
    lateinit var txtInstructions:TextView
    lateinit var txtIngredients:TextView

    var isFavorite:Boolean = false
    lateinit var idMeal:String
    lateinit var vidUrl:String
    lateinit var strSourceUrl:String
    lateinit var mDialog: SimpleArcDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //loading the screen
        findViewById<RelativeLayout>(R.id.screen).visibility = View.GONE
        mDialog = SimpleArcDialog(this)
        mDialog.setConfiguration(ArcConfiguration(this))
        mDialog.show()

        imgStrMealThumb = findViewById(R.id.imgStrMealThumb)
        txtStrMeal = findViewById(R.id.txtStrMeal)
        favBtn = findViewById(R.id.favBtn)
        vidBtn = findViewById(R.id.vidBtn)
        strSource = findViewById(R.id.strSource)
        btnIngredients = findViewById(R.id.btnIngredients)
        btnInstructions= findViewById(R.id.btnInstructions)
        txtIngredients= findViewById(R.id.txtIngredients)
        txtInstructions = findViewById(R.id.txtInstructions)


        val queue = newRequestQueue1(this)
        val url:String = "https://www.themealdb.com/api/json/v1/1/random.php"
        val json_Object_Request = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

            val meals = it.getJSONArray("meals")
            for(i in 0 until meals.length()){
                val mealJSONObject = meals.getJSONObject(i)
                txtStrMeal.text = mealJSONObject.getString("strMeal")
                val imageUrl = mealJSONObject.getString("strMealThumb")
                vidUrl = mealJSONObject.getString("strYoutube")
                strSourceUrl = meals.getJSONObject(0).getString("strSource")
                if(strSourceUrl==""){
                    strSource.visibility = View.GONE
                }

                idMeal = mealJSONObject.getString("idMeal")
                Picasso.with(this).load(imageUrl).into(imgStrMealThumb)
                txtInstructions.text = meals.getJSONObject(0).getString("strInstructions")
                val ingList = arrayListOf<String>(meals.getJSONObject(0).getString("strIngredient1"),
                    meals.getJSONObject(0).getString("strIngredient2"),
                    meals.getJSONObject(0).getString("strIngredient3"),
                    meals.getJSONObject(0).getString("strIngredient4"),
                    meals.getJSONObject(0).getString("strIngredient5"),
                    meals.getJSONObject(0).getString("strIngredient6"),
                    meals.getJSONObject(0).getString("strIngredient7"),
                    meals.getJSONObject(0).getString("strIngredient8"),
                    meals.getJSONObject(0).getString("strIngredient9"),
                    meals.getJSONObject(0).getString("strIngredient10"),
                    meals.getJSONObject(0).getString("strIngredient11"),
                    meals.getJSONObject(0).getString("strIngredient12"),
                    meals.getJSONObject(0).getString("strIngredient13"),
                    meals.getJSONObject(0).getString("strIngredient14"),
                    meals.getJSONObject(0).getString("strIngredient15"),
                    meals.getJSONObject(0).getString("strIngredient16"),
                    meals.getJSONObject(0).getString("strIngredient17"),
                    meals.getJSONObject(0).getString("strIngredient18"),
                    meals.getJSONObject(0).getString("strIngredient19"),
                    meals.getJSONObject(0).getString("strIngredient20"))
                val measureList = arrayListOf<String>(meals.getJSONObject(0).getString("strMeasure1"),
                    meals.getJSONObject(0).getString("strMeasure2"),
                    meals.getJSONObject(0).getString("strMeasure3"),
                    meals.getJSONObject(0).getString("strMeasure4"),
                    meals.getJSONObject(0).getString("strMeasure5"),
                    meals.getJSONObject(0).getString("strMeasure6"),
                    meals.getJSONObject(0).getString("strMeasure7"),
                    meals.getJSONObject(0).getString("strMeasure8"),
                    meals.getJSONObject(0).getString("strMeasure9"),
                    meals.getJSONObject(0).getString("strMeasure10"),
                    meals.getJSONObject(0).getString("strMeasure11"),
                    meals.getJSONObject(0).getString("strMeasure12"),
                    meals.getJSONObject(0).getString("strMeasure13"),
                    meals.getJSONObject(0).getString("strMeasure14"),
                    meals.getJSONObject(0).getString("strMeasure15"),
                    meals.getJSONObject(0).getString("strMeasure16"),
                    meals.getJSONObject(0).getString("strMeasure17"),
                    meals.getJSONObject(0).getString("strMeasure18"),
                    meals.getJSONObject(0).getString("strMeasure19"),
                    meals.getJSONObject(0).getString("strMeasure20"))
                var txtIng = ""
                for(i in 0 until 20){
                    if(ingList[i]!=""){
                        if(ingList[i]!="null"){
                            txtIng = txtIng.plus(ingList[i]).plus(" - ").plus(measureList[i]).plus(" \n")
                        }else{
                            break
                        }
                    }else{
                        break
                    }
                }
                txtIngredients.text = txtIng

                val prefConfig:PrefConfig = object :PrefConfig(){}
                val favInfoList = prefConfig.readListFromPref(this)
                if(favInfoList.contains(Meal(txtStrMeal.text.toString(), imageUrl,idMeal))){
                    favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                    isFavorite = true
                }else{
                    isFavorite = false
                    favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
            findViewById<RelativeLayout>(R.id.screen).visibility = View.VISIBLE
            mDialog.hide()

        }, Response.ErrorListener {
            Toast.makeText(this,"Failed to load! Check your internet connection", Toast.LENGTH_SHORT).show()
        }){

        }
        queue.add(json_Object_Request)

        favBtn.setOnClickListener {
            if(isFavorite==true){
                //already favorite
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                isFavorite = false
                Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_LONG).show()
                redirectToFavSection(idMeal, "remove")
            }else{
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                isFavorite = true
                Toast.makeText(this, "Added to favourites", Toast.LENGTH_LONG).show()
                redirectToFavSection(idMeal, "add")
            }
        }
        vidBtn.setOnClickListener {
            if(vidUrl != ""){
                val intent = Intent("android.intent.action.VIEW",
                    Uri.parse(vidUrl))
                startActivity(intent)
            }else{
                Toast.makeText(this, "Sorry no video yet", Toast.LENGTH_SHORT).show()
            }
        }
        btnIngredients.setOnClickListener {
            if(txtIngredients.visibility == View.VISIBLE){
                txtIngredients.visibility = View.GONE
            }else{
                txtIngredients.visibility = View.VISIBLE
            }
        }
        btnInstructions.setOnClickListener {
            if(txtInstructions.visibility == View.VISIBLE){
                txtInstructions.visibility = View.GONE
            }else{
                txtInstructions.visibility = View.VISIBLE
            }
        }
        strSource.setOnClickListener {
            val intent = Intent("android.intent.action.VIEW",
                Uri.parse(strSourceUrl))
            startActivity(intent)
        }
    }

    private fun redirectToFavSection(idMeal: String, type_intent: String) {
        val intent = Intent(this@LaunchingActivity, FavouriteActivity::class.java)
        intent.putExtra("idMeal",idMeal)
        intent.putExtra("type",type_intent)
        val someThread = Runnable {
            startActivity(intent)
        }
        Handler().postDelayed(someThread, 800)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                val intent = Intent(this@LaunchingActivity, ListActivity::class.java)
                p0?.replace("\\s+".toRegex(),"_")
                intent.putExtra("strMeal", p0)
                startActivity(intent)
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.app_bar_fav -> {
                val intent = Intent(this@LaunchingActivity, FavouriteActivity::class.java)
                intent.putExtra("type", "view")
                intent.putExtra("idMeal","null")
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        mDialog.dismiss()
        super.onPause()
    }
}


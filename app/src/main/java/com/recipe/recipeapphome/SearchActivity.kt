package com.recipe.recipeapphome

import android.annotation.SuppressLint
import android.content.Intent
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.recipe.recipeapphome.databinding.ActivityHomeBinding
import com.recipe.recipeapphome.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recipes:List<Recipe?>
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>

    @SuppressLint("ServiceCast", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchtext.requestFocus()
        var db= Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject =db.getDao()
        recipes = daoObject.getAll()

        binding.searchtext.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString()!=""){
                    filterData(s.toString())
                }
                else{
                    setupRecyclerView()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

     //   var imm=getSystemService(INPUT_METHOD_SERVICE) as InputMethodService

     //   binding.rvSearch.setOnTouchListener{ v,event ->
     //       imm.hideWindow()
     //   }

        setupRecyclerView()

        binding.backtoHome.setOnClickListener{
            finish()
        }
    }

    private fun filterData(filterText: String) {

        var filterData=ArrayList<Recipe>()
        for(i in recipes.indices){
            if(recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                filterData.add(recipes[i]!!)
            }
            rvAdapter.filterList(filterList = filterData)
        }
    }

    private fun setupRecyclerView() {
        dataList = ArrayList()
        binding.rvSearch.layoutManager =
            LinearLayoutManager(this)

        for (i in recipes!!.indices) {
            if (recipes[i]?.category?.contains("Popular") == true) {
                dataList.add(recipes[i]!!)
            }
        }

        rvAdapter = SearchAdapter(dataList, this)
        binding.rvSearch.adapter = rvAdapter
    }
}
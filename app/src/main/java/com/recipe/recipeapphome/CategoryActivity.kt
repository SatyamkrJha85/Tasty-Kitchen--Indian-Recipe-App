package com.recipe.recipeapphome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.recipe.recipeapphome.databinding.ActivityCategoryBinding // Import your View Binding class
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room

class CategoryActivity : AppCompatActivity() {

    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var binding: ActivityCategoryBinding // Initialize your View Binding variable here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding variable before setting the content view
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val intent: Intent = intent
        var data = intent.getStringExtra("TITTLE")
        binding.title.text=data

        binding.backHome.setOnClickListener{
            finish()
        }

    }

    private fun setupRecyclerView() {
        dataList = ArrayList()
        binding.rvCategory1.layoutManager =
            LinearLayoutManager(this)

        var db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]?.category?.contains(intent.getStringExtra("CATEGORY")!!) == true) {
                dataList.add(recipes[i]!!)
            }
        }

        rvAdapter = CategoryAdapter(dataList, this)
        binding.rvCategory1.adapter = rvAdapter
    }
}

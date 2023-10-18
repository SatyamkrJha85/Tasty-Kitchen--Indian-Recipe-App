package com.recipe.recipeapphome

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.recipe.recipeapphome.databinding.ActivityHomeBinding // Import your View Binding class
import com.recipe.recipeapphome.databinding.PopularRvItemBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding // Declare your View Binding object
    private lateinit var rvAdapter: popularAdapter
    private lateinit var dataList: ArrayList<Recipe>

    lateinit var mAdView : AdView
    private var mInterstitialAd: InterstitialAd? = null


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize your View Binding object
        binding = ActivityHomeBinding.inflate(layoutInflater)

        // Set the content view using binding.root
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
       // setContentView(binding1.root)


        setupRecyclerView()

//        binding.menuicon.setOnClickListener{
//            var dialog=Dialog(this)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setContentView(R.layout.activity_bottom_sheet)
//            dialog.show()
//            dialog.window!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.window!!.setGravity(Gravity.BOTTOM)
//        }

        ad()

        binding.search.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))
        }

        binding.salad.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }

        }
        binding.maindish.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }
        binding.drinks.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }
        binding.desserts.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent)

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }


    }

    private fun setupRecyclerView() {
        dataList = ArrayList()


        binding.recyclerviewPopular.layoutManager =
          LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

      //  val sab = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) // Replace '2' with the desired number of columns
        //binding.recyclerviewPopular.layoutManager = sab

        var db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]?.category?.contains("Popular") == true) {
                dataList.add(recipes[i]!!)
            }
        }

        rvAdapter = popularAdapter(dataList, this)
        binding.recyclerviewPopular.adapter = rvAdapter
    }
    public fun ad(){
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3388221262538162/6221190817", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d(TAG, it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }
}

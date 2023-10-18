package com.recipe.recipeapphome

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.recipe.recipeapphome.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    var imgcrop=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.recipeImg)
        binding.title.text=intent.getStringExtra("tittle")
        binding.stepData.text=intent.getStringExtra("des")

        var ing=intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        binding.time.text=ing?.get(0)
        for(i in 1 until ing!!.size){
            binding.ingData.text=
                """${binding.ingData.text} 🟢 ${ing[i]}     
                """.trimIndent()
        }
        binding.stepBtn.background=null
        binding.stepBtn.setTextColor(getColor(R.color.black))


        binding.stepBtn.setOnClickListener{
            binding.stepBtn.setBackgroundColor(R.drawable.btn_ing)
            binding.stepBtn.setTextColor(getColor(R.color.black))
            binding.btnIngredi.setTextColor(getColor(R.color.black))
            binding.btnIngredi.background=null
            binding.stepScrool.visibility=View.VISIBLE
            binding.scroolIng.visibility=View.GONE
        }

        binding.btnIngredi.setOnClickListener{
            binding.btnIngredi.setBackgroundColor(R.drawable.btn_ing)
            binding.btnIngredi.setTextColor(getColor(R.color.black))
            binding.stepBtn.setTextColor(getColor(R.color.black))
            binding.stepBtn.background=null
            binding.scroolIng.visibility=View.VISIBLE
            binding.stepScrool.visibility=View.GONE
        }



        binding.fullScreen.setOnClickListener{
             if(imgcrop){
                 binding.recipeImg.scaleType=ImageView.ScaleType.FIT_CENTER
                 Glide.with(this).load(intent.getStringExtra("img")).into(binding.recipeImg)
                 binding.fullScreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                 binding.overlay.visibility= View.GONE
                 imgcrop=!imgcrop
             }
            else{
                binding.recipeImg.scaleType=ImageView.ScaleType.CENTER_CROP
                 Glide.with(this).load(intent.getStringExtra("img")).into(binding.recipeImg)
                 binding.fullScreen.setColorFilter(null )
                 binding.overlay.visibility= View.GONE
                 imgcrop=!imgcrop
             }
        }
        binding.backHome.setOnClickListener{
            finish()
        }
    }
}
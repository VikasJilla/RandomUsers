package com.example.randomusers

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.randomusers.data.entities.UserEntity
import com.example.randomusers.databinding.ActivityUserDetailsBinding
import com.example.randomusers.presentation.transformations.CircleTransformation
import com.squareup.picasso.Picasso

class UserDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userDetailsBinding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDetailsBinding = ActivityUserDetailsBinding.inflate(layoutInflater)
        val obj = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra("User",UserEntity::class.java)!!
            else @Suppress("DEPRECATION")  intent.getSerializableExtra("User") as UserEntity
        userDetailsBinding.user = obj
        val colorCode = if(obj.isMale())R.color.teal_200 else R.color.purple_200
        val color = ContextCompat.getColor(this,colorCode)
        Picasso.get()
            .load(obj.mediumImage)
            .error(AppCompatResources.getDrawable(this,R.drawable.baseline_person_24)!!)
            .transform(CircleTransformation(color,2.0f))
            .into(userDetailsBinding.profilePic)
        userDetailsBinding.headerColor = ColorDrawable(color)
        setContentView(userDetailsBinding.root)
        userDetailsBinding.iconColor = color
        supportActionBar?.hide()
        window.statusBarColor = color
        userDetailsBinding.clickListener = this
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
             android.R.id.home -> {
                 finish()
                 return true
             }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        @JvmStatic
        @BindingAdapter("app:tint")
        fun ImageView.setImageTint(@ColorInt color: Int) {
            setColorFilter(color)
        }
    }

    override fun onClick(v: View?) {
        finish()
    }
}
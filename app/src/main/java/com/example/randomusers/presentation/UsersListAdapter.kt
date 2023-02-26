package com.example.randomusers.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.randomusers.BR
import com.example.randomusers.R
import com.example.randomusers.data.entities.UserEntity
import com.example.randomusers.databinding.UserListItemBinding
import com.example.randomusers.presentation.clicklisteners.CustomClickListener
import com.example.randomusers.presentation.transformations.CircleTransformation
import com.squareup.picasso.Picasso


class UserViewHolder(
    private val binding: UserListItemBinding,
    itemView: View,
    private val drawable: Drawable,
) :
    ViewHolder(itemView) {
    fun bind(obj: UserEntity?,itemClickListener: CustomClickListener,color:Int) {
        binding.setVariable(BR.user, obj)
        binding.itemClickListener = itemClickListener
        binding.executePendingBindings()
        if(obj != null){
            Picasso.get()
                .load(obj.thumbnail)
                .error(drawable)
                .transform(CircleTransformation(color,2.0f))
                .into(binding.profileView)
        }
    }

}

class UsersListAdapter(private val users:MutableList<UserEntity?>,private val itemClickListener: CustomClickListener,private val context:Context) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = DataBindingUtil.inflate<UserListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item, parent, false)

        return UserViewHolder(binding,binding.root,AppCompatResources.getDrawable(context,R.drawable.ic_launcher_background)!!)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(if(position < users.size) users[position] else null,itemClickListener,context.resources.getColor(if(users[position]?.isMale() != false)R.color.teal_200 else R.color.purple_200))
    }

    fun addUsers(nUsers:MutableList<UserEntity?>){
        if(users.size > 0 && users.size%20 != 0){
            users.removeLast()
            notifyItemRemoved(users.size)
        }
        users.addAll(nUsers)
        if(users.size == nUsers.size){//when the list is loaded for the first time(i.e size is 0)
            notifyDataSetChanged()
        }else{//when new data is added to existing users
            notifyItemRangeInserted(users.size-nUsers.size, nUsers.size)
        }
    }
}
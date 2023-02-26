package com.example.randomusers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.randomusers.data.entities.UserEntity
import com.example.randomusers.databinding.ActivityMainBinding
import com.example.randomusers.presentation.UsersListAdapter
import com.example.randomusers.presentation.clicklisteners.CustomClickListener
import com.example.randomusers.presentation.states.LoadingMoreState
import com.example.randomusers.presentation.states.ProgressState
import com.example.randomusers.presentation.states.ResultUIState
import com.example.randomusers.presentation.states.UIState
import com.example.randomusers.presentation.viewmodels.UsersViewModel
import com.example.randomusers.presentation.viewmodels.UsersViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CustomClickListener{

    private lateinit var mainBinding:ActivityMainBinding
    private val viewModel:UsersViewModel by viewModels{
        UsersViewModelFactory((application as UsersApplication).usersRepository)
    }
    private var adapter:UsersListAdapter? = null
    private lateinit var layoutManager: StaggeredGridLayoutManager

    private val tag = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
        mainBinding.usersView.layoutManager = layoutManager
        adapter = adapter?: UsersListAdapter(mutableListOf(),this,this)
        mainBinding.usersView.adapter = adapter
        addScrollListener()
        lifecycleScope.launch{
            viewModel.uiState.collect{
                listenToStateChanges(it)
            }
        }
        supportActionBar?.title = resources.getString(R.string.users)
        viewModel.loadUsers()
    }

    private fun lastVisibleItemPosition(): Int {
        val positions = IntArray(2)
        layoutManager.findLastVisibleItemPositions(positions)
        return positions[1]
    }

    private fun addScrollListener(){
        val scrollListener = object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager?.itemCount?:0
                if(totalItemCount%20 == 0)return
                Log.d(tag,"in onScrollStateChanged $totalItemCount ${lastVisibleItemPosition()}")
                if(totalItemCount - 2  <= lastVisibleItemPosition() && viewModel.uiState.value !is LoadingMoreState){
                    Log.d(tag,"triggering loadMore")
                    viewModel.loadMoreUsers()
                }
            }
        }
        mainBinding.usersView.addOnScrollListener(scrollListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    @Suppress("UNCHECKED_CAST")
    private fun listenToStateChanges(state:UIState){
        Log.d(tag,"listenToStateChanges $state")
        val progressVisibility = if(state is ProgressState) View.VISIBLE else View.INVISIBLE
        val recyclerViewVisibility = if(state is LoadingMoreState || (state is ResultUIState && state.isSuccess)) View.VISIBLE else View.INVISIBLE
        mainBinding.progressCircular.visibility = progressVisibility
        mainBinding.usersView.visibility = recyclerViewVisibility
        if(state is ResultUIState && state.isSuccess && state.data is MutableList<*>){
            adapter!!.addUsers(state.data as MutableList<UserEntity?>)
        }
    }

    override fun itemClicked(user: UserEntity?) {
        if(user == null)return
        val intent = Intent(this,UserDetailsActivity::class.java)
        intent.putExtra("User",user)
        startActivity(intent)
    }
}


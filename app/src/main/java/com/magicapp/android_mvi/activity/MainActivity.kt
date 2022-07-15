package com.magicapp.android_mvi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magicapp.android_mvi.R
import com.magicapp.android_mvi.activity.main.helper.MainHelperImpl
import com.magicapp.android_mvi.activity.main.intentstate.MainIntent
import com.magicapp.android_mvi.activity.main.intentstate.MainState
import com.magicapp.android_mvi.activity.main.viewmodel.MainViewModel
import com.magicapp.android_mvi.activity.main.viewmodel.MainViewModelFactory
import com.magicapp.android_mvi.adapters.PostAdapter
import com.magicapp.android_mvi.models.Post
import com.magicapp.android_mvi.network.RetrofitBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Init -> {
                        Log.d("@@@", "Init")
                    }
                    is MainState.Loading -> {
                        Log.d("@@@", "Loading")
                    }
                    is MainState.AllPosts -> {
                        Log.d("@@@", "PostList")
                        refreshAdapter(it.posts)
                    }
                    is MainState.DeletePost -> {
                        Log.d("@@@", "DeletePost "+it.post)
                        intentAllPosts()
                    }
                    is MainState.Error -> {
                        Log.d("@@@", "Error $it")
                    }
                }
            }
        }
    }

    private fun initViews() {
        val factory = MainViewModelFactory(MainHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        intentAllPosts()
    }

    private fun refreshAdapter(posters: ArrayList<Post>) {
        val adapter = PostAdapter(this, posters)
        recyclerView.adapter = adapter
    }

    private fun intentAllPosts() {
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntent.AllPosts)
        }
    }

    fun intentDeletePost(id: Int) {
        lifecycleScope.launch {
            viewModel.postId = id
            viewModel.mainIntent.send(MainIntent.DeletePost)
        }
    }

}
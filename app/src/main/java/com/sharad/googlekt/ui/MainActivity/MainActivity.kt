package com.sharad.googlekt.ui.MainActivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.MergeAdapter
import com.google.android.material.snackbar.Snackbar
import com.sharad.googlekt.databinding.ActivityMainBinding
import com.sharad.googlekt.ui.TotalAdapter.TotalAdapter
import com.sharad.googlekt.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    private val mTotalAdapter = TotalAdapter()
    private val mStateAdapter = ItemAdapter()
    private val adapter = MergeAdapter(mTotalAdapter, mStateAdapter)

    // Useful when back navigation is pressed.
    private var backPressedTime = 0L
    private val backSnackbar by lazy {
        Snackbar.make(binding.root, BACK_PRESSED_MESSAGE, Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initData()
    }

    private fun initViews() {
        binding.recycler.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
    }


    private fun initData() {
        viewModel.covidLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false

                    val list = state.data.stateWiseDetails
                    mTotalAdapter.submitList(list.subList(0, 1))
                    mStateAdapter.submitList(list.subList(1, list.size - 1))

                }
            }
        })

        if (viewModel.covidLiveData.value !is State.Success) {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getData()
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backSnackbar.dismiss()
            super.onBackPressed()
            return
        } else {
            backSnackbar.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    companion object {
        const val JOB_TAG = "notificationWorkTag"
        const val BACK_PRESSED_MESSAGE = "Press back again to exit"
    }
}

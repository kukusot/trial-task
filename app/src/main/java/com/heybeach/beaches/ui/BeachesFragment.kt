package com.heybeach.beaches.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.heybeach.R
import com.heybeach.beaches.di.BeachesFragmentInjector
import com.heybeach.beaches.domain.data.NetworkState
import kotlinx.android.synthetic.main.fragment_beaches.*

class BeachesFragment : Fragment() {

    lateinit var beachesAdapter: BeachesAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var viewModel: BeachesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_beaches, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.imagesDataResult.observe(this, Observer {
            beachesAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            if (it == NetworkState.ERROR) {
                Snackbar.make(parentView, R.string.error_fetching_data, Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.try_again){
                        viewModel.retry()
                    }
                    show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
            }
            addItemDecoration(dividerItemDecoration)

            layoutManager = this@BeachesFragment.layoutManager
            adapter = beachesAdapter
        }
    }

    override fun onAttach(context: Context?) {
        BeachesFragmentInjector.inject(this)
        super.onAttach(context)
    }

}
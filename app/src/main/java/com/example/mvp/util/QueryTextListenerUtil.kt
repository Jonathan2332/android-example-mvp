package com.example.mvp.util

import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class QueryTextListenerUtil(
    lifecycle: Lifecycle, private val queryTextListenerUtil: (String?) -> Unit
) : SearchView.OnQueryTextListener, LifecycleObserver
{
    private val coroutine = lifecycle.coroutineScope
    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean
    {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean
    {
        searchJob?.cancel()
        searchJob = coroutine.launch {
            newText?.let {
                delay(500)
                queryTextListenerUtil(it)
            }
        }
        return false
    }

}
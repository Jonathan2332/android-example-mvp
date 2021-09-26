package com.example.mvp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class AbstractActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(getLayout().root)
        onInject()
    }

    protected abstract fun getLayout() : ViewBinding

    protected abstract fun onInject()

    protected abstract fun onUninject()

    override fun onDestroy()
    {
        super.onDestroy()
        onUninject()
    }
}
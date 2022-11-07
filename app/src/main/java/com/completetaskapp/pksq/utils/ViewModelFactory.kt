package com.completetaskapp.pksq.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.completetaskapp.pksq.data.repository.Repository
import com.completetaskapp.pksq.ui.StartViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(
                Repository(
                    context,
                    PrefsHelper(context = context)
                )
            ) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}


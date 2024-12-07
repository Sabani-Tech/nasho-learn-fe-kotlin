package com.learn.nasho.ui.viewmodels.material

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learn.nasho.data.repository.MaterialRepository
import com.learn.nasho.di.Injection

@Suppress("UNCHECKED_CAST")
class MaterialViewModelFactory(private var materialRepository: MaterialRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: MaterialViewModelFactory? = null

        fun getInstance(context: Context): MaterialViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MaterialViewModelFactory(Injection.provideMaterialRepository(context))
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoryListViewModel::class.java) ->
                CategoryListViewModel(materialRepository) as T

            modelClass.isAssignableFrom(MaterialListViewModel::class.java) ->
                MaterialListViewModel(materialRepository) as T

            modelClass.isAssignableFrom(CategoryDetailViewModel::class.java) ->
                CategoryDetailViewModel(materialRepository) as T

            else -> throw IllegalArgumentException("unknown viewmodel class: ${modelClass.name}")
        }
    }
}
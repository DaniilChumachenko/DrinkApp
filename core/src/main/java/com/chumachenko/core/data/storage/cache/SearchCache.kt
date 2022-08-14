package com.chumachenko.core.data.storage.cache

import android.util.LruCache
import com.chumachenko.core.data.model.DrinksList

class SearchCache : LruCache<String, DrinksList>(CACHE_SIZE) {
    companion object {
        private const val CACHE_SIZE = 15
    }
}
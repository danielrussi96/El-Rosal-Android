package com.app.elrosal.ui.products.paginate

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.data.EnvironmentConfig
import com.app.domain.products.Product
import com.app.usecases.products.GetProductsUseCase

class ProductsPagingSource(
    private val id: String,
    private val getProductsUseCase: GetProductsUseCase
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> =
        try {
            val page = params.key ?: 0
            val data = getProductsUseCase.invoke(
                environmentConfig = EnvironmentConfig.PRODUCTION,
                id = id,
                paginate = page,
            )
            LoadResult.Page(
                data = data.listProducts,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (page == data.totalPages) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}
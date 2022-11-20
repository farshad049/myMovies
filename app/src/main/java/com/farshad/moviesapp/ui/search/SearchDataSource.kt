package com.farshad.moviesapp.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.mapper.MovieMapper
import com.farshad.moviesapp.data.model.network.PagingModel
import com.farshad.moviesapp.data.network.ApiClient
import com.farshad.moviesapp.data.network.SimpleResponse


class SearchDataSource (
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
    private val userSearch:String,
    private val localExceptionCallBack: (LocalException)->Unit
)  :PagingSource<Int,DomainMovieModel>() {

    sealed class LocalException(val title:String, val description:String=""):Exception(){
        object EmptySearch : LocalException(title = "Start Typing To Search")
        object NoResult: LocalException(title = "whoops", description = "looks like your search didn't return any result")
    }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainMovieModel> {

        if (userSearch.isEmpty()) {
            val exception = LocalException.EmptySearch
            localExceptionCallBack(exception)
            return LoadResult.Error(exception)
        }




        val pageNumber = params.key ?: 1
        val previewPage= if (pageNumber ==1 ) null else pageNumber -1

        val request=apiClient.getMoviesPagingByName(userSearch,pageNumber)



        if (request.bodyNullable?.metadata?.total_count == 0) {
            val exception = LocalException.NoResult
            localExceptionCallBack(exception)
            return LoadResult.Error(exception)
        }



        //when !pageRequest.isSuccessful do this
        request.exception?.let {
            return LoadResult.Error(it)
        }


        return LoadResult.Page(
            //we map it because the parent function has to return Character
            data = request.bodyNullable?.data?.map { movieMapper.buildFrom(it) } ?: emptyList(),
            prevKey = previewPage,
            nextKey = calculateNextPage(request)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, DomainMovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }



    private fun calculateNextPage(request : SimpleResponse<PagingModel>):Int?{
        var nextPage : Int? = null

        if (request.bodyNullable != null){
            val totalPage = request.bodyNullable!!.metadata.page_count
            val currentPage = request.bodyNullable!!.metadata.current_page.toInt()
            if (currentPage < totalPage){
                nextPage = currentPage.plus(1)
            }
        }

        return nextPage
    }


}
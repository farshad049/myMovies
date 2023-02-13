package com.farshad.moviesapp.ui.movieList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.mapper.MovieMapper
import com.farshad.moviesapp.data.model.network.PagingModel
import com.farshad.moviesapp.data.remote.ApiClient
import com.farshad.moviesapp.data.remote.SimpleResponse
import javax.inject.Inject

class MovieDataSource@Inject constructor(private val apiClient: ApiClient, private val movieMapper: MovieMapper)
    : PagingSource<Int, DomainMovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainMovieModel> {
        val pageNumber = params.key ?: 1
        val previewPage= if (pageNumber ==1 ) null else pageNumber - 1


        val request=apiClient.getMoviesPaging(pageNumber)

        //when !pageRequest.isSuccessful do this
        request.exception?.let {
            return LoadResult.Error(it)
        }


        return LoadResult.Page(
            //we map it because the parent function has to return DomainMovie
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
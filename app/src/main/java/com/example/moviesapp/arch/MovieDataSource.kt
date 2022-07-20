package com.example.moviesapp.arch

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.network.ApiClient
import javax.inject.Inject

class MovieDataSource@Inject constructor(private val apiClient: ApiClient, private val movieMapper: MovieMapper)
    : PagingSource<Int, DomainMovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainMovieModel> {
        val pageNumber = params.key ?: 1
        val previewPage= if (pageNumber ==1 ) null else pageNumber -1
        val nextPage= if (pageNumber < 25) pageNumber+1 else null

        val pageRequest=apiClient.getCharactersPage(pageNumber)

        //when !pageRequest.isSuccessful do this
        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }


        return LoadResult.Page(
            //we map it because the parent function has to return Character
            data = pageRequest.bodyNullable?.data?.map { movieMapper.buildFrom(it) } ?: emptyList(),
            prevKey = previewPage,
            nextKey = nextPage
        )
    }

    override fun getRefreshKey(state: PagingState<Int, DomainMovieModel>): Int? {
        TODO("Not yet implemented")
    }


}
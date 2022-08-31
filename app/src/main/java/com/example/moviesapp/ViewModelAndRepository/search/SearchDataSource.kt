package com.example.moviesapp.ViewModelAndRepository.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.network.ApiClient
import javax.inject.Inject


class SearchDataSource (
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
    private val userSearch:String,
   // private val localExceptionCallBack: (LocalException)->Unit
)  :PagingSource<Int,DomainMovieModel>() {

//    sealed class LocalException(val title:String, val description:String=""):Exception(){
//        object EmptySearch :LocalException(title = "Start Typing To Search")
//        object NoResult:LocalException(title = "whoops", description = "looks like your search didn't return any result")
//    }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainMovieModel> {

//        if (userSearch.isEmpty()) {
//            val exception = LocalException.EmptySearch
//            localExceptionCallBack(exception)
//            return LoadResult.Error(exception)
//        }




        val pageNumber = params.key ?: 1
        val previewPage= if (pageNumber ==1 ) null else pageNumber -1

        val request=apiClient.getMoviesPagingByName(userSearch,pageNumber)



//        if (request.bodyNullable?.data?.isEmpty() == true) {
//            val exception = LocalException.NoResult
//            localExceptionCallBack(exception)
//            return LoadResult.Error(exception)
//        }



        //when !pageRequest.isSuccessful do this
        request.exception?.let {
            return LoadResult.Error(it)
        }


        return LoadResult.Page(
            //we map it because the parent function has to return Character
            data = request.bodyNullable?.data?.map { movieMapper.buildFrom(it) } ?: emptyList(),
            prevKey = previewPage,
            nextKey = request.bodyNullable?.metadata?.current_page?.toInt()?.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, DomainMovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
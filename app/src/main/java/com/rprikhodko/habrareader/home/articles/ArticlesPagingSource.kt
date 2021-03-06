package com.rprikhodko.habrareader.home.articles

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rprikhodko.habrareader.home.articles.domain.PostsRepository
import com.rprihodko.habrareader.common.dto.PostPreview
import com.rprihodko.habrareader.common.network.Period
import com.rprihodko.habrareader.common.network.Rating
import com.rprihodko.habrareader.common.network.SortBy
import retrofit2.HttpException
import java.lang.Exception

class ArticlesPagingSource constructor(
    val repository: PostsRepository,
    var repoParams: RepositoryParams
) : PagingSource<Int, PostPreview>() {

    // load params
    override fun getRefreshKey(state: PagingState<Int, PostPreview>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostPreview> {
        try {
            val pageNumber = params.key ?: 1

            val response = when(repoParams.sortBy) {
                SortBy.Rating -> repository.getLatestArticles(pageNumber, repoParams.rating)
                SortBy.Period -> repository.getBestArticles(pageNumber, repoParams.period)
            }

            if (response.isSuccessful) {
                val articles = response.body()!!.articleRefs
                val pagesCount = response.body()!!.pagesCount
                val nextPageNumber = if (articles.isEmpty() || pagesCount == 1 || pageNumber == pagesCount) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                val filteredResult = articles.values.toList().filter{ it.postType == "article" }
                val sortedResult = when(repoParams.sortBy) {
                    SortBy.Rating -> filteredResult.sortedByDescending{ it.timePublished }
                    SortBy.Period -> filteredResult.sortedByDescending{ it.stats.score }
                }
                return LoadResult.Page(sortedResult, prevPageNumber, nextPageNumber)
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    data class RepositoryParams(
        var sortBy: SortBy = SortBy.Rating,
        var period: Period = Period.Daily,
        var rating: Rating = Rating.AnyRating
    )
}

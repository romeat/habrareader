package com.rprikhodko.habrareader

import com.rprikhodko.habrareader.data.network.Period
import com.rprikhodko.habrareader.data.network.Rating
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepository @Inject constructor(
    private val remoteData : HabrRemoteData
) {

    suspend fun getArticles(page : Int) = remoteData.getNewArticles(page)

    //suspend fun getNews(page: Int) = remoteData.getNews(page)

    suspend fun getLatestArticles(page: Int, rating: Rating) = remoteData.getArticlesWithRating(page, rating)

    suspend fun getBestArticles(page: Int, period: Period) = remoteData.getBestArticlesOfPeriod(page, period)
}
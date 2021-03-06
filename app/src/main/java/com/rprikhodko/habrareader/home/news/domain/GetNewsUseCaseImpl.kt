package com.rprikhodko.habrareader.home.news.domain

import com.rprihodko.habrareader.common.dto.PostsPage
import com.rprihodko.habrareader.common.network.HabrRemoteData
import retrofit2.Response
import javax.inject.Inject

class GetNewsUseCaseImpl @Inject constructor(
    private val remoteData : HabrRemoteData
) : GetNewsUseCase {
    override suspend fun invoke(page: Int): Response<PostsPage> = remoteData.getNews(page)
}
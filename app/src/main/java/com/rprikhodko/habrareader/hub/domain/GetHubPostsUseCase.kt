package com.rprikhodko.habrareader.hub.domain

import com.rprihodko.habrareader.common.dto.PostsPage
import com.rprikhodko.habrareader.common.interfaces.GetPostsByCategoryUseCase
import com.rprihodko.habrareader.common.network.HabrRemoteData
import retrofit2.Response
import javax.inject.Inject

class GetHubPostsUseCase @Inject constructor(
    private val remoteData : HabrRemoteData
) : GetPostsByCategoryUseCase {
    override suspend fun invoke(page: Int, alias: String): Response<PostsPage> = remoteData.getArticlesOfHub(page, alias)
}
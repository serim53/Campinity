package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.data.remote.datasource.search.SearchReviewRequest
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.search.CampsiteDetailInfo
import com.ssafy.campinity.domain.entity.search.CampsiteNoteBriefInfo
import com.ssafy.campinity.domain.entity.search.Review

interface SearchRepository {

    suspend fun getCampsitesByFiltering(filter: SearchFilterRequest): Resource<List<CampsiteBriefInfo>>

    suspend fun getCampsitesByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): Resource<List<CampsiteBriefInfo>>

    suspend fun getCampsiteDetail(campsiteId: String): Resource<CampsiteDetailInfo>

    suspend fun getCampsiteReviewNotes(
        campsiteId: String,
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double,
    ): Resource<List<CampsiteNoteBriefInfo>>

    suspend fun writeReview(review: SearchReviewRequest): Resource<Review>

    suspend fun deleteReview(reviewId: String): Resource<String>

    suspend fun scrapCampsite(campsiteId: String): Resource<Boolean>
}
package com.ssafy.campinity.domain.entity.search

data class CampsiteBriefInfo(
    val campsiteId: String,
    val campsiteName: String,
    val area: String,
    val noteCount: Int,
    val images: List<String>,
    val isScraped: Boolean,
    val lat: Double,
    val lng: Double
)
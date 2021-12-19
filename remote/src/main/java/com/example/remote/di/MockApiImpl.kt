package com.example.remote.di

import com.example.remote.api.MockApi
import com.example.remote.mapper.requestToResponse
import com.example.remote.model.mock.RemoteMockItem
import com.example.remote.model.mock.RemoteMockRequest
import com.example.remote.model.mock.RemoteMockResponse
import io.reactivex.Single

class MockApiImpl : MockApi {

    override fun getAllLabel(requestBody: RemoteMockRequest): Single<RemoteMockResponse> {
        return Single.just(requestBody)
            .map { it.requestToResponse() }
    }

    override fun getHistory(year: Int, month: Int): Single<List<RemoteMockResponse>> {
        return Single.just(
            listOf(
                RemoteMockResponse(
                    labelA = RemoteMockItem(
                        labelType = "a",
                        latitude = 37.48857498168945,
                        longitude = 127.01065063476562,
                        aqi = 263,
                        name = "서초동, 서초3동"
                    ),
                    labelB = RemoteMockItem(
                        labelType = "b",
                        latitude = 37.5052375793457,
                        longitude = 127.02490234375,
                        aqi = 177,
                        name = "논현동, 논현1동"
                    ),
                    price = 183.186
                ),
                RemoteMockResponse(
                    labelA = RemoteMockItem(
                        labelType = "a",
                        latitude = 37.5105094909668,
                        longitude = 127.05546569824219,
                        aqi = 105,
                        name = "삼성동, 삼성1동"
                    ),
                    labelB = RemoteMockItem(
                        labelType = "b",
                        latitude = 37.506778717041016,
                        longitude = 127.03312683105469,
                        aqi = 74,
                        name = "Yeoksam-dong, 역삼 1동"
                    ),
                    price = 394.459
                ),
            )
        )
    }
}
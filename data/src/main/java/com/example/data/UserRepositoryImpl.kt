package com.example.data

import com.example.data.local.UserLocalDataSource
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userLocalDataSource: UserLocalDataSource) : UserRepository {

    override var language: String
        get() = userLocalDataSource.language
        set(value) {
            userLocalDataSource.language = value
        }

}
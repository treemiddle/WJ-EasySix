package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import java.util.*
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun getLanguage(): String {
        return userRepository.language
    }

    fun setLanguage(language: String) {
        userRepository.language = language
    }

    fun getCurrentLanguage(): String {
        return Locale.getDefault().language
    }

}
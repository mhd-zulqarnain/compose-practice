package com.project.tailor.data.signup

import com.project.tailor.api.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpRepository @Inject constructor(
    private val dataSource: SignUpDataSource
) {

     fun signUp(name: String,email: String, password: String): Flow<Result<String>> = flow {
        try {
            when (val result = dataSource.signUp(name,email, password)) {
                is Result.Success -> {
                    val data = result.data.message
                    emit(Result.Success(data.orEmpty()))
                }
                is Result.Error -> {
                    val data = result.exception
                    emit(Result.Error(data))
                }
                else -> {
                    //not implemented
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }


}
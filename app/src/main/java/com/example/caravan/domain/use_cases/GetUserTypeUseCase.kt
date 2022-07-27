package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.BuyersList
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.UserType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserTypeUseCase @Inject constructor(
    private val repository: CaravanRepository
) {
    operator fun invoke(id: Id): Flow<Result<UserType>>  = flow {

        try {
            emit(Result.Loading<UserType>())
            val usertype = repository.getUserType(id)
            emit(Result.Success<UserType>(usertype))
        }catch (e: HttpException){
            emit(Result.Error<UserType>(e.localizedMessage ?: "you dont have netin happend"))
        }catch (e: IOException){
            emit(Result.Error<UserType>("cant get to server: "+e.message.toString()))
        }

    }
}
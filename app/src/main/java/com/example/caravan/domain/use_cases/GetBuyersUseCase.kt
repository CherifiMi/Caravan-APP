package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.BuyersList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBuyersUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    operator fun invoke(): Flow<Result<BuyersList>> = flow {
        try {
            emit(Result.Loading<BuyersList>())
            val buyersList = repository.getBuyers()
            emit(Result.Success<BuyersList>(buyersList))
        }catch (e: HttpException){
            emit(Result.Error<BuyersList>(e.localizedMessage ?: "you dont have netin happend"))
        }catch (e: IOException){
            emit(Result.Error<BuyersList>("cant get to server"))
        }
    }
}
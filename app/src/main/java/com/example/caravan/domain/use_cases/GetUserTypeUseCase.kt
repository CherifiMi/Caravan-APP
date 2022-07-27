package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.BuyersList
import com.example.caravan.domain.model.UserType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserTypeUseCase @Inject constructor(
    private val repository: CaravanRepository
) {
    suspend operator fun invoke(): UserType = repository.getUserType()
}
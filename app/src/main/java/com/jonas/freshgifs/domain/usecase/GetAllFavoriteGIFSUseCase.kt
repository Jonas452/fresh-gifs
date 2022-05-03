package com.jonas.freshgifs.domain.usecase

import com.jonas.freshgifs.data.repository.FreshGIFSRepository
import com.jonas.freshgifs.domain.model.GIF
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteGIFSUseCase  @Inject constructor(
    private val freshGIFSRepository: FreshGIFSRepository,
) {

    operator fun invoke(): Flow<List<GIF>> =
        freshGIFSRepository.getAllFavoriteGIFS()
}

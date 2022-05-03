package com.jonas.freshgifs.domain.usecase

import com.jonas.freshgifs.data.repository.FreshGIFSRepository
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.util.Util
import javax.inject.Inject

class GetTrendingGIFSUseCase @Inject constructor(
    private val freshGIFSRepository: FreshGIFSRepository,
    private val isFavoriteGIFUseCase: IsFavoriteGIFUseCase,
) {

    suspend operator fun invoke(): List<GIF> {
        return freshGIFSRepository.getTrendingGIFS(Util.API_KEY).map {
            it.copy(
                isFavorite =  isFavoriteGIFUseCase(it)
            )
        }
    }
}

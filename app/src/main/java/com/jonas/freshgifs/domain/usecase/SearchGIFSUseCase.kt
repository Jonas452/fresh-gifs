package com.jonas.freshgifs.domain.usecase

import com.jonas.freshgifs.data.repository.FreshGIFSRepository
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.util.Util
import javax.inject.Inject

class SearchGIFSUseCase @Inject constructor(
    private val freshGIFSRepository: FreshGIFSRepository,
    private val isFavoriteGIFUseCase: IsFavoriteGIFUseCase,
) {

    suspend operator fun invoke(query: String): List<GIF> {
        return freshGIFSRepository.searchGIFS(Util.API_KEY, query).map {
            it.copy(
                isFavorite =  isFavoriteGIFUseCase(it)
            )
        }
    }
}

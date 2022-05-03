package com.jonas.freshgifs.domain.usecase

import com.jonas.freshgifs.data.repository.FreshGIFSRepository
import com.jonas.freshgifs.domain.model.GIF
import javax.inject.Inject

class RemoveFavoriteGIFUseCase @Inject constructor(
    private val freshGIFSRepository: FreshGIFSRepository,
) {

    suspend operator fun invoke(gif: GIF) {
        freshGIFSRepository.delete(gif)
    }
}

package com.jonas.freshgifs.domain.usecase

import com.jonas.freshgifs.domain.model.GIF
import javax.inject.Inject

/*
    Explanation:
    All gifs could be cached with room and the trending/search screen could load all the cached data from room
    not needing to revalidate the favorite list (this can cause potential problems depending on the users interaction).
    I kept the impl like due to time constraint and the right way would be to refac to have room as the single source of true of the data
 */
class RevalidateFavoriteGIFSUseCase  @Inject constructor(
    private val isFavoriteGIFUseCase: IsFavoriteGIFUseCase,
) {

    suspend operator fun invoke(gifs: List<GIF>): List<GIF> {
        return gifs.map { gif ->
            gif.copy(
                isFavorite = isFavoriteGIFUseCase(gif)
            )
        }
    }
}

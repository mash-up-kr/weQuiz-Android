/*
 * Designed and developed by "옴마야" Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/mash-up-kr/WeQuiz-Android/blob/main/LICENSE
 */

package team.ommaya.wequiz.android.domain.model.rank

data class Rank(
    val cursorUserId: Int?,
    val cursorScore: Int?,
    val rankings: List<RankingsItem>,
    val hasNext: Boolean,
)

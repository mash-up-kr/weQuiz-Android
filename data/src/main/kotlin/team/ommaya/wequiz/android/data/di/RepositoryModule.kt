/*
 * Designed and developed by "옴마야" Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/mash-up-kr/WeQuiz-Android/blob/main/LICENSE
 */

package team.ommaya.wequiz.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.ommaya.wequiz.android.data.repository.FirebaseAuthRepositoryImpl
import team.ommaya.wequiz.android.data.repository.QuizRepositoryImpl
import team.ommaya.wequiz.android.data.repository.RankRepositoryImpl
import team.ommaya.wequiz.android.data.repository.UserRepositoryImpl
import team.ommaya.wequiz.android.domain.repository.FirebaseAuthRepository
import team.ommaya.wequiz.android.domain.repository.QuizRepository
import team.ommaya.wequiz.android.domain.repository.RankRepository
import team.ommaya.wequiz.android.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindRankRepository(impl: RankRepositoryImpl): RankRepository

    @Binds
    @Singleton
    abstract fun bindQuizRepository(impl: QuizRepositoryImpl): QuizRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthRepository(
        firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl,
    ): FirebaseAuthRepository
}

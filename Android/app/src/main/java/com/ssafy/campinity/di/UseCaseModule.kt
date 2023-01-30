package com.ssafy.campinity.di

import com.ssafy.campinity.domain.repository.AuthRepository
import com.ssafy.campinity.domain.repository.NoteRepository
import com.ssafy.campinity.domain.repository.UserRepository
import com.ssafy.campinity.domain.usecase.auth.LoginUseCase
import com.ssafy.campinity.domain.usecase.note.NoteMyQuestionUseCase
import com.ssafy.campinity.domain.usecase.user.CheckDuplicationUseCase
import com.ssafy.campinity.domain.usecase.user.EditUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideNoteMyQuestionUseCase(noteRepository: NoteRepository): NoteMyQuestionUseCase {
        return NoteMyQuestionUseCase(noteRepository)
    }
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Singleton
    @Provides
    fun provideEditUserUseCase(userRepository: UserRepository): EditUserUseCase =
        EditUserUseCase(userRepository)

    @Singleton
    @Provides
    fun provideCheckDuplicationUseCase(userRepository: UserRepository): CheckDuplicationUseCase =
        CheckDuplicationUseCase(userRepository)
}
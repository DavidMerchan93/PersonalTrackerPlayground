package com.davidmerchan.personaltrackerplayground.di

import com.davidmerchan.personaltrackerplayground.data.repository.InMemoryExpenseRepository
import com.davidmerchan.personaltrackerplayground.domain.repository.ExpenseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module responsible for providing dependency injection bindings
 * related to repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * Binds the [InMemoryExpenseRepository] implementation to the [ExpenseRepository] interface.
     * Whenever an [ExpenseRepository] is injected, Hilt will provide an instance
     * of [InMemoryExpenseRepository].
     *
     * @param inMemoryExpenseRepository The concrete implementation of the repository.
     * @return The bound [ExpenseRepository] interface.
     */
    @Binds
    abstract fun bindExpenseRepository(
        inMemoryExpenseRepository: InMemoryExpenseRepository
    ): ExpenseRepository
}

package com.davidmerchan.personaltrackerplayground.domain.repository

import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import kotlinx.coroutines.flow.Flow

/**
 * Defines the contract for data operations related to [Expense] objects.
 * This repository interface allows abstracting the data source from the domain layer.
 */
interface ExpenseRepository {
    /**
     * Retrieves a continuous stream of expenses.
     *
     * @return A [Flow] emitting the list of all recorded expenses.
     */
    fun getExpenses(): Flow<List<Expense>>

    /**
     * Records a new expense into the repository.
     *
     * @param expense The [Expense] to be added.
     */
    suspend fun addExpense(expense: Expense)
}

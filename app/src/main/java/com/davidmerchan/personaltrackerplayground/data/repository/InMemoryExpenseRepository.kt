package com.davidmerchan.personaltrackerplayground.data.repository

import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * An in-memory implementation of [ExpenseRepository].
 * This repository holds the expenses data only in RAM and does not persist it 
 * across app restarts. It is mainly used for testing or demonstration purposes.
 */
@Singleton
class InMemoryExpenseRepository @Inject constructor() : ExpenseRepository {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    
    /**
     * Retrieves the current stream of expenses from the in-memory list.
     *
     * @return A [Flow] emitting the list of all recorded expenses.
     */
    override fun getExpenses(): Flow<List<Expense>> {
        return _expenses.asStateFlow()
    }

    /**
     * Adds a new expense to the in-memory list.
     *
     * @param expense The [Expense] to be added.
     */
    override suspend fun addExpense(expense: Expense) {
        _expenses.update { currentList ->
            currentList + expense
        }
    }
}

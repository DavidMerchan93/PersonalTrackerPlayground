package com.davidmerchan.personaltrackerplayground.domain.usecase

import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeExpenseRepository : ExpenseRepository {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())

    override fun getExpenses(): Flow<List<Expense>> {
        return _expenses.asStateFlow()
    }

    override suspend fun addExpense(expense: Expense) {
        _expenses.update { currentList ->
            currentList + expense
        }
    }
}

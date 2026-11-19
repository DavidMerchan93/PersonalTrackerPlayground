package com.davidmerchan.personaltrackerplayground.domain.usecase

import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case responsible for adding a new expense.
 * Encapsulates the business logic required to save a new expense record.
 *
 * @property repository The [ExpenseRepository] used to persist the new expense.
 */
class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    /**
     * Executes the use case to add a single expense.
     *
     * @param expense The [Expense] object to be saved.
     */
    suspend operator fun invoke(expense: Expense) {
        repository.addExpense(expense)
    }
}

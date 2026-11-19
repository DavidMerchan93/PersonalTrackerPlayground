package com.davidmerchan.personaltrackerplayground.domain.usecase

import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case responsible for retrieving all expenses.
 * Acts as an intermediary between the presentation layer and the data layer
 * to fetch the stream of expenses.
 *
 * @property repository The [ExpenseRepository] used to fetch expenses data.
 */
class GetExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    /**
     * Executes the use case to obtain the list of expenses.
     *
     * @return A [Flow] emitting the list of [Expense].
     */
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getExpenses()
    }
}

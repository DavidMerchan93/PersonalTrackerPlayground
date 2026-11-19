package com.davidmerchan.personaltrackerplayground.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.domain.usecase.AddExpenseUseCase
import com.davidmerchan.personaltrackerplayground.domain.usecase.GetExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing and presenting expense-related data to the UI.
 * Handles user actions and business logic interactions regarding expenses.
 *
 * @property getExpensesUseCase Use case to retrieve the list of expenses.
 * @property addExpenseUseCase Use case to add a new expense.
 */
@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    /**
     * A state flow representing the current list of expenses.
     * It actively subscribes to the [getExpensesUseCase] and transforms it into a [StateFlow]
     * that the UI can observe.
     */
    val expenses: StateFlow<List<Expense>> = getExpensesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * A state flow representing the total amount spent across all recorded expenses.
     * It is calculated dynamically based on the [expenses] flow.
     */
    val totalSpent: StateFlow<Double> = expenses
        .map { list -> list.sumOf { it.amount } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    /**
     * Initiates the process of adding a new expense through the respective use case.
     *
     * @param expense The new [Expense] instance to add.
     */
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            addExpenseUseCase(expense)
        }
    }
}
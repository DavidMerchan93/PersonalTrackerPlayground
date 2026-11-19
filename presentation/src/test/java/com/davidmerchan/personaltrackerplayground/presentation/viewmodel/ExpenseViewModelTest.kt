package com.davidmerchan.personaltrackerplayground.presentation.viewmodel

import app.cash.turbine.test
import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.domain.repository.ExpenseRepository
import com.davidmerchan.personaltrackerplayground.domain.usecase.AddExpenseUseCase
import com.davidmerchan.personaltrackerplayground.domain.usecase.GetExpensesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExpenseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakeExpenseRepository
    private lateinit var viewModel: ExpenseViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeExpenseRepository()
        val getExpensesUseCase = GetExpensesUseCase(fakeRepository)
        val addExpenseUseCase = AddExpenseUseCase(fakeRepository)
        viewModel = ExpenseViewModel(getExpensesUseCase, addExpenseUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `expenses flow reflects expenses from repository`() = runTest {
        viewModel.expenses.test {
            val initial = awaitItem()
            assertEquals(emptyList<Expense>(), initial)

            val expense = Expense("1", 25.0, "Lunch", System.currentTimeMillis(), "Food")
            viewModel.addExpense(expense)

            val updated = awaitItem()
            assertEquals(1, updated.size)
            assertEquals("Lunch", updated[0].description)
        }
    }

    @Test
    fun `totalSpent calculates correct sum of amounts`() = runTest {
        viewModel.totalSpent.test {
            val initial = awaitItem()
            assertEquals(0.0, initial, 0.01)

            viewModel.addExpense(Expense("1", 25.0, "Lunch", System.currentTimeMillis(), "Food"))
            assertEquals(25.0, awaitItem(), 0.01)

            viewModel.addExpense(Expense("2", 15.0, "Taxi", System.currentTimeMillis(), "Transport"))
            assertEquals(40.0, awaitItem(), 0.01)
        }
    }

    private class FakeExpenseRepository : ExpenseRepository {
        private val expensesFlow = MutableStateFlow<List<Expense>>(emptyList())

        override fun getExpenses(): Flow<List<Expense>> = expensesFlow

        override suspend fun addExpense(expense: Expense) {
            expensesFlow.value = expensesFlow.value + expense
        }
    }
}

package com.davidmerchan.personaltrackerplayground.domain.usecase

import app.cash.turbine.test
import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [GetExpensesUseCase].
 */
class GetExpensesUseCaseTest {

    private lateinit var getExpensesUseCase: GetExpensesUseCase
    private lateinit var repository: FakeExpenseRepository

    /**
     * Sets up the test dependencies before each test execution.
     */
    @Before
    fun setUp() {
        repository = FakeExpenseRepository()
        getExpensesUseCase = GetExpensesUseCase(repository)
    }

    /**
     * Verifies that invoking [GetExpensesUseCase] streams updates from the repository as new expenses are added.
     */
    @Test
    fun `invoke emits updates from repository`() = runTest {
        val expense1 = Expense("1", 10.0, "Coffee", 123456789L, "Food")
        val expense2 = Expense("2", 20.0, "Lunch", 123456799L, "Food")
        
        getExpensesUseCase().test {
            // Initial state
            assertEquals(emptyList<Expense>(), awaitItem())
            
            // Add first expense
            repository.addExpense(expense1)
            assertEquals(listOf(expense1), awaitItem())
            
            // Add second expense
            repository.addExpense(expense2)
            assertEquals(listOf(expense1, expense2), awaitItem())
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}

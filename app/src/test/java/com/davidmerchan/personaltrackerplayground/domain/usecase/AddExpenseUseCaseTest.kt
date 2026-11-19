package com.davidmerchan.personaltrackerplayground.domain.usecase

import app.cash.turbine.test
import com.davidmerchan.personaltrackerplayground.data.repository.InMemoryExpenseRepository
import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [AddExpenseUseCase].
 */
class AddExpenseUseCaseTest {

    private lateinit var addExpenseUseCase: AddExpenseUseCase
    private lateinit var repository: InMemoryExpenseRepository

    /**
     * Sets up the test dependencies before each test execution.
     */
    @Before
    fun setUp() {
        repository = InMemoryExpenseRepository()
        addExpenseUseCase = AddExpenseUseCase(repository)
    }

    /**
     * Verifies that invoking [AddExpenseUseCase] correctly delegates to the repository to store the expense.
     */
    @Test
    fun `invoke adds expense to repository`() = runTest {
        val expense = Expense("1", 10.0, "Coffee", 123456789L, "Food")
        
        repository.getExpenses().test {
            assertEquals(emptyList<Expense>(), awaitItem())
            
            addExpenseUseCase(expense)
            
            assertEquals(listOf(expense), awaitItem())
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}

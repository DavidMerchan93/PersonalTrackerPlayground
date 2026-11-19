package com.davidmerchan.personaltrackerplayground.data.repository

import app.cash.turbine.test
import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [InMemoryExpenseRepository].
 */
class InMemoryExpenseRepositoryTest {

    private lateinit var repository: InMemoryExpenseRepository

    /**
     * Sets up the test environment before each test execution.
     */
    @Before
    fun setUp() {
        repository = InMemoryExpenseRepository()
    }

    /**
     * Verifies that [InMemoryExpenseRepository.getExpenses] initially emits an empty list
     * and subsequently emits an updated list containing newly added expenses.
     */
    @Test
    fun `getExpenses emits initial empty list and updates when expense is added`() = runTest {
        val expense = Expense("1", 10.0, "Coffee", 123456789L, "Food")
        
        repository.getExpenses().test {
            // Initial emission should be an empty list
            assertEquals(emptyList<Expense>(), awaitItem())
            
            // Add an expense
            repository.addExpense(expense)
            
            // Should emit the new list containing the expense
            assertEquals(listOf(expense), awaitItem())
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}

package com.davidmerchan.personaltrackerplayground.domain.model

/**
 * Represents a single financial expense.
 *
 * @property id The unique identifier for the expense.
 * @property amount The monetary value of the expense.
 * @property description A brief textual description of what the expense was for.
 * @property date The timestamp of when the expense occurred, represented in milliseconds since epoch.
 * @property category The category to which this expense belongs (e.g., "Food", "Transport").
 */
data class Expense(
    val id: String,
    val amount: Double,
    val description: String,
    val date: Long,
    val category: String
)

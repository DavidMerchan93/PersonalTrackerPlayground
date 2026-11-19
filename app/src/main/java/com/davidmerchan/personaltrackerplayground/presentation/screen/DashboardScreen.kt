package com.davidmerchan.personaltrackerplayground.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.ui.theme.CategoryEntertainment
import com.davidmerchan.personaltrackerplayground.ui.theme.CategoryFood
import com.davidmerchan.personaltrackerplayground.ui.theme.CategoryHousing
import com.davidmerchan.personaltrackerplayground.ui.theme.CategoryOthers
import com.davidmerchan.personaltrackerplayground.ui.theme.CategoryTransport
import com.davidmerchan.personaltrackerplayground.ui.theme.CategoryUtilities

/**
 * Maps an expense category name to its corresponding accent [Color].
 *
 * @param category The string representation of the expense category.
 * @return The associated [Color] token for the category.
 */
fun getCategoryColor(category: String): Color {
    return when (category) {
        "Food" -> CategoryFood
        "Transport" -> CategoryTransport
        "Housing" -> CategoryHousing
        "Utilities" -> CategoryUtilities
        "Entertainment" -> CategoryEntertainment
        else -> CategoryOthers
    }
}

/**
 * Displays the main dashboard screen showing total expenses spent and a list of recent transactions.
 *
 * @param expenses List of [Expense] items to display.
 * @param totalSpent Total monetary amount spent across all expenses.
 * @param onAddExpenseClick Callback triggered when the floating action button to add an expense is clicked.
 * @param modifier The [Modifier] to be applied to the dashboard screen container.
 */
@Composable
fun DashboardScreen(
    expenses: List<Expense>,
    totalSpent: Double,
    onAddExpenseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)) {
            
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Total Spent", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = String.format("$%.2f", totalSpent), 
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp
                        )
                    )
                }
            }

            Text(
                text = "Recent Transactions", 
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expenses) { expense ->
                    TransactionItem(expense = expense)
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp)) // FAB padding
                }
            }
        }
    }
}

/**
 * Composable representing an individual transaction card in the expense list.
 *
 * @param expense The [Expense] details to display.
 */
@Composable
fun TransactionItem(expense: Expense) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(getCategoryColor(expense.category))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = expense.description, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = expense.category, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
            }
            Text(
                text = String.format("$%.2f", expense.amount),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Preview for [DashboardScreen] with sample expense data.
 */
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen(
            expenses = listOf(
                Expense(
                    id = "1",
                    amount = 12.50,
                    description = "Lunch",
                    date = System.currentTimeMillis(),
                    category = "Food"
                ),
                Expense(
                    id = "2",
                    amount = 2.50,
                    description = "Bus Ticket",
                    date = System.currentTimeMillis(),
                    category = "Transport"
                )
            ),
            totalSpent = 15.00,
            onAddExpenseClick = {}
        )
    }
}

/**
 * Preview for [TransactionItem] with sample expense data.
 */
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    MaterialTheme {
        TransactionItem(
            expense = Expense(
                id = "1",
                amount = 12.50,
                description = "Lunch",
                date = System.currentTimeMillis(),
                category = "Food"
            )
        )
    }
}

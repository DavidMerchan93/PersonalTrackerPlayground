package com.davidmerchan.personaltrackerplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowWidthSizeClass
import com.davidmerchan.personaltrackerplayground.domain.model.Expense
import com.davidmerchan.personaltrackerplayground.presentation.screen.AddExpenseScreen
import com.davidmerchan.personaltrackerplayground.presentation.screen.DashboardScreen
import com.davidmerchan.personaltrackerplayground.ui.theme.PersonalTrackerPlaygroundTheme
import com.davidmerchan.personaltrackerplayground.presentation.viewmodel.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Navigation destination key for the Dashboard screen.
 */
@Serializable
object Dashboard : NavKey

/**
 * Navigation destination key for the Add Expense screen.
 */
@Serializable
object AddExpense : NavKey

/**
 * Main activity for the Personal Tracker application.
 * Serves as the single activity host managing Navigation 3 backstack and adaptive UI layouts.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting. Initializes edge-to-edge display and sets up
     * the Compose UI hierarchy with Navigation 3 and adaptive layout strategies.
     *
     * @param savedInstanceState If non-null, this activity is being re-constructed from a previous saved state.
     */
    @OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalTrackerPlaygroundTheme {
                val backStack = rememberNavBackStack(Dashboard)
                
                val windowAdaptiveInfo = currentWindowAdaptiveInfo()
                val directive = remember(windowAdaptiveInfo) {
                    calculatePaneScaffoldDirective(windowAdaptiveInfo)
                }
                
                val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        sceneStrategies = listOf(listDetailStrategy),
                        modifier = Modifier.padding(innerPadding),
                        entryProvider = entryProvider {
                            entry<Dashboard>(
                                metadata = ListDetailSceneStrategy.listPane(
                                    detailPlaceholder = {
                                        Box(Modifier.fillMaxSize()) {
                                            Text(stringResource(R.string.select_an_action))
                                        }
                                    }
                                )
                            ) {
                                val viewModel: ExpenseViewModel = hiltViewModel()
                                val expenses by viewModel.expenses.collectAsStateWithLifecycle()
                                val totalSpent by viewModel.totalSpent.collectAsStateWithLifecycle()

                                DashboardScreen(
                                    expenses = expenses,
                                    totalSpent = totalSpent,
                                    onAddExpenseClick = dropUnlessResumed {
                                        backStack.add(AddExpense)
                                    }
                                )
                            }
                            
                            entry<AddExpense>(
                                metadata = ListDetailSceneStrategy.detailPane()
                            ) {
                                val viewModel: ExpenseViewModel = hiltViewModel()
                                
                                val isCompact = windowAdaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
                                
                                val content = @Composable {
                                    AddExpenseScreen(
                                        onSave = { amount, desc, category ->
                                            viewModel.addExpense(
                                                Expense(
                                                    id = UUID.randomUUID().toString(),
                                                    amount = amount,
                                                    description = desc,
                                                    date = System.currentTimeMillis(),
                                                    category = category
                                                )
                                            )
                                            backStack.removeLastOrNull()
                                        },
                                        onCancel = {
                                            backStack.removeLastOrNull()
                                        }
                                    )
                                }

                                if (isCompact) {
                                    ModalBottomSheet(
                                        onDismissRequest = { backStack.removeLastOrNull() }
                                    ) {
                                        content()
                                    }
                                } else {
                                    content()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

# 💰 Personal Expense Tracker Playground

![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)
![Android](https://img.shields.io/badge/Android-SDK%2035-green.svg)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20Multi--Module-orange.svg)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose%20%26%20M3-purple.svg)

**Personal Expense Tracker Playground** is a modern, modular Android application designed as a playground for best practices in Android development. It demonstrates multi-module Clean Architecture, Jetpack Compose with Material 3 design system, state management with Coroutines/Flow, Jetpack Navigation 3, and Dagger Hilt dependency injection.

---

## 📱 Features

- **📊 Dashboard Overview**: Displays a prominent "Total Spent" financial summary card alongside quick category breakdown stats and recent expense activities.
- **🏷️ Categorized Expense Tracking**: Expense items assigned to distinct categories (Food 🍔, Transport 🚗, Shopping 🛍️, Utilities 💡, Entertainment 🎬, Other 📦) with customized visual icons and theme colors.
- **➕ Add Expense Modal**: Clean modal sheet/dialog allowing users to add new expenses seamlessly with instant reactive UI updates.
- **🎨 Financial Light-Green Theme**: Custom Material 3 color palette built around an energetic light-green financial aesthetic supporting dark and light modes with dynamic color compatibility.
- **🌐 String Externalization & i18n**: Fully externalized string resources providing native support for English and Spanish localizations.

---

## 🏗️ Architecture

The project strictly follows **Clean Architecture** principles decoupled into four specialized Gradle modules:

```text
               ┌───────────────────────┐
               │         :app          │  (Application Entry & Nav 3)
               └──────────┬────────────┘
                          │
         ┌────────────────┴────────────────┐
         ▼                                 ▼
┌─────────────────┐               ┌─────────────────┐
│  :presentation  │               │      :data      │
└────────┬────────┘               └────────┬────────┘
         │                                 │
         └────────────────┬────────────────┘
                          ▼
               ┌───────────────────────┐
               │        :domain        │  (Pure Kotlin JVM)
               └───────────────────────┘
```

### Module Breakdown

- **`:app`**
  - Application entry point (`PersonalTrackerApplication`) with `@HiltAndroidApp` annotation.
  - `MainActivity` hosting the root Jetpack Navigation 3 navigation graph (`NavDisplay`).
  - Assembles presentation, domain, and data dependencies together.

- **`:presentation`**
  - Built entirely with **Jetpack Compose** and **Material 3**.
  - `DashboardScreen` and `AddExpenseScreen` composable screens with edge-to-edge support.
  - `ExpenseViewModel` utilizing `StateFlow` to expose immutable `ExpenseUiState`.
  - Design tokens (`Color.kt`, `Theme.kt`, `Type.kt`), preview functions, and localized string resources.

- **`:domain`**
  - **Pure Kotlin JVM module** (no Android dependencies) ensuring high testability and fast build times.
  - Core domain models: `Expense` and `Category`.
  - Repository interface contracts: `ExpenseRepository`.
  - Use cases: `GetExpensesUseCase` and `AddExpenseUseCase`.

- **`:data`**
  - Implementation of domain repository contracts: `InMemoryExpenseRepository`.
  - Local reactive state data source powered by Kotlin `MutableStateFlow`.
  - Dagger Hilt dependency injection modules (`RepositoryModule`).

---

## 🛠️ Tech Stack & Libraries

| Layer / Concern | Technologies |
| :--- | :--- |
| **Language** | Kotlin 2.1.0 |
| **UI Framework** | Jetpack Compose & Material 3 (Light-green financial motif) |
| **Navigation** | Jetpack Navigation 3 (`androidx.navigation3`) & Compose Material Adaptive |
| **State & Async** | Kotlin Coroutines, `Flow`, `StateFlow` |
| **Dependency Injection** | Dagger Hilt 2.51.1 |
| **Architecture** | Clean Architecture, Multi-Module, MVI/MVVM pattern |
| **Unit Testing** | JUnit 4, Kotlinx Coroutines Test, Turbine |

---

## 🚀 Getting Started / How to Run

### Prerequisites
- **Android Studio**: Ladybug (2024.2.1) or newer recommended.
- **JDK**: JDK 17 or JDK 21.
- **Android SDK**: API Level 35 (Android 15) installed.

### Opening in Android Studio
1. Clone or open the project folder in Android Studio:
   ```bash
   /Users/davidmerchan/Documents/Proyectos/PersonalTrackerPlayground
   ```
2. Allow Gradle sync to complete automatically.

### Build and Run via Gradle CLI

- **Assemble Debug APK**:
  ```bash
  ./gradlew assembleDebug
  ```

- **Run Unit Tests across all modules**:
  ```bash
  ./gradlew testDebugUnitTest
  ```

- **Install and Run on Connected Device / Emulator**:
  ```bash
  ./gradlew :app:installDebug
  ```

---

## 🧪 Testing Strategy

Unit tests are written for use cases in the `:domain` module and ViewModels in the `:presentation` module:
- `AddExpenseUseCaseTest` & `GetExpensesUseCaseTest`: Pure JVM tests utilizing fake repository implementations.
- `ExpenseViewModelTest`: Tests UI state emissions using `Turbine` and `StandardTestDispatcher`.

Run all unit tests with:
```bash
./gradlew test
```

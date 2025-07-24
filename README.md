# 📱 TripManager

**TripManager** es una aplicación Android que permite gestionar los gastos de un viaje de forma estructurada, agrupándolos por secciones (como "Comida", "Transporte", "Alojamiento", etc.). Fue desarrollada utilizando Jetpack Compose, Room y el patrón MVVM.

---

## ✨ Características

- Crear y eliminar secciones de gastos
- Añadir gastos con cantidad y descripción a una sección específica
- Visualizar gastos por sección en una lista
- Eliminar gastos individuales
- Calcular y mostrar el total de gastos por sección
- Persistencia local mediante Room

---

## 🛠️ Tecnologías

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose) – UI moderna para Android
- [Room](https://developer.android.com/training/data-storage/room) – Base de datos local SQLite
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) + [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- Arquitectura **MVVM**

---

## 📦 Estructura del Proyecto

TripManager/

├── data/

│ ├── AppDatabase.kt # Configuración de Room

│ ├── Expense.kt # Modelo de datos: Expense

│ └── ExpenseSection.kt # Modelo de datos: ExpenseSection

├── viewmodel/

│ └── ExpenseViewModel.kt # Lógica de negocio y acceso a datos

└── MainActivity.kt # Composable UI principal

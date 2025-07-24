package com.example.tripmanager.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val description: String,
    val sectionId: Int // Usaremos sectionId para vincular los gastos a las secciones
)

@Entity
data class ExpenseSection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM Expense WHERE sectionId = :sectionId")
    fun getExpensesBySection(sectionId: Int): Flow<List<Expense>>

    @Insert
    suspend fun insertSection(section: ExpenseSection)

    @Delete
    suspend fun deleteSection(section: ExpenseSection)

    @Query("SELECT * FROM ExpenseSection")
    fun getAllSections(): Flow<List<ExpenseSection>>
}


@Database(entities = [Expense::class, ExpenseSection::class], version = 3) // Aumenta la versión aquí
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_database"
                )
                    .fallbackToDestructiveMigration() // Añade esta línea para reiniciar la base de datos
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

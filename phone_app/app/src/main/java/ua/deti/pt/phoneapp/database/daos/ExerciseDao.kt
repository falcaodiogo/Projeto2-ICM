package ua.deti.pt.phoneapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import ua.deti.pt.phoneapp.database.entities.Exercise

@Dao
interface ExerciseDao {

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM EXERCISE WHERE exerciseId = :id")
    fun getExerciseById(id: Long): Exercise

    @Query("SELECT * FROM EXERCISE WHERE exerciseType = :exerciseType")
    fun getExerciseByType(exerciseType: String): Exercise

    @Transaction
    @Query("SELECT * FROM EXERCISE WHERE userAssociatedId = :userId")
    fun getExercisesByUserId(userId: Long): List<Exercise>

    @Transaction
    @Query("SELECT * FROM EXERCISE WHERE userAssociatedId = :userId AND dayOfWeek = :dayOfWeek")
    fun getExercisesByUserIdAndDay(userId: Long, dayOfWeek: String): List<Exercise>

}
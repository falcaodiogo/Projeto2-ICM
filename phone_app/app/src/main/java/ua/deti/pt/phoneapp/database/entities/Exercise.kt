package ua.deti.pt.phoneapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "EXERCISE")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long? = null,
    @Nonnull
    val exerciseType: String,
    @Nonnull
    val dayOfWeek: String,
    @Nonnull
    val userAssociatedId: Long,
    val isCompleted: Boolean = false,
)

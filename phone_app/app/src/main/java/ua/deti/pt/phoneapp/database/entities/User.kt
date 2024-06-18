package ua.deti.pt.phoneapp.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ua.deti.pt.phoneapp.database.entities.Exercise
import javax.annotation.Nonnull

@Entity(tableName = "USER")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long? = null,
    @Nonnull
    val name: String,
    @Nonnull
    val email: String,

    var stepsGoal: Int,
)




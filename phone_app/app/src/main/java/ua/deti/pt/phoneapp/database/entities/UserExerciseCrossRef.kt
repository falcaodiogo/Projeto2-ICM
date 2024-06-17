package ua.deti.pt.phoneapp.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

/**
 * A many to many relationship, cause multiple users can have multiple exercises associated
 */
@Entity(primaryKeys = ["userId", "exerciseId"])
data class UserExerciseCrossRef(
    val userId: Long,
    val exerciseId: Long
)

data class UserWithExercises(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "exerciseId",
        associateBy = Junction(UserExerciseCrossRef::class)
    )
    val exercises: List<Exercise>
)

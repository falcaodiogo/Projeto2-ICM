package ua.deti.pt.phoneapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.deti.pt.phoneapp.database.daos.ExerciseDao
import ua.deti.pt.phoneapp.database.daos.UserDao
import ua.deti.pt.phoneapp.database.entities.Exercise
import ua.deti.pt.phoneapp.database.entities.User

@Database(
    entities = [User::class, Exercise::class],
    version = 1
)
abstract class HealthTrackerDatabase: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val exerciseDao: ExerciseDao

}
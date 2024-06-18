package ua.deti.pt.phoneapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import ua.deti.pt.phoneapp.database.entities.User

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM USER WHERE userId = :id")
    fun getUserById(id: Long): User

    @Query("SELECT * FROM USER WHERE name = :name")
    fun getUserByName(name: String): User

    @Query("SELECT * FROM USER WHERE email = :email")
    fun getUserByEmail(email: String): User

}
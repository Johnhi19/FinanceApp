package com.example.finanzapp.objects
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OutgoingsDao {
    @Query("SELECT * FROM OutgoingsEntry")
    fun getAll(): Flow<List<OutgoingsEntry>>

    @Insert
    suspend fun insertOutgoing(outgoingsEntry: OutgoingsEntry)

    @Delete
    suspend fun deleteOutgoing(outgoingsEntry: OutgoingsEntry)
}
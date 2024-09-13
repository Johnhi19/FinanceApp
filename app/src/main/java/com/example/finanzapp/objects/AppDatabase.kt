package com.example.finanzapp.objects
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OutgoingsEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: OutgoingsDao
}
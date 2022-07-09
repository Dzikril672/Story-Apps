package com.dzikril.submission1.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dzikril.submission1.data.local.entity.RemoteKeys
import com.dzikril.submission1.data.local.entity.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDb : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDb? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDb::class.java, "story_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
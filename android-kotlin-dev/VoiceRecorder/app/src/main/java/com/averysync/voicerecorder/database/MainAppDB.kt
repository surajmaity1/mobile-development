package com.averysync.voicerecorder.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(VoiceRecordDB::class), version = 1)
abstract class MainAppDB : RoomDatabase() {
    abstract fun voiceRecordDao() : VoiceRecordDao
}
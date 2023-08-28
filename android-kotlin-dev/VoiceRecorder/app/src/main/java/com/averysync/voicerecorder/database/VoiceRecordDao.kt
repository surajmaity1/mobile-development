package com.averysync.voicerecorder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VoiceRecordDao {
    @Query("SELECT * FROM VoiceRecordDB")
    fun getAll(): List<VoiceRecordDB>

    @Query("SELECT * FROM VoiceRecordDB WHERE filename LIKE :query")
    fun searchDB(query: String): List<VoiceRecordDB>

    @Insert
    fun insert(vararg audioRecord: VoiceRecordDB)
}

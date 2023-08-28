package com.averysync.voicerecorder.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VoiceRecordDB")
data class VoiceRecordDB(
    var filename: String,
    var filePath: String,
    var timestamp: Long,
    var duration: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
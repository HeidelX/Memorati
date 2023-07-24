package com.memorati.core.v2

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

@RenameColumn(fromColumnName = "front", toColumnName = "idiom", tableName = "flashcards")
@RenameColumn(fromColumnName = "back", toColumnName = "meaning", tableName = "flashcards")
class V1V2AutoMigrationSpecs : AutoMigrationSpec

package com.example.plantdiseasedetector.data.datasource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.dao.ReportDao
import com.example.plantdiseasedetector.data.model.Advice
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.Report
import com.example.plantdiseasedetector.data.model.ReportItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Disease::class, Report::class, ReportItem::class, Advice::class],
    version = 1
)
abstract class LocalDataBase : RoomDatabase() {
    abstract val diseaseDao: DiseaseDao
    abstract val reportDao: ReportDao
}
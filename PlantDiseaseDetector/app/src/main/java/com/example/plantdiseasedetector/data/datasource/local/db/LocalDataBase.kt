package com.example.plantdiseasedetector.data.datasource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.model.Disease
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Disease::class],
    version = 1
)
abstract class LocalDataBase : RoomDatabase() {
    // сюда дописывать нужные DAO
    abstract val diseaseDao: DiseaseDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LocalDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDataBase::class.java,
                    "plant_disease_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.diseaseDao)
                    }
                }
            }
        }

        suspend fun populateDatabase(diseaseDao: DiseaseDao) {
            diseaseDao.insertDisease(Disease(title = "Мучнистая Роса", shortDesc = "Мучнистая Роса"))
            diseaseDao.insertDisease(Disease(title = "Ржавчина", shortDesc = "Ржавчина"))
            diseaseDao.insertDisease(Disease(title = "Пятнистость листьев", shortDesc = "Пятнистость листьев"))
            diseaseDao.insertDisease(Disease(title = "Поражение слизнями", shortDesc = "Поражение слизнями"))
        }


    }
}
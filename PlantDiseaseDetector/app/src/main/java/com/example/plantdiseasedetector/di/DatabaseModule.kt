package com.example.plantdiseasedetector.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.plantdiseasedetector.R
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.datasource.local.db.LocalDataBase
import com.example.plantdiseasedetector.data.model.Disease
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        daoProvider: Provider<DiseaseDao>,
        scope: CoroutineScope
    ): LocalDataBase {
        return Room.databaseBuilder(
            context,
            LocalDataBase::class.java,
            "plant_disease_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    scope.launch {
                        val dao = daoProvider.get()
                        dao.insertDisease(
                            Disease(
                                id = "powdery",
                                name = "Мучнистая Роса",
                                description = "Мучнистая Роса",
                                imageId = R.drawable.powdery,
                                marked = false
                            )
                        )
                        dao.insertDisease(
                            Disease(
                                id = "rust",
                                name = "Ржавчина",
                                description = "Ржавчина",
                                imageId = R.drawable.rust,
                                marked = false
                            )
                        )
                        dao.insertDisease(
                            Disease(
                                id = "slug",
                                name = "Поражение слизнями",
                                description = "Поражение слизнями",
                                imageId = R.drawable.slug,
                                marked = false
                            )
                        )
                        dao.insertDisease(
                            Disease(
                                id = "spot",
                                name = "Пятнистость листьев",
                                description = "Пятнистость листьев",
                                imageId = R.drawable.spot,
                                marked = false
                            )
                        )
                    }
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideDiseaseDao(db: LocalDataBase): DiseaseDao = db.diseaseDao
}


package com.example.plantdiseasedetector

import android.app.Application
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.model.Disease
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PlantDiseaseDetector : Application() {

    @Inject
    lateinit var diseaseDao: DiseaseDao

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            if (diseaseDao.getAllDiseasesOnce().isEmpty()) {
                diseaseDao.insertDisease(Disease(title = "Мучнистая Роса", shortDesc = "Мучнистая Роса"))
                diseaseDao.insertDisease(Disease(title = "Ржавчина", shortDesc = "Ржавчина"))
                diseaseDao.insertDisease(Disease(title = "Пятнистость листьев", shortDesc = "Пятнистость листьев"))
                diseaseDao.insertDisease(Disease(title = "Поражение слизнями", shortDesc = "Поражение слизнями"))
            }
        }
    }
}
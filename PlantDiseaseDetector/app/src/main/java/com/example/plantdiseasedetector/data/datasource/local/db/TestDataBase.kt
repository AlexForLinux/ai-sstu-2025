package com.example.plantdiseasedetector.data.datasource.local.db

import com.example.plantdiseasedetector.data.model.DiseasePreviewModel

//Шаблон базы
class TestDataBase {
    private val items = listOf(
        DiseasePreviewModel(1, "Мучнистая Роса", "Мучнистая Роса"),
        DiseasePreviewModel(2, "Ржавчина", "Ржавчина"),
        DiseasePreviewModel(3, "Пятнистость листьев", "Пятнистость листьев"),
        DiseasePreviewModel(4, "Поражение слизнями", "Поражение слизнями")
    )

    fun getDiseasePreviewList(): List<DiseasePreviewModel> = items
}
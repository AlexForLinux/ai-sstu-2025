package com.example.plantdiseasedetector.data.repository

import android.util.Log
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.dao.ReportDao
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.Report
import com.example.plantdiseasedetector.data.model.ReportItem
import com.example.plantdiseasedetector.data.model.ReportWithDetails
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

interface HistoryRepository {
    suspend fun getAllReports() : List<ReportWithDetails>
    suspend fun createReport(imagePath: String, items: List<ReportItem>) : Long
    suspend fun deleteReport(reportId: Long)
}

class HistoryRepositoryImpl @Inject constructor (
    private val reportDao: ReportDao
) : HistoryRepository {

    override suspend fun getAllReports(): List<ReportWithDetails> {
        return reportDao.getAllReportsWithDetailes()
    }

    override suspend fun createReport(
        imagePath: String,
        items: List<ReportItem>
    ): Long {
        return reportDao.insertReportWithItems(Report(imagePath = imagePath), items)
    }

    override suspend fun deleteReport(reportId: Long) {
        reportDao.deleteReportWithItems(reportId)
    }
}
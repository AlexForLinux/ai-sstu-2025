package com.example.plantdiseasedetector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.Report
import com.example.plantdiseasedetector.data.model.ReportDetailedItem
import com.example.plantdiseasedetector.data.model.ReportItem
import com.example.plantdiseasedetector.data.model.ReportWithDetails

@Dao
interface ReportDao {

    @Insert
    suspend fun insertReport(report: Report) : Long

    @Insert
    suspend fun insertReportItems(items: List<ReportItem>)

    @Transaction
    suspend fun insertReportWithItems(report: Report, items: List<ReportItem>): Long {
        val reportId = insertReport(report)

        val itemsWithReportId = items.map { it.copy(reportId = reportId) }
        insertReportItems(itemsWithReportId)

        return reportId
    }

    @Query("""
        SELECT report_items.*,
               diseases.name as diseaseName
        FROM report_items
        LEFT JOIN diseases ON report_items.diseaseClassName = diseases.className
        WHERE report_items.reportId = :reportId
    """)
    suspend fun getReportDetailedItems(reportId: Long): List<ReportDetailedItem>

    @Query("SELECT * FROM reports ORDER BY createdAt DESC")
    suspend fun getAllReports(): List<Report>

    @Transaction
    suspend fun getAllReportsWithDetailes(): List<ReportWithDetails> {
        val reports = getAllReports()
        return reports.map { report ->
            val detailedItems = getReportDetailedItems(report.id)
            ReportWithDetails(report, detailedItems)
        }
    }

    @Query("DELETE FROM reports WHERE id = :reportId")
    suspend fun deleteReportById(reportId: Long)

    @Query("DELETE FROM report_items WHERE reportId = :reportId")
    suspend fun deleteReportItemsByReportId(reportId: Long)

    @Transaction
    suspend fun deleteReportWithItems(reportId: Long) {
        deleteReportItemsByReportId(reportId)
        deleteReportById(reportId)
    }

}
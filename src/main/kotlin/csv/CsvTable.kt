package csv

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class CsvTable{
    private var numRows = 0
    private var numColumns = 0
    private var table = ArrayList<CsvRow>()
    private var localDatePattern = "MM/dd/yyyy"
    var tableName = "No Name"

    fun mapCsvTableBy(mapType: MapType, props: CsvTableProperties): CsvTable{
        val rows = ArrayList<CsvRow>()
        rows.add(titleRow(props.titles))
        val mapped = when(mapType) {
            MapType.KEYWORD -> byKeyword(props, rows)
            MapType.AMOUNT_RANGE -> byAmountRange(props, rows)
            MapType.DATE_RANGE -> byDateRange(props, rows)
        }
        if(mapType == MapType.DATE_RANGE || mapType == MapType.AMOUNT_RANGE)
            mapped.tableName = props.range.min.toString()+"TO"+props.range.max.toString()
        else
            mapped.tableName = props.keyword
        return mapped
    }

    private fun byDateRange(props: CsvTableProperties, rows: ArrayList<CsvRow>): CsvTable{
        for((i, row) in table.withIndex()){
            val dateStr = row.values[props.mappingColumnIndex].value as String
            val ofPattern = DateTimeFormatter.ofPattern(localDatePattern)
            val date = LocalDate.parse(dateStr, ofPattern)
            val min = props.range.min as LocalDate
            val max = props.range.max as LocalDate
            if(date in min..max) {
                rows.add(copyCsvRow(row, i))
            }
        }
        val mapped = rowsToCsvTable(rows)

        return mapped
    }

    private fun byAmountRange(props: CsvTableProperties, rows: ArrayList<CsvRow>): CsvTable{
        for((i, row) in table.withIndex()){
            val amtStr = row.values[props.mappingColumnIndex].value as String
            val nf = NumberFormat.getInstance()
            val amt = nf.parse(amtStr).toInt()
            val min = props.range.min as Int
            val max = props.range.max as Int
            if(amt in min..max) {
                rows.add(copyCsvRow(row, i))
            }
        }
        return rowsToCsvTable(rows)
    }

    private fun byKeyword(props: CsvTableProperties, rows: ArrayList<CsvRow>): CsvTable {
        for((i, row) in table.withIndex()){
            val msg = row.values[props.mappingColumnIndex].value as String
            if(msg.contains(props.keyword)) {
                rows.add(copyCsvRow(row, i))
            }
        }
        return rowsToCsvTable(rows)
    }

    private fun rowsToCsvTable(rows: ArrayList<CsvRow>) : CsvTable{
        val mapped = CsvTable()
        mapped.numRows = rows.size
        mapped.numColumns = numColumns
        mapped.table = rows
        return mapped
    }

    private fun titleRow(titles: List<String>) : CsvRow{
        val titleRow = CsvRow()
        for((i, title) in titles.withIndex()) {
            titleRow.values.add(CsvCell(0, i, title))
        }
      return titleRow
    }

    private fun copyCsvRow(row: CsvRow, rowIndex: Int): CsvRow {
        val newRow = CsvRow()
        for((j, csvCell) in row.values.withIndex()){
            newRow.values.add(CsvCell(rowIndex, j, csvCell.value))
        }
        return newRow
    }

    fun readCsv(filePath: String, desiredColumns: Set<Int>) {
        val csvParser = CSVParser(Files.newBufferedReader( Paths.get( filePath ) ), CSVFormat.DEFAULT)
        var dimX = 0
        var dimY = 0
        for((i, csvRow) in csvParser.withIndex()){
            val newRow = CsvRow()
            for((j, cell) in csvRow.withIndex()){
                if(i == 0 && j == 0) dimY = csvRow.size()-1
                if( desiredColumns.contains(j))
                    newRow.values.add(CsvCell(i, j, cell))
            }
            table.add(newRow)
            dimX++
        }
        numRows = dimX
        numColumns = dimY
    }

    fun writeCsv(filePath: String) {
        val csvFileOutputStream = FileOutputStream( filePath )
        val writer = csvFileOutputStream.bufferedWriter()
        for(row in table){
            for(value in row.values){
                writer.write("${value.value},")
            }
            writer.newLine()
        }
        writer.flush()
    }
}

//fun appendSum(csvTable: CsvTable, amountIndex: Int): CsvTable {
//    var sum = 0.0
//    val rows = csvTable.table
//    val nf = NumberFormat.getInstance(Locale.US)
//    for(i in 1..<rows.size){
//        val amount: String = rows[i][amountIndex] as String
//        sum+=nf.parse(amount).toFloat()
//    }
//    csvTable.table.first().add(SUM)
//    csvTable.table[1].add(sum)
//    return CsvTable(csvTable.numRows+1, csvTable.numColumns, rows)
//}

//fun combineCsvTables(t1: CsvTable, t2: CsvTable): CsvTable {
//    if(t1.numRows > t2.numRows) {
//        return joinCsvTables(t1, t2)
//    }else {
//        return joinCsvTables(t2, t1)
//    }
//}
//
////t1 must have more rows than t2
//fun joinCsvTables(t1: CsvTable, t2: CsvTable): CsvTable {
//    for(i in 0..<t2.table.size){
//        t1.table[i].addAll(t2.table[i])
//    }
//    return CsvTable(t1.numRows, t1.numColumns+t2.numColumns, t1.table)
//}




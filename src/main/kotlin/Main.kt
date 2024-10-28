import csv.CsvTable
import csv.CsvTableProperties
import csv.MapType
import keyword.Keywords
import range.Ranges
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

const val LOCAL_PART = "C:/Users/wjett/Documents/"
const val PROJECT_NAME = "CSVEditor"
const val RESOURCES_PATH = "$LOCAL_PART$PROJECT_NAME/src/main/resources"

const val RAW_CSV_PATH = "$RESOURCES_PATH/inputs/raw_csv/"
const val PARSED_CSV_PATH = "$RESOURCES_PATH/outputs/"
const val KEYWORD_PATH = "$RESOURCES_PATH/inputs/keywords/"
const val AMOUNT_RANGE_PATH = "$RESOURCES_PATH/inputs/amountRanges/"
const val DATE_RANGE_PATH = "$RESOURCES_PATH/inputs/dateRanges/"

const val HIGH_LEVEL_PACKAGE = "highLevel/"
const val PURCHASE_PACKAGE = "purchase/"
const val PAYMENT_PACKAGE = "payment/"
const val AMOUNT_RANGES_PACKAGE = "amountRanges/"
const val DATE_RANGES_PACKAGE = "dateRanges/"

const val RAW_CHECKING = "Checking"
const val HIGH_LEVEL = "highLevel"
const val PURCHASE = "purchase"
const val PAYMENT = "payment"
const val AMOUNT_RANGES = "amountRanges"
const val DATE_RANGES = "dateRanges"

const val CSV = ".csv"
const val TXT = ".txt"

const val DATE ="DATE"
const val AMOUNT = "AMOUNT"
const val MESSAGE = "MESSAGE"

fun main() {
    val titles = arrayListOf(DATE, AMOUNT, MESSAGE)
    val desiredColumns = hashSetOf(0,1,4)
    val keywordMapProps = CsvTableProperties()
    keywordMapProps.mappingColumnIndex = titles.indexOf(MESSAGE)
    keywordMapProps.titles = titles

    writeAggregatedCsvTables(
        aggregateByKeywordsRunner(
            hashMapOf(
                Pair(
                    HIGH_LEVEL_PACKAGE,
                    readCsvTable(desiredColumns, RAW_CSV_PATH + RAW_CHECKING + CSV)
                ),
                Pair(
                    PURCHASE_PACKAGE,
                    readCsvTable(desiredColumns, RAW_CSV_PATH + RAW_CHECKING + CSV)
                ),
                Pair(
                    PAYMENT_PACKAGE,
                    readCsvTable(desiredColumns, RAW_CSV_PATH + RAW_CHECKING + CSV)
                )
            ),
            keywordMapProps
        )
    )

    val amountRangesProps = CsvTableProperties()
    amountRangesProps.titles = titles
    amountRangesProps.mappingColumnIndex = titles.indexOf(AMOUNT)
    writeAggregatedCsvTables(
        aggregateByAmountRangesRunner(
            readCsvTable(desiredColumns, RAW_CSV_PATH + RAW_CHECKING + CSV),
            amountRangesProps
        )
    )

    val dateRangesProps = CsvTableProperties()
    dateRangesProps.titles = titles
    dateRangesProps.mappingColumnIndex = titles.indexOf(DATE)
    writeAggregatedCsvTables(
        aggregateByDateRangesRunner(
            readCsvTable(desiredColumns, RAW_CSV_PATH + RAW_CHECKING + CSV),
            dateRangesProps
        )
    )
}

fun aggregateByDateRangesRunner(rawCsvTable: CsvTable, mapProps: CsvTableProperties) : HashMap<String, List<CsvTable>>{
    return hashMapOf(
        Pair(
            DATE_RANGES_PACKAGE,
            aggregateByDateRanges(
                FileUtility.readDateRangesFromTxtFile(DATE_RANGE_PATH + DATE_RANGES + TXT),
                rawCsvTable,
                mapProps
            )
        )
    )
}

fun aggregateByDateRanges(
    ranges: Ranges,
    rawCsvTable:CsvTable,
    mapProps: CsvTableProperties
) : List<CsvTable>
{
    val csvTables = ArrayList<CsvTable>()
    for(range in ranges.ranges) {
        mapProps.range = range
        csvTables.add(rawCsvTable.mapCsvTableBy(MapType.DATE_RANGE,mapProps))

    }
    return csvTables
}

fun aggregateByKeywordsRunner(rawCsvTables: HashMap<String, CsvTable>, mapProps: CsvTableProperties) : HashMap<String, List<CsvTable>>{
    return hashMapOf(
        Pair(
            HIGH_LEVEL_PACKAGE,
            aggregateByHighLevelKeywordsRunner(rawCsvTables[HIGH_LEVEL_PACKAGE]!!, mapProps)
        ),
        Pair(
            PURCHASE_PACKAGE,
            aggregateByPurchaseKeywordsRunner(rawCsvTables[PURCHASE_PACKAGE]!!, mapProps)
        ),
        Pair(
            PAYMENT_PACKAGE,
            aggregateByPaymentKeywordsRunner(rawCsvTables[PAYMENT_PACKAGE]!!, mapProps)
        )
    )
}

fun aggregateByPaymentKeywordsRunner(rawCsvTable: CsvTable, mapProps: CsvTableProperties) : List<CsvTable> {
    return aggregateByKeywords(
        FileUtility.readKeywordsFromTxtFile(KEYWORD_PATH + PAYMENT + TXT),
        rawCsvTable,
        mapProps
    )
}

fun aggregateByPurchaseKeywordsRunner(rawCsvTable: CsvTable, mapProps: CsvTableProperties) : List<CsvTable>{
    return aggregateByKeywords(
        FileUtility.readKeywordsFromTxtFile(KEYWORD_PATH + PURCHASE + TXT),
        rawCsvTable,
        mapProps
    )
}

fun aggregateByHighLevelKeywordsRunner(rawCsvTable: CsvTable, mapProps: CsvTableProperties) : List<CsvTable>{
    return aggregateByKeywords(
        FileUtility.readKeywordsFromTxtFile(KEYWORD_PATH + HIGH_LEVEL + TXT),
        rawCsvTable,
        mapProps
    )
}

fun aggregateByKeywords(
    keywords: Keywords,
    rawCsvTable:CsvTable,
    mapProps: CsvTableProperties
) : List<CsvTable>
{
    val csvTables = ArrayList<CsvTable>()
    for(keyword in keywords.keywords) {
        mapProps.keyword = keyword
        csvTables.add(rawCsvTable.mapCsvTableBy(MapType.KEYWORD, mapProps))
    }
    return csvTables
}

fun aggregateByAmountRangesRunner(rawCsvTable: CsvTable, mapProps: CsvTableProperties): HashMap<String, List<CsvTable>>{
    return hashMapOf(
        Pair(
            AMOUNT_RANGES_PACKAGE,
            aggregateByAmountRanges(
                FileUtility.readAmountRangesFromTxtFile(AMOUNT_RANGE_PATH + AMOUNT_RANGES + TXT),
                rawCsvTable,
                mapProps
            )
        )
    )
}

fun aggregateByAmountRanges(
    ranges: Ranges,
    rawCsvTable:CsvTable,
    mapProps: CsvTableProperties
): List<CsvTable>
{
    val csvTables = ArrayList<CsvTable>()
    for(range in ranges.ranges) {
        mapProps.range = range
        csvTables.add(rawCsvTable.mapCsvTableBy(MapType.AMOUNT_RANGE, mapProps))

    }
    return csvTables
}

fun readCsvTable(desiredColumns: HashSet<Int>, filePath: String): CsvTable{
    val rawCsvTable = CsvTable()
    rawCsvTable.readCsv( filePath, desiredColumns)
    return rawCsvTable
}

fun writeAggregatedCsvTables(aggregatedCsvTables: HashMap<String, List<CsvTable>>){
    for(csvTableListKey in aggregatedCsvTables.keys) {
        for(csvTable in aggregatedCsvTables[csvTableListKey]!!) {
            val outCsvFilePath = PARSED_CSV_PATH + csvTableListKey + csvTable.tableName + CSV
            csvTable.writeCsv(outCsvFilePath)
        }
    }
}
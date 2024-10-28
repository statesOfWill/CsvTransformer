package csv

data class CsvCell(
    val rowIndex: Int,
    val colIndex: Int,
    val value: Any
)

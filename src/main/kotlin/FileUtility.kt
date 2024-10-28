import keyword.Keywords
import range.Range
import range.Ranges
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FileUtility private constructor() {
    companion object {
        // The @Volatile annotation is needed to ensure that the instance property is updated atomically.
        // This prevents other threads from creating more instances and breaking the singleton pattern.
        @Volatile
        private var instance: FileUtility? = null
        // We need the synchronized keyword in the static getInstance method to prevent accessing the
        // method from multiple threads simultaneously.
        fun getInstance() {
            instance ?: synchronized(this) {
                instance ?: FileUtility().also { instance = it }
            }
        }
        fun readKeywordsFromTxtFile(filePath: String) : Keywords {
            val txtFileReader = Files.newBufferedReader( Paths.get( filePath ) )
            val keywords = Keywords()
            txtFileReader.forEachLine {
                keywords.add(it)
            }
            return keywords
        }

        fun readAmountRangesFromTxtFile(filePath: String) : Ranges {
            val txtFileReader = Files.newBufferedReader( Paths.get( filePath ) )
            val ranges = Ranges()
            txtFileReader.forEachLine {
                val rangeArr = it.split(",")
                val min = rangeArr[0]
                val max = rangeArr[1]
                ranges.add(Range(min.toInt(), max.toInt()))
            }
            return ranges
        }

        fun readDateRangesFromTxtFile(filePath: String) : Ranges {
            val txtFileReader = Files.newBufferedReader( Paths.get( filePath ) )
            val ranges = Ranges()
            txtFileReader.forEachLine {
                val rangeArr = it.split(",")
                val min = rangeArr[0]
                val max = rangeArr[1]
                //fun givenString_whenCustomFormat_thenLocalDateCreated() {
                //    val localDate = LocalDate.parse("01-06-2022", DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                //    assertThat(localDate).isEqualTo("2022-01-06")
                //}
                val ofPattern = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                ranges.add(Range(LocalDate.parse(min, ofPattern), LocalDate.parse(max, ofPattern)))
            }
            return ranges
        }
    }
}
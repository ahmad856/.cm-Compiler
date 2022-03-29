import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.OutputStreamWriter

class FileUtils {
    companion object {
        fun writeFile(file: String, s: String) {
            val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(File(file), true)))
            bw.write(s)
            bw.close()
        }

        fun lookUp(file: String, subString: String): Boolean {
            var found = false
            val br = BufferedReader(FileReader(file))

            var st = br.readLine()
            do {
                if (subString.equals(st, true)) {
                    found = true
                    break
                }
                st = br.readLine()
            } while (!st.isNullOrEmpty())
            return found
        }

        fun readFile(file: String): String {
            var output = ""
            try {
                val bufferedReader = BufferedReader(FileReader(file))
                var line = bufferedReader.readLine()
                do {
                    output = output.plus(line).plus("\n")
                    line = bufferedReader.readLine() // TODO: 3/23/2022 check with readLines()
                } while (!line.isNullOrEmpty())
            } catch (ex: FileNotFoundException) {
                println("File does not exist..\n" + ex.message)
            } catch (ex: IOException) {
                println("Problem Reading File..\n" + ex.message)
            }

//            output = output.trim()
//            output = output.replace(" +", " ") // TODO: 3/23/2022 check why this has to be done
            return output
        }
    }

}
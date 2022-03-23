import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.OutputStreamWriter

class FileUtils {
    fun writeFile(file: String, s: String) {
        val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(File(file), true)))
        bw.write(s)
        bw.close()
    }

    fun lookUp(file: String, subString: String) : Boolean{
        val file = File(file)

        var found = false
        val br = BufferedReader(FileReader(file))

        var st: String = br.readLine()
        do {
            if (subString.equals(st, true)) {
                found = true
                break
            }
            st = br.readLine()
        } while (!st.isNullOrEmpty())
        return found
    }
}
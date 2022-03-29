import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

fun main(args: Array<String>) {
//    println("Hello World!")
//
//    // Try adding program arguments via Run/Debug configuration.
//    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
//    println("Program arguments: ${args.joinToString()}")

    val scanner = Scanner(System.`in`)
    while (true) {
        print("\n> ")
        var inputCommand = scanner.nextLine()
        if (inputCommand.substring(0, 4).equals("cmm ", true)) {
            inputCommand = inputCommand.substring(4)

            val lexicalAnalyzer = LexicalAnalyzer(inputCommand)
            lexicalAnalyzer.run()



        } else if (inputCommand.contains("quit"))
            break
        else
            println("Please provide compiler....")
    }
}
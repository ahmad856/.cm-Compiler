import java.io.File

class LexicalAnalyzer() {

    private var inputFile: String = ""

    constructor(_inputFile: String) : this() {
        inputFile = _inputFile
    }

    //    private var index: Int = 0
    private val lambdaChar = "".plus(Char(955))
    private val relationalOperator = "RO"

    private val identifiersList: List<String> = ArrayList()
    private val symbolList: List<String> = ArrayList()
    private var tokenLexemePairList: List<TokenLexeme> = ArrayList()

    private fun isPunctuation(s: String): TokenLexeme? {
        if (s.isNotEmpty()) {
            if (s[0] == ';' || s[0] == ',') {
                return TokenLexeme(s[0].toString(), lambdaChar)
            }
        }
        return null
    }

    private fun isBracket(s: String): TokenLexeme? {
        if (s.isNotEmpty()) {
            if (s[0] == '(' || s[0] == ')' || s[0] == '[' || s[0] == ']' || s[0] == '{' || s[0] == '}') {
                return TokenLexeme(s[0].toString(), lambdaChar)
            }
        }
        return null
    }

    private fun isKeyWord(s: String): TokenLexeme? {
        if (s.isNotEmpty()) {
            if (s.length > 1 && s.substring(0, 2) == "if") {
                return TokenLexeme(s.substring(0, 2).uppercase(), lambdaChar)
            }
            if (s.length > 2) {
                val subString = s.substring(0, 3)
                if (subString == "def" || subString == "ret") {
                    return TokenLexeme(subString.uppercase(), lambdaChar)
                }
            }
            if (s.length > 3) {
                val subString = s.substring(0, 4)
                if (subString == "read" || subString == "else") {
                    return TokenLexeme(subString.uppercase(), lambdaChar)
                }
            }
            if (s.length > 4) {
                val subString = s.substring(0, 5)
                if (subString == "while" || subString == "print") {
                    return TokenLexeme(subString.uppercase(), lambdaChar)
                }
            }
        }
        return null
    }

    private fun isArithmeticOp(s: String): TokenLexeme? {
        if (s.isNotEmpty()) {
            if (s[0] == '+' || s[0] == '-' || s[0] == '*' || s[0] == '/') {
                return TokenLexeme(s[0].toString(), lambdaChar)
            }
        }
        return null
    }

    private fun isRelationalOp(s: String): TokenLexeme? {
        if (s.isNotEmpty()) {
            if (s.length > 1) {
                when (s.substring(0, 2)) {
                    "<=" -> return TokenLexeme(relationalOperator, "LE")
                    ">=" -> return TokenLexeme(relationalOperator, "GE")
                    "==" -> return TokenLexeme(relationalOperator, "EQ")
                    "<>" -> return TokenLexeme(relationalOperator, "NE")
                }
            }
            if (s.isNotEmpty()) {
                when (s.substring(0, 1)) {
                    "<" -> return TokenLexeme(relationalOperator, "LT")
                    ">" -> return TokenLexeme(relationalOperator, "GT")
                }
            }
        }
        return null
    }

    private fun isAssignmentOp(s: String): TokenLexeme? {
        if (s.length > 1) {
            if (s.substring(0, 2) == ("<-")) {
                return TokenLexeme("<-", lambdaChar)
            }
        }
        return null
    }

    private fun isDataType(s: String): TokenLexeme? {
        if (s.isNotEmpty()) {
            if (s.length > 3 && s.substring(0, 4) == "int ") {
                return TokenLexeme("INT", lambdaChar)
            }
            if (s.length > 4 && s.substring(0, 5) == "char ") {
                return TokenLexeme("CHAR", lambdaChar)
            }
        }
        return null
    }

    fun isLiteral(s: String): Int {
        var length = 0
        var state = 1
        for (i in s.indices) {
            when (state) {
                1 -> {
                    if (s[0].code == 39) { //character '
                        state = 4
                        length++
                    } else if (s[0] == '"') {
                        state = 2
                        length++
                    }
                }
                2 -> {
                    length++
                    if (s[i] == '"') {
                        state = 3
                    }
                }
                4 -> {
                    state = if (s[i].code == 39) { //character '
                        length = 0
                        -1
                    } else {
                        length++
                        5
                    }
                }
                5 -> {
                    state = if (s[i].code == 39) { //character '
                        length++
                        6
                    } else {
                        length = 0
                        -1
                    }
                }
            }
        }
        return length
    }

    fun isIdentifier(s: String): Int { // TODO: 3/23/2022 check for special characters
        var length = 0
        var state = 1
        for (i in s.indices) {
            when (state) {
                1 ->
                    if (s[0].isLetter()) {
                        state = 2
                        length++
                    }
                2 ->
                    state = if (s[i].isLetterOrDigit()) {
                        length++
                        2
                    } else 3
            }
        }
        return length
    }

    fun isNumericConstant(s: String): Int {
        var length = 0
        var state = 1
        for (i in s.indices) {
            when (state) {
                1 -> {
                    state = if (s[i].isDigit()) {
                        length++
                        1
                    } else if(!s[i].isLetter()){
                        3
                    } else {
                        length = 0
                        3
                    }
                }
            }
        }
        return length
    }

    private fun removeSingleLineComment(str: String): String {
        var output = str
        if (str.isNotEmpty() && str.contains("#")) {
            output = str.replace(str.substring(str.indexOf('#'), str.indexOf('\n')), "")
//            output = output.replace(" +", " ") // TODO: 3/23/2022 check why this need to be replaced
        }
        return output
    }

    private fun removeMultiLineComments(str: String): String {
        var output = str
        if (str.length > 2 && str.contains("##")) {
            val start = str.indexOf("##")
            val end = str.indexOf("##", start + 2)
            output = str.replace(str.substring(start, end + 2), "")
//            output = output.replace(" +", " ") // TODO: 3/23/2022 check why this need to be replaced

        }
        return output
    }

    private fun addTokenLexemePar(tokenLexeme: TokenLexeme?, wordsPath: String): Int {
        if (tokenLexeme != null) {
            tokenLexemePairList = tokenLexemePairList.plus(tokenLexeme)
            FileUtils.writeFile(wordsPath, tokenLexeme.toString())
            return if(lambdaChar == tokenLexeme.lexeme)
                tokenLexeme.token.length
            else
                tokenLexeme.lexeme.length
        }
        return 0
    }

    fun run(): List<TokenLexeme> {

        val wordsPath = inputFile.split(".")[0] + "-words.txt"
        val words = File(wordsPath)
        if (words.exists())
            words.delete()
        val symbolPath = inputFile.split(".")[0] + "-symbols.txt"
        val symbols = File(symbolPath)
        if (symbols.exists())
            symbols.delete()

        FileUtils.writeFile(wordsPath, "")
        FileUtils.writeFile(symbolPath, "")

        var inputFileString = FileUtils.readFile(inputFile)

        var line: String
        var tempString: String
        var lineNo = 0

        while (inputFileString.isNotEmpty()) {
            inputFileString = removeMultiLineComments(inputFileString)

            line = inputFileString.substring(0, inputFileString.indexOf('\n') + 1)
            lineNo++
            val end = inputFileString.indexOf('\n')
//            inputFileString = inputFileString.replaceFirst(Pattern.quote(inputFileString.substring(0, end + 1)), "")
            inputFileString = inputFileString.replace(inputFileString.substring(0, end + 1), "")
            line = removeSingleLineComment(line)

            while (line.isNotEmpty()) {
                tempString = line

                line = line.substring(addTokenLexemePar(isAssignmentOp(line), wordsPath)).trim()
                line = line.substring(addTokenLexemePar(isArithmeticOp(line), wordsPath)).trim()
                line = line.substring(addTokenLexemePar(isRelationalOp(line), wordsPath)).trim()
                line = line.substring(addTokenLexemePar(isKeyWord(line), wordsPath)).trim()
                line = line.substring(addTokenLexemePar(isDataType(line), wordsPath)).trim()

                val identifier = isIdentifier(line)
                if (identifier != 0) {
                    line =
                        line.substring(addTokenLexemePar(TokenLexeme("ID", line.substring(0, identifier)), wordsPath))
                            .trim()

                    if(!FileUtils.lookUp(symbolPath, tokenLexemePairList.last().lexeme)){
                        symbolList.plus(tokenLexemePairList.last().lexeme)
                        FileUtils.writeFile(symbolPath, tokenLexemePairList.last().lexeme.plus('\n'))
                    }
                }

                line = line.substring(addTokenLexemePar(isBracket(line), wordsPath)).trim()
                line = line.substring(addTokenLexemePar(isPunctuation(line), wordsPath)).trim()

                val literal = isLiteral(line)
                if(literal != 0){
                    var lt = line.substring(0, literal)
                    lt = if(lt.length > 3 || lt.length == 2){
                        lt.substring(1, lt.lastIndexOf('"'))
                    } else {
                        lt.substring(1,2)
                    }

                    line = line.substring(addTokenLexemePar(TokenLexeme("LITERAL", lt), wordsPath) + 2).trim()
                }

                val numeric = isNumericConstant(line)
                if(numeric != 0) {
                    line =
                        line.substring(addTokenLexemePar(TokenLexeme("NUMBER", line.substring(0, numeric)), wordsPath))
                            .trim()
                }

                if(tempString == line) {
                    var notSupported = ""
                    if (line.indexOf(' ') == -1) {
                        notSupported = line.substring(0)
                        line = ""
                    } else {
                        notSupported = line.substring(0, line.indexOf(' '))
                        line = line.substring(line.indexOf(' ') + 1)
                    }
                    println("\n Error at line: $lineNo, language not supported : $notSupported")
                }
            }
        }

        return tokenLexemePairList
    }
}

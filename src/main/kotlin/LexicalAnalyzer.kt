class LexicalAnalyzer {

//    private var index: Int = 0

    fun isPunctuation(s: String): Punctuation? {
        if (s.isNotEmpty()) {
            if (Punctuation.values().any { it.char == s[0] }) {
                return Punctuation.values().find { it.char == s[0] }
            }
        }
        return null
    }

    fun isBracket(s: String): BracketTypes? {
        if (s.isNotEmpty()) {
            if (BracketTypes.values().any { it.char == s[0] }) {
                return BracketTypes.values().find { it.char == s[0] }
            }
        }
        return null
    }

    fun isKeyWord(s: String): KeyWords? {
        if (s.isNotEmpty()) {
            if (s.length > 1 && KeyWords.values().any { it.string == s.substring(0, 2) }) {
                return KeyWords.values().find { it.string == s.substring(0, 2) }
            }
            if (s.length > 2 && KeyWords.values().any { it.string == s.substring(0, 3) }) {
                return KeyWords.values().find { it.string == s.substring(0, 3) }
            }
            if (s.length > 3 && KeyWords.values().any { it.string == s.substring(0, 4) }) {
                return KeyWords.values().find { it.string == s.substring(0, 4) }
            }
            if (s.length > 4 && KeyWords.values().any { it.string == s.substring(0, 5) }) {
                return KeyWords.values().find { it.string == s.substring(0, 5) }
            }
        }
        return null
    }

    fun isArithmeticOp(s: String): ArithmeticOperator? {
        if (s.isNotEmpty()) {
            if (ArithmeticOperator.values().any { it.char == s[0] }) {
                return ArithmeticOperator.values().find { it.char == s[0] }
            }
        }
        return null
    }

    fun isRelationalOp(s: String): RelationalOperator? {
        if (s.isNotEmpty()) {
            if (s.length > 1 && RelationalOperator.values().any { it.string == s.substring(0, 2) }) {
                return RelationalOperator.values().find { it.string == s.substring(0, 2) }
            }
            if (RelationalOperator.values().any { it.string == s.substring(0, 1) }) {
                return RelationalOperator.values().find { it.string == s.substring(0, 1) }
            }
        }
        return null
    }

    fun isDataType(s: String): DataTypes? {
        if (s.isNotEmpty()) {
            if (s.length > 3 && DataTypes.values().any { it.string == s.substring(0, 4) }) {
                return DataTypes.values().find { it.string == s.substring(0, 4) }
            }
            if (s.length > 4 && DataTypes.values().any { it.string == s.substring(0, 5) }) {
                return DataTypes.values().find { it.string == s.substring(0, 5) }
            }
        }
        return null
    }

    fun isLiteral(s: String): Literal? {
        var state = 1
        for (i in s.indices) {
            when (state) {
                1 -> {
                    if (s[0].code == 39) //character '
                        state = 4
                    else if (s[0] == '"') {
                        state = 2
                    }
                }
                2 -> {
                    if (s[i] == '"')
                        state = 3
                }
                4 -> {
                    state = 5
                }
                5 -> {
                    state = if (s[i].code == 39) //character '
                        6
                    else -1
                }
            }
        }
        return when (state) {
            3 -> Literal.STRING
            6 -> Literal.CHARACTER
            else -> null
        }
    }

    fun isIdentifier(s: String): Boolean { //TODO: check for special characters
        var state = 1
        for (i in s.indices) {
            when (state) {
                1 ->
                    if (s[0].isLetter()) {
                        state = 2
                    }
                2 ->
                    state = if (s[i].isLetterOrDigit()) 2 else 3
            }
        }
        return state != 1
    }

    fun isAssignmentOp(s: String): Boolean {
        var state = 1
        if (s.length > 1) {
            if (s.substring(0, 2).equals("<-", true)) {
                state = 2
            }
        }
        return state != 1
    }

    fun isNumericConstant(s: String): Boolean {
        var state = 1
        for (i in s.indices) {
            when (state) {
                1 -> {
                    state = if (s[0].isDigit()) {
                        2
                    } else
                        3
                }
                2 -> {
                    state = if (s[i].isDigit())
                        2
                    else
                        3
                }
            }
        }
        return state == 2
    }

    fun run() {

    }
}

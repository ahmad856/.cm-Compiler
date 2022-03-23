enum class ArithmeticOperator(val char: Char) {
    ADD('+'), SUBTRACT('-'),
    MULTIPLY('*'), DIVIDE('/')
}

enum class RelationalOperator(val string: String) {
    LESS_THEN("<"), LESS_THEN_EQUAL("<="),
    GREATER_THEN(">"), GREATER_THEN_EQUAL(">="),
    EQUAL("=="), NOT_EQUAL("<>")
}

enum class KeyWords(val string: String) {
    DEF("def"),
    IF("if"), ELSE("else"),
    WHILE("while"),
    RET("ret"),
    PRINT("print"), READ("read")
}

enum class DataTypes(val string: String) {
    INT("int "), CHAR("char ")
}

enum class BracketTypes(val char: Char) {
    LEFT_PARENTHESES('('), RIGHT_PARENTHESES(')'),
    LEFT_BRACE('{'), RIGHT_BRACE('}'),
    LEFT_SQUARE_BRACKET('['), RIGHT_SQUARE_BRACKET(']')
}

enum class Punctuation(val char: Char) {
    SEMI_COLON(';'), COMA(',')
}

enum class Literal {
    CHARACTER, STRING
}

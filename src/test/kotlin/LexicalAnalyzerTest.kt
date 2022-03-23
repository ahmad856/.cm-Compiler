import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class LexicalAnalyzerTest {

    private val analyzer: LexicalAnalyzer = LexicalAnalyzer()

    @TestFactory
    fun `return valid punctuations if exists in grammar`() =
        listOf(
            ";" to Punctuation.SEMI_COLON,
            "," to Punctuation.COMA,
            "" to null,
            "-" to null,
            "null" to null
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned Punctuation $expected"
            ) {
                val actual = analyzer.isPunctuation(input)
                assertEquals(expected, actual)
            }
        }

    @TestFactory
    fun `return valid brackets if exists in grammar`() =
        listOf(
            "(" to BracketTypes.LEFT_PARENTHESES,
            ")" to BracketTypes.RIGHT_PARENTHESES,
            "{" to BracketTypes.LEFT_BRACE,
            "}" to BracketTypes.RIGHT_BRACE,
            "[" to BracketTypes.LEFT_SQUARE_BRACKET,
            "]" to BracketTypes.RIGHT_SQUARE_BRACKET,
            "" to null,
            "*" to null,
            "null" to null
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned Bracket Type $expected"
            ) {
                val actual = analyzer.isBracket(input)
                assertEquals(expected, actual)
            }
        }

    @TestFactory
    fun `return valid keywords if exists in grammar`() =
        listOf(
            "def" to KeyWords.DEF,
            "if" to KeyWords.IF,
            "else" to KeyWords.ELSE,
            "while" to KeyWords.WHILE,
            "ret" to KeyWords.RET,
            "print" to KeyWords.PRINT,
            "read" to KeyWords.READ,
            "" to null,
            "test" to null,
            "null" to null
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned Keyword $expected"
            ) {
                val actual = analyzer.isKeyWord(input)
                assertEquals(expected, actual)
            }
        }

    @TestFactory
    fun `return Literals if exists in grammar`() =
        listOf(
            "\"def\"" to Literal.STRING,
            "\"\"" to Literal.STRING,
            "\"else\"" to Literal.STRING,
            "'a'" to Literal.CHARACTER,
            "'ab'" to null,
            "''" to null,
            "1" to null
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned Literal $expected"
            ) {
                val actual = analyzer.isLiteral(input)
                assertEquals(expected, actual)
            }
        }

    @TestFactory
    fun `check for numeric constants in grammar`() =
        listOf(
            "123" to true,
            "123a" to false,
            "a123" to false,
            "123a456" to false
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned Literal $expected"
            ) {
                val actual = analyzer.isNumericConstant(input)
                assertEquals(expected, actual)
            }
        }

    @TestFactory
    fun `check for identifier constants in grammar`() =
        listOf(
            "a123" to true,
            "123" to false,
            "a_123" to true,
            "123a456" to false
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned Literal $expected"
            ) {
                val actual = analyzer.isIdentifier(input)
                assertEquals(expected, actual)
            }
        }
}
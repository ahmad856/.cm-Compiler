import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class LexicalAnalyzerTest {

    private val analyzer: LexicalAnalyzer = LexicalAnalyzer()

    @TestFactory
    fun `return Literals if exists in grammar`() =
        listOf(
            "\"def\"" to 5,
            "\"\"" to 2,
            "\"else\"" to 6,
            "'a'" to 3,
            "'ab'" to 0,
            "''" to 0,
            "1" to 0
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned $expected"
            ) {
                val actual = analyzer.isLiteral(input)
                assertEquals(expected, actual)
            }
        }

    @TestFactory
    fun `check for numeric constants in grammar`() =
        listOf(
            "1" to 1,
            "123" to 3,
            "123a" to 0,
            "a123" to 0,
            "1(" to 1,
            "123a456" to 0
        ).map { (input, expected) ->
            dynamicTest(
                "for input \"$input\" returned $expected"
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
                "for input \"$input\" returned $expected"
            ) {
                val actual = analyzer.isIdentifier(input)
                assertEquals(expected, actual)
            }
        }
}
data class TokenLexeme(val token: String, val lexeme: String){
    override fun toString(): String {
        return "<'${token}',${lexeme}>  "
    }
}

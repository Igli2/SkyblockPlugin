package skyblock.utils.minion;

import org.bukkit.Material;

public class ProgramLexer {

    private String program;
    private int pos;
    private int line;
    private int column;

    public ProgramLexer(String program) {
        this.program = program;
        this.pos = 0;
        this.line = 0;
        this.column = 0;
    }

    public Token nextToken() {
        this.consumeWhitespaces();

        if(this.pos >= this.program.length()) return new Token(Token.TokenType.EOF, this.line, this.column, "EOF");

        int l = this.line, c = this.column;

        if(this.curr() == ';') {
            this.next();
            return new Token(Token.TokenType.SEPERATOR, l, c, ";");
        } else if(this.curr() == '!') {
            this.next();
            return new Token(Token.TokenType.NOT, l, c, "!");
        } else if(this.curr() == '{') {
            this.next();
            return new Token(Token.TokenType.PAR_OPEN, l, c, "{");
        } else if(this.curr() == '}') {
            this.next();
            return new Token(Token.TokenType.PAR_CLOSE, l, c, "}");
        } else if(Character.isDigit(this.curr()) && this.pos < this.program.length()) {
            String intStr = "";
            do {
                intStr += this.curr();
                this.next();
            } while(Character.isDigit(this.curr()) && this.pos < this.program.length());

            return new Token(Token.TokenType.INT, l, c, intStr);
        } else if((this.curr() == '_' || (Character.isAlphabetic(this.curr()) && Character.isLowerCase(this.curr()))) && this.pos < this.program.length()) {
            String identifier = "";
            do {
                identifier += this.curr();
                this.next();
            } while((this.curr() == '_' || (Character.isAlphabetic(this.curr()) && Character.isLowerCase(this.curr()))) && this.pos < this.program.length());

            return new Token(this.typeOfIdentifier(identifier.toLowerCase()), l, c, identifier);
        }

        this.next();
        return new Token(Token.TokenType.UNDEFINED, l, c, "UNDEFINED");
    }

    private char curr() {
        return this.program.charAt(this.pos);
    }

    private void next() {
        if(this.curr() == '\n') {
            this.line++;
            this.column = 0;
        } else {
            this.column++;
        }

        this.pos++;
    }

    private void consumeWhitespaces() {
        while(this.pos < this.program.length() && Character.isWhitespace(this.curr())) this.next();
    }

    private Token.TokenType typeOfIdentifier(String identifier) {
        if(identifier.equals("power")) {
            return Token.TokenType.POWER;
        } else if(identifier.equals("break")) {
            return Token.TokenType.BREAK;
        } else if(identifier.equals("select")) {
            return Token.TokenType.SELECT;
        } else if(identifier.equals("place")) {
            return Token.TokenType.PLACE;
        } else if(identifier.equals("use")) {
            return Token.TokenType.USE;
        } else if(identifier.equals("drop")) {
            return Token.TokenType.DROP;
        } else if(identifier.equals("throw")) {
            return Token.TokenType.THROW;
        } else if(identifier.equals("craft")) {
            return Token.TokenType.CRAFT;
        } else if(Material.getMaterial(identifier.toUpperCase()) != null) {
            return Token.TokenType.MATERIAL;
        } else {
            return Token.TokenType.UNDEFINED;
        }
    }
}

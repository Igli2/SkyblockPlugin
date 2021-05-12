package skyblock.utils.minion;

public class Token {

    public enum TokenType {
        POWER,
        BREAK,
        SELECT,
        PLACE,
        USE,
        DROP,
        THROW,
        MATERIAL,
        CRAFT,
        PAR_OPEN,
        PAR_CLOSE,
        INT,
        NOT,
        SEPERATOR,
        EOF,
        UNDEFINED
    }

    private TokenType type;
    private int line;
    private int column;
    private String identifier;

    public Token(TokenType type, int line, int column, String identifier) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.identifier = identifier;
    }

    public TokenType getType() {
        return this.type;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + this.type +
                ", line=" + this.line +
                ", column=" + this.column +
                ", identifier='" + this.identifier + '\'' +
                '}';
    }
}

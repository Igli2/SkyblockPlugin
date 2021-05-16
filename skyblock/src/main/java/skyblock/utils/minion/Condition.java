package skyblock.utils.minion;

public class Condition {

    private boolean negated;
    private Token.TokenType type;
    private String identifier;

    public Condition(boolean negated, Token.TokenType type, String identifier) {
        this.negated = negated;
        this.type = type;
        this.identifier = identifier;
    }

    public boolean isNegated() {
        return negated;
    }

    public Token.TokenType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "negated=" + negated +
                ", type=" + type +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}

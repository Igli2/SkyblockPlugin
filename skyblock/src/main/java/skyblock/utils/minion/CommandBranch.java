package skyblock.utils.minion;

public class CommandBranch extends Branch {

    private Token.TokenType type;
    private int arg;

    public CommandBranch(Token.TokenType type) {
        this.type = type;
        this.arg = -1;
    }

    public CommandBranch(Token.TokenType type, int arg) {
        this.type = type;
        this.arg = arg;
    }

    @Override
    public String toString() {
        return "Command{" + this.type.toString() + ", arg: " + this.arg + "}";
    }

    public Token.TokenType getType() {
        return type;
    }

    public int getArg() {
        return arg;
    }
}

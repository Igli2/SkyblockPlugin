package skyblock.utils.minion;

import java.util.ArrayList;

public class ProgramParser {

    private static final Token.TokenType[] COMMANDS = new Token.TokenType[] {
        Token.TokenType.BREAK,
        Token.TokenType.SELECT,
        Token.TokenType.PLACE,
        Token.TokenType.USE,
        Token.TokenType.DROP,
        Token.TokenType.THROW,
        Token.TokenType.CRAFT
    };

    private static final Token.TokenType[] PARAMETER_COMMANDS = new Token.TokenType[] {
        Token.TokenType.SELECT,
            Token.TokenType.DROP,
            Token.TokenType.THROW,
            Token.TokenType.CRAFT
    };

    private ProgramLexer lexer;

    private Token curr;

    public ProgramParser(ProgramLexer lexer) {
        this.lexer = lexer;
        this.curr = this.lexer.nextToken();
    }

    public Branch parse() throws Exception {
        Branch ast = this.parseSequence();
        this.expect(Token.TokenType.EOF);

        return ast;
    }

    private Branch parseSequence() throws Exception {
        SequenceBranch sequence = new SequenceBranch();
        while(true) {
            if(this.accept(new Token.TokenType[]{Token.TokenType.MATERIAL, Token.TokenType.POWER, Token.TokenType.NOT})) {
                sequence.addChild(this.parseConditional());
            } else if(this.accept(ProgramParser.COMMANDS)) {
                sequence.addChild(this.parseCommand());
            } else {
                break;
            }
        }

        return sequence;
    }

    private Branch parseConditional() throws Exception {
        ArrayList<Condition> conditions = new ArrayList<>();

        while(this.accept(new Token.TokenType[]{Token.TokenType.MATERIAL, Token.TokenType.POWER, Token.TokenType.NOT})) {
            conditions.add(this.parseCondition());

            if(this.accept(Token.TokenType.PAR_OPEN)) break;
            this.expect(Token.TokenType.SEPERATOR);
            this.advance();
        }

        this.expect(Token.TokenType.PAR_OPEN);
        this.advance();

        if(conditions.size() == 0) throw new Exception("Missing condition!");

        Branch sequence = this.parseSequence();

        this.expect(Token.TokenType.PAR_CLOSE);
        this.advance();
        this.expect(Token.TokenType.SEPERATOR);
        this.advance();

        Condition[] conditionsArr = new Condition[conditions.size()];
        conditionsArr = conditions.toArray(conditionsArr);

        return new ConditionalBranch(conditionsArr, sequence);
    }

    private Branch parseCommand() throws Exception {
        Branch command;
        if(this.accept(ProgramParser.PARAMETER_COMMANDS)) {
            Token.TokenType type = this.curr.getType();
            this.advance();
            this.expect(Token.TokenType.INT);
            command = new CommandBranch(type, Integer.parseInt(this.curr.getIdentifier()));
        } else {
            command = new CommandBranch(this.curr.getType());
        }

        this.advance();
        this.expect(Token.TokenType.SEPERATOR);
        this.advance();

        return command;
    }

    private Condition parseCondition() throws Exception {
        boolean negated = false;
        if(accept(Token.TokenType.NOT)) {
            this.advance();
            negated = true;
        }

        this.expect(new Token.TokenType[]{Token.TokenType.MATERIAL, Token.TokenType.POWER});
        Condition condition = new Condition(negated, this.curr.getType(), this.curr.getIdentifier());
        this.advance();

        return condition;
    }

    private void advance() {
        this.curr = this.lexer.nextToken();
    }

    private boolean accept(Token.TokenType[] types) {
        for(Token.TokenType type : types) {
            if(this.accept(type)) return true;
        }

        return false;
    }

    private boolean accept(Token.TokenType type) {
        return this.curr.getType() == type;
    }

    private void expect(Token.TokenType type) throws Exception {
        if(this.curr.getType() != type) throw new Exception("Unexpected Token '" + this.curr.getType().toString() + "' at l:" + this.curr.getLine() + ",c:" + this.curr.getColumn() + " while parsing!");
    }

    private void expect(Token.TokenType[] types) throws Exception {
        for(Token.TokenType type : types) {
            if(this.accept(type)) return;
        }

        throw new Exception("Unexpected Token '" + this.curr.getType().toString() + "' at l:" + this.curr.getLine() + ",c:" + this.curr.getColumn() + " while parsing!");
    }

}

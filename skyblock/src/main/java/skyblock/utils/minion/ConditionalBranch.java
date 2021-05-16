package skyblock.utils.minion;

public class ConditionalBranch extends Branch {

    private Condition[] conditions;
    private Branch child;

    public ConditionalBranch(Condition[] conditions, Branch child) {
        this.conditions = conditions;
        this.child = child;
    }

    @Override
    public String toString() {
        String ret = "Conditional{Condition{";

        for(int c = 0; c < this.conditions.length; c++) {
            if(c != 0) ret += ", ";
            if(this.conditions[c].isNegated()) ret += "!";
            ret += this.conditions[c].getIdentifier();
        }

        return ret += "}, " + this.child.toString() + "}";
    }
}

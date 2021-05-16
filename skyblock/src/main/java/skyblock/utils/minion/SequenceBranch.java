package skyblock.utils.minion;

import java.util.ArrayList;

public class SequenceBranch extends Branch {

    private ArrayList<Branch> children;

    public SequenceBranch() {
        this.children = new ArrayList<>();
    }

    public void addChild(Branch child) {
        this.children.add(child);
    }

    public int size() {
        return this.children.size();
    }

    @Override
    public String toString() {
        String ret = "Sequence{";

        for(int b = 0; b < this.children.size(); b++) {
            if(b != 0) ret += ", ";
            ret += this.children.get(b).toString();
        }

        return ret + "}";
    }
}

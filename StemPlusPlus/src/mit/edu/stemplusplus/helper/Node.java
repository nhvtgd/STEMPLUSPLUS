package mit.edu.stemplusplus.helper;

/**
 *  Node.java - Represents a step of a project
 *  @author Ashley Smith
 */
//FIXME needs work, as does history (I will take care of it later -ashley)
import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> children;
    private Step associatedStep;
    public Node(Step s) {
        children = new ArrayList<Node>();
        associatedStep = s;
    }
    public Step getStep() { return associatedStep; }
    public List<Node> getChildren() { return children; }
    public void addChild(Node child) { children.add(child); }
    public void addChildren(List<Node> children) { this.children.addAll(children); }
}

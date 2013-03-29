package mit.edu.stemplusplus.helper;

/**
 *  History.java - Represents the history of a project in the form of a list of nodes
 *  @author Ashley Smith
 */
//FIXME needs work, as does node (I will take care of it later -ashley)
import java.util.ArrayList;
import java.util.List;


public class History {
    private List<Node> nodes;
    public History(Project p) {
        List<Step> steps = p.getSteps();
        nodes = new ArrayList<Node>();
        for(Step s : steps) nodes.add(createNodeAndFillHistory(s));
    }
    
    public Node createNodeAndFillHistory(Step s) {
        Node n = new Node(s);
        for (Step sChild : s.getPastVersions()){
            Node child = createNodeAndFillHistory(sChild);
            n.addChild(child);
        }
        return n;
    }
    public List<Node> getStartNode() { return nodes; }
}

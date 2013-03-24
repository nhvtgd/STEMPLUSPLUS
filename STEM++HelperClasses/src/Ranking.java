
public class Ranking {
    private int rank;
    private Project associatedProject;
    private User associatedUser;
    public Ranking(Project p, User u) {
        rank = 0; //FIXME not sure what ranking they should start with
        associatedProject = p;
        associatedUser = u;
    }
    public Ranking(User u){
        rank = 0; //FIXME not sure what ranking they should start with
        associatedProject = null;
        associatedUser = u;
    }
    public void setRank(int r) { rank = r; }
    public void incrementRank() { rank++; }
    public void decrementRank() { rank--; }
    public int getRank() { return rank; }
    public Project getAssociatedProject() { return associatedProject; }
    public User getAssociatedUser() { return associatedUser; }
}

package CommitCollector;

public interface Project {
    public void fetch(String workspaceDirectory);
    public void extractMetadata();
}
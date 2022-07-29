package CommitCollector;

public class GitHubProject implements Project {
    GitHubProject()
    {
    }

    public void fetch(String workspaceDirectory)
    {
        ProcessBuilder fetcher = new ProcessBuilder("git", "clone", this.projectLink)
            .directory(new File(workspaceDirectory));
        Process p = fetcher.start();

        p.waitFor();
    }

    public void extractMetadata()
    {
        ProcessBuilder checkoutPB = new ProcessBuilder("git",
            "-C", this.projectDirectory,
            "blame", "-C", "-C", "-f", "-l", "-L", String.format("%s,%s", this.faultyLineBlame, this.faultyLineBlame),
            this.faultyPath);
        
    }
}
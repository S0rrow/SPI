package CommitCollector;

import org.eclipse.jgit.api.*;

public class Defects4JProject implements Project {
    Defects4JProject(String project)
    {
        this.project = project;
    }

    public void fetch(String workspaceDirectory)
    {
        ProcessBuilder fetcher = new ProcessBuilder("defects4j", "checkout",
            "-p", project, "-v", String.format("%db", this.identifier),
            "-w", project)
            .directory(new File(workspaceDirectory));
        Process p = fetcher.start();

        p.waitFor();
    }

    public void extractMetadata()
    {
        /*
            # os.system("git -C "+target_dir+"/"+project+" checkout "+buggy_sha)
            print("git -C "+target_dir+"/"+project+" blame -C -C -f -l -L "+str(blame_faulty_line)+","+str(blame_faulty_line)+" "+faulty_file_path)
            git_stream = os.popen("git -C "+target_dir+"/"+project+" blame -C -C -f -l -L "+str(blame_faulty_line)+","+str(blame_faulty_line)+" "+faulty_file_path)
            foo = str(git_stream.read()).split(' ')
            FIC_sha = foo[0]

            # 이렇게 하면 오히려 실제 고치는 장소랑 바뀌어버린다... 패스가 바뀌는게 문제
            # faulty_file_path = foo[1]

            git_stream = os.popen("git -C "+target_dir+"/"+project+" rev-parse "+FIC_sha+"~1")
            BFIC_sha = str(git_stream.read()).split('\n')[0]       
        */
        ProcessBuilder checkoutPB = new ProcessBuilder("git",
            "-C", this.projectDirectory,
            "blame", "-C", "-C", "-f", "-l", "-L", String.format("%s,%s", this.faultyLineBlame, this.faultyLineBlame),
            this.faultyPath);
        
    }
}
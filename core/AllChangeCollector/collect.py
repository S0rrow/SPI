import csv
import getopt
import os
import sys
import numpy as np
from subprocess import call

def build_and_run(file, commitID, projectName, gitDirectory, argtxt):
    pwd = os.getcwd()
    # due to gradle issue, we must use java version 11 or higher
    init_java_home = "JAVA_HOME=\"/usr/lib/jvm/java-11-openjdk-amd64\""
    call(f"./gradlew clean; ./gradlew build\n", env = "JAVA_HOME=/usr/lib/jv/java-11-openjdk-amd64", shell = True)
    # clean build ACC
    call(f"cd app/build/distributions/; unzip app.zip; cd {pwd}; cp app/build/distributions/app/bin/app pool/app", shell = True)
    # unzip and copy the executable under ACC
    call(f"echo {file} {commitID} {projectName} {gitDirectory}> {argtxt}", shell = True)
    # create arguments as text file under ACC
    call(f"cd ~/leshen/APR/AllChangeCollector/pool\n./app -l {pwd}/{argtxt}", shell = True)
    # execute ACC according to arguments text file
    return 0


def main(argv):
    try:
        opts, args = getopt.getopt(argv[1:], "h:f:c:p:g:", ["help", "file", "commitID", "project", "gitDirectory"])
    except getopt.GetoptError as err:
        print(err)
        sys.exit(2)
    file = ''
    commitId = ''
    projectName = ''
    gitDirectory = ''
    for o, a in opts:
        if o in ("-H", "--help") or o in ("-h", "--hash"):
            print("")
            sys.exit()
        elif o in ("-f", "--file"):
            file = a
        elif o in ("-c", "--commitID"):
            commitId = a
        elif o in ("-p", "--project"):
            projectName = a
        elif o in ("-g", "--gitDirectory"):
            gitDirectory = a
        else:
            assert False, "unhandled option"
    build_and_run(file, commitId, projectName, gitDirectory, "collection.txt")
    

if __name__ == '__main__':
    main(sys.argv)
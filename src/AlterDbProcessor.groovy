import java.util.regex.Pattern

import static Utils.ALTER_DB_FILE_PATTERN
import static Utils.getArchiveFolder
import static Utils.getBaseFolder
import static Utils.isVersionInRange

class AlterDbProcessor {


    public static String prepareAggregatedFile(String projectDir, String versionFrom) {
        TreeMap<W2moVersion, File> fileMap = collectFiles(getBaseFolder(projectDir), getArchiveFolder(projectDir), versionFrom, ALTER_DB_FILE_PATTERN)
        fileMap.each { entry ->
            println("${entry.key}; File name: ${entry.value.getName()}")
        }
    }

    static TreeMap<W2moVersion, File> collectFiles(File baseFolder, File archiveFolder, String from, Pattern versionRegex) {
        def filesMap = [:] as TreeMap
        if (from != null && from.length() > 0) {
            W2moVersion versionFrom = new W2moVersion(from)
            baseFolder.eachFileMatch(versionRegex, { file ->
                def version = new W2moVersion(file =~ versionRegex)
                if (isVersionInRange(versionFrom, version)) {
                    filesMap.put(version, file)
                }
            })
            if (versionFrom < (filesMap.keySet().min() as W2moVersion)) {
                archiveFolder.eachFileMatch(versionRegex, { file ->
                    def version = new W2moVersion(file =~ versionRegex)
                    if (isVersionInRange(versionFrom, version)) {
                        filesMap.put(version, file)
                    }
                })
            }
        } else {
            baseFolder.eachFileMatch(versionRegex, { file ->
                filesMap.put(new W2moVersion(file =~ versionRegex), file)
            })
        }
        return filesMap
    }

}

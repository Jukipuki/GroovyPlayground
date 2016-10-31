
class PrepareAlterDbFiles {

    String prepareAggregatedFile(String projectDir, String versionFrom) {
        def versionRegex = "(\\d+)\\.+(\\d+)+(\\.(\\d+))*"
        def baseFolder = new File("${projectDir}\\alter-db")
        def archiveFolder = new File("${projectDir}\\alter-db-arch")
        def filesMap = [:]
        if (versionFrom == null || versionFrom.length() == 0) {
            baseFolder.eachFileMatch("alter-db-${versionRegex}.sql", { f ->
                println(f.getName() =~ versionRegex)
            })
        }
    }

}

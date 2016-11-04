import groovy.sql.Sql

import static ConnectionPropertiesKeys.DATABASE
import static ConnectionPropertiesKeys.getSql
import static ConnectionPropertiesKeys.getSql3d
import static Utils.ALTER_DB_FILE_PATTERN
import static Utils.getArchiveFolder
import static Utils.getBaseFolder
import static Utils.isVersionInRange
import static Utils.loadProperties
import static Utils.testDbConnection

class AlterDbProcessor {

    String projectDir
    Properties metaFile
    Properties connectionProperties
    Sql sql, sql3d

    AlterDbProcessor(String projectDir, String connectionPropertiesPath) {
        initialize(projectDir, connectionPropertiesPath)
        metaFile.getProperty('latest-execution-version')
    }

    private void initialize(String projectDir, String connectionPropertiesPath) {
        this.projectDir = projectDir
        metaFile = loadProperties(getBaseFolder(projectDir).getAbsolutePath(), true)
        connectionProperties = loadProperties(connectionPropertiesPath, false)
        sql = getSql(connectionProperties)
        sql3d = getSql3d(connectionProperties)
        testConnection()
    }

    void testConnection() {
        /**
         * Test main database
         */
        testDbConnection(sql, connectionProperties.getProperty(DATABASE.getKey()))
        /**
         * Test 3D models database
         */
        testDbConnection(sql3d, connectionProperties.getProperty(DATABASE.get3dKey()))
    }

    public static String prepareAggregatedFile(String projectDir, String versionFrom) {
        TreeMap<W2moVersion, File> fileMap = collectFiles(getBaseFolder(projectDir), getArchiveFolder(projectDir), versionFrom)
        fileMap.each { entry ->
            println("${entry.key}; File name: ${entry.value.getName()}")
        }
    }

    static TreeMap<W2moVersion, File> collectFiles(String projectDir, String from) {
        TreeMap filesMap = [:] as TreeMap
        File baseFolder = getBaseFolder(projectDir)
        File archiveFolder = getArchiveFolder(projectDir)
        /**
         * Check if original version {@code from} is set
         * Otherwise - get all files from base folder
         */
        if (from) {
            W2moVersion versionFrom = new W2moVersion(from)
            /**
             * Collect files from base folder
             */
            collectFilesPattern(baseFolder, versionFrom, filesMap)
            /**
             * Check if {@code versionFrom} is already collected
             * Otherwise - collect files from archive folder
             */
            if (versionFrom < (filesMap.keySet().min() as W2moVersion)) {
                collectFilesPattern(archiveFolder, versionFrom, filesMap)
            }
        } else {
            collectFilesPattern(baseFolder, filesMap)
        }
        return filesMap
    }

    static void collectFilesPattern(File folder, TreeMap container) {
        collectFilesPattern(folder, null, container)
    }

    static void collectFilesPattern(File folder, W2moVersion from, TreeMap container) {
        folder.eachFileMatch(ALTER_DB_FILE_PATTERN, { file ->
            def version = new W2moVersion(file =~ ALTER_DB_FILE_PATTERN)
            if (!from || isVersionInRange(from, version)) {
                container.put(version, file)
            }
        })
    }

}

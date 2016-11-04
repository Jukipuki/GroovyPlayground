import groovy.sql.Sql

import java.nio.file.attribute.UserDefinedFileAttributeView
import java.security.MessageDigest
import java.sql.SQLException
import java.util.regex.Pattern

import static java.nio.file.Files.*
import static java.nio.file.Paths.get
import static java.security.MessageDigest.getInstance

class Utils {

    public static final Pattern ALTER_DB_FILE_PATTERN = ~/alter-db-(\d+)\.(\d+)\.?(\d+)?\.sql/

    static final String W2MO_VERSION_FILE_ATTRIBUTE_NAME = "user:w2mo-version"

    public static void markFiles(String projectDir) {
        if (isSupportsUserDefinedFileAttributeView()) {
            getBaseFolder(projectDir).eachFileMatch(ALTER_DB_FILE_PATTERN, { file ->
                setAttribute(get(file.getPath()), W2MO_VERSION_FILE_ATTRIBUTE_NAME, new W2moVersion(file =~ ALTER_DB_FILE_PATTERN).toString().getBytes("UTF-8"))
            })
        }
    }

    public static W2moVersion getFileVersion(File file) {
        return new W2moVersion(new String(getAttribute(get(file.getPath()), W2MO_VERSION_FILE_ATTRIBUTE_NAME) as byte[]))
    }

    public static boolean isSupportsUserDefinedFileAttributeView() {
        return getFileStore(get("")).supportsFileAttributeView(UserDefinedFileAttributeView.class)
    }

    public static File getBaseFolder(String projectDir) {
        return new File("${projectDir}\\alter-db")
    }

    public static String jsonToSql(String json) {
        GsonBas
    }

    public static Properties loadProperties(String path, boolean createIfMissing) {
        Properties properties = new Properties()
        File file = new File(path)
        if (!file.exists()) {
            if (createIfMissing) {
                file.createNewFile()
            } else {
                throw new FileNotFoundException("File '${path}' not found")
            }
        }
        properties.load(file.newDataInputStream())
        return properties
    }

    public static BigInteger md5HexInt(String s) {
        MessageDigest md = getInstance("MD5")
        byte[] digest = md.digest(s.getBytes("UTF-8"))
        return new BigInteger(1, digest)
    }

    public static String md5HexString(String s) {
        return md5HexInt(s).toString(16)
    }

    static File getArchiveFolder(String projectDir) {
        return new File("${projectDir}\\alter-db\\alter-db-arch")
    }

    static boolean isVersionInRange(W2moVersion originalVersion, W2moVersion targetVersion) {
        assert originalVersion != null : "Original version cannot be null"
        assert targetVersion != null : "Target version cannot be null"
        return targetVersion >= originalVersion
    }

    static void testDbConnection(Sql sql, String targetDb) {
        try {
            def selectedDb = sql.firstRow("SELECT DATABASE()")[0]
            assert selectedDb == targetDb: "Wrong database selected. Expected - '${targetDb}', actual - '${selectedDb}'"
            println("Connection to '${selectedDb}' successfully acquired")
        } catch (SQLException e) {
            throw new SQLException("Unable to connect to the database (${targetDb}). Please, check connection properties", e)
        }
    }

}

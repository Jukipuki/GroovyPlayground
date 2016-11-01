import java.nio.ByteBuffer
import java.nio.file.attribute.UserDefinedFileAttributeView
import java.util.regex.Pattern

import static java.nio.file.Files.*
import static java.nio.file.Paths.get

class Utils {

    public static Pattern ALTER_DB_FILE_PATTERN = ~/alter-db-(\d+)\.(\d+)\.?(\d+)?\.sql/

    static String W2MO_VERSION_FILE_ATTRIBUTE_NAME = "user:w2mo-version"

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

    static File getArchiveFolder(String projectDir) {
        return new File("${projectDir}\\alter-db\\alter-db-arch")
    }

    static boolean isVersionInRange(W2moVersion versionFrom, W2moVersion targetVersion) {
        return versionFrom != null && targetVersion >= versionFrom
    }

}

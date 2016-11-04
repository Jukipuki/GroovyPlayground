import static Utils.md5HexInt
import static Utils.md5HexString

/*def proc = new AlterDbProcessor()
proc.prepareAggregatedFile("c:\\Work\\Trunk\\mysql", null)
println()
proc.prepareAggregatedFile("c:\\Work\\Trunk\\mysql", "6.15")
println()
proc.prepareAggregatedFile("c:\\Work\\Trunk\\mysql", "4.1")*/

/*markFiles("c:\\Work\\Trunk\\mysql")
getBaseFolder("c:\\Work\\Trunk\\mysql").eachFileMatch(~/alter-db-(\d+)\.(\d+)\.?(\d+)?\.sql/, { file ->
    println("Attribute version: ${getFileVersion(file)}; File name: ${file.getName()}")
})*/

/*
def v1 = new W2moVersion("5.16")
assert v1.getMajor() == 5 && v1.getMinor() == 16
def v2 = new W2moVersion("6.14.9")
assert v2.getMajor() == 6 && v2.getMinor() == 14 && v2.getPatch() == 9

def map = [:] as TreeMap
map.put(new W2moVersion("5.12"), "alter-db-file")
map.put(new W2moVersion("5.9"), "alter-db-file")
map.put(new W2moVersion("6.13"), "alter-db-file")
map.put(new W2moVersion("6.13.12"), "alter-db-file")
map.put(new W2moVersion("4.5"), "alter-db-file")
map.put(new W2moVersion("5.2"), "alter-db-file")
map.put(new W2moVersion("6.17"), "alter-db-file")
println(map)

new AlterDbProcessor("C:\\Trunk\\projects\\w2mo-webapp/conf/connection.properties").testConnection()*/

String s1 = "CREATE TABLE IF NOT EXISTS sys_build_parameters (\n" +
        "\t`version` VARCHAR(10) NOT NULL DEFAULT -1 COMMENT 'Version in XX.YY.ZZ format. If version equals -1 - parameter is not version related',\n" +
        "\tparameter VARCHAR(256) NOT NULL COMMENT 'Parameter name',\n" +
        "\t`value` VARCHAR(21845) NOT NULL COMMENT 'Parameter value. To use it as a key, fixed length should be defined. Take care during insert and remember this during retrieve',\n" +
        "\tPRIMARY KEY (`version`, parameter),\n" +
        "\tKEY(`value`)\n" +
        ") ENGINE MYISAM;//"
String s2 = "CREATE TABLE IF NOT EXISTS sys_build_parameters (\n" +
        "\t`version` VARCHAR(10) NOT NULL DEFAULT -1 COMMENT 'Version in XX.YY.ZZ format. If version equals -1 - parameter is not version related',\n" +
        "\tparameter VARCHAR(256) NOT NULL COMMENT 'Parameter name',\n" +
        "\t`value` VARCHAR(21845) NOT NULL COMMENT 'Parameter value. To use it as a key, fixed length should be defined. Take care during insert and remember this during retrieve',\n" +
        "\tPRIMARY KEY (`version`, parameter),\n" +
        "\tKEY(`value`)\n" +
        ") ENGINE MYISAM;//"
println("String length: ${s1.length()}")
println("String hex: ${md5HexString(s1)}")
println("Integer hex: ${md5HexInt(s1)}")
assert md5HexString(s1) == md5HexString(s2)


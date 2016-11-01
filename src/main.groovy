import static Utils.getBaseFolder
import static Utils.getFileVersion
import static Utils.markFiles

/*def proc = new AlterDbProcessor()
proc.prepareAggregatedFile("c:\\Work\\Trunk\\mysql", null)
println()
proc.prepareAggregatedFile("c:\\Work\\Trunk\\mysql", "6.15")
println()
proc.prepareAggregatedFile("c:\\Work\\Trunk\\mysql", "4.1")*/

markFiles("c:\\Work\\Trunk\\mysql")
getBaseFolder("c:\\Work\\Trunk\\mysql").eachFileMatch(~/alter-db-(\d+)\.(\d+)\.?(\d+)?\.sql/, { file ->
    println("Attribute version: ${getFileVersion(file)}; File name: ${file.getName()}")
})

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
println(map)*/

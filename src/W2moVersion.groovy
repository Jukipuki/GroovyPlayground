import java.util.regex.Matcher

import static java.lang.Integer.compare

class W2moVersion implements Comparable<W2moVersion> {

    private def versionRegex = ~/(\d+)\.(\d+)\.?(\d+)?/

    private int major, minor, patch

    W2moVersion(String version) {
        def matcher = version =~ versionRegex
        assert matcher.matches(): "Invalid version format - '${version}'"
        parseVersion(matcher)
    }

    W2moVersion(Matcher tableNameMatcher) {
        parseVersion(tableNameMatcher)
    }

    W2moVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    int getMajorVersion() {
        return major;
    }

    int getMinorVersion() {
        return minor
    }

    int getPatchVersion() {
        return patch
    }

    @Override
    String toString() {
        return patch == 0 ? "${major}.${minor}" : "${major}.${minor}.${patch}"
    }

    @Override
    int compareTo(W2moVersion o) {
        int majorCompare = compare(this.major, o.major)
        if (majorCompare != 0) {
            return majorCompare
        }
        int minorCompare = compare(this.minor, o.minor)
        if (minorCompare != 0) {
            return minorCompare
        }
        return compare(this.patch, o.patch)
    }

    private void parseVersion(Matcher matcher) {
        def groups = matcher[0] as ArrayList
        this.major = groups.get(1) as int;
        this.minor = groups.get(2) as int;
        this.patch = (groups.size() == 4 && groups.get(3) != null ? groups.get(3) : 0) as int;
    }

}

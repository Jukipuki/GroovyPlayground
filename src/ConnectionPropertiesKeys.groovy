import groovy.sql.Sql

import static groovy.sql.Sql.newInstance

enum ConnectionPropertiesKeys {

    DRIVER('driver'),
    URL('url'),
    DATABASE('database'),
    USER('user'),
    PASSWORD('password'),
    URL_SUFFIX('url-suffix'),
    MAX_CONNECTIONS('max-connections')

    final String key;
    final String key3d;
    String prefix3d = '3d-models-'

    ConnectionPropertiesKeys(String key) {
        this.key = key
        this.key3d = "${prefix3d}${key}"
    }

    String getKey() {
        return key
    }

    String get3dKey() {
        return key3d
    }

    public static Sql getSql(Properties p) {
        return newInstance(getUrl(p), p.getProperty(USER.key), p.getProperty(PASSWORD.key), p.getProperty(DRIVER.key))
    }

    public static Sql getSql3d(Properties p) {
        return newInstance(getUrl3d(p), p.getProperty(USER.key3d), p.getProperty(PASSWORD.key3d), p.getProperty(DRIVER.key3d))
    }

    static String getUrl(Properties p) {
        return "${p.getProperty(URL.key)}${p.getProperty(DATABASE.key)}?${p.getProperty(URL_SUFFIX.key)}"
    }

    static String getUrl3d(Properties p) {
        return "${p.getProperty(URL.key3d)}${p.getProperty(DATABASE.key3d)}?${p.getProperty(URL_SUFFIX.key3d)}"
    }

}

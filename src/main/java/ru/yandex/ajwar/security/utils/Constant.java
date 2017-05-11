package ru.yandex.ajwar.security.utils;

import java.io.File;
import java.net.InetAddress;

/**
 * Created by Ajwar on 28.04.2017.
 */
public final class Constant {
    public static final int PORT=5505;
    public static final String HOST= InetAddress.getLoopbackAddress().getHostAddress();
    public static final String SCHEMA="HTTP";
    public static final String FS= File.separator;
    public static final String NAME_REPOSITORY="repository.prop";
    public static final String NOT_CONNECTION="No connection";
    private Constant() {
    }
}

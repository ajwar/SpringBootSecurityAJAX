package ru.yandex.ajwar.security.model;

/**
 * Created by Ajwar on 27.04.2017.
 */
public class ServerInfo {
    private String port;
    private String name;
    private String station;
    private String index;
    private String adminPort;
    private String version;
    private String count;
    private String memory;

    public ServerInfo() {
    }

    public ServerInfo(String port, String name, String station, String index, String adminPort, String version, String count, String memory) {
        this.port = port;
        this.name = name;
        this.station = station;
        this.index = index;
        this.adminPort = adminPort;
        this.version = version;
        this.count = count;
        this.memory = memory;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(String adminPort) {
        this.adminPort = adminPort;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "port='" + port + '\'' +
                ", name='" + name + '\'' +
                ", station='" + station + '\'' +
                ", index='" + index + '\'' +
                ", adminPort='" + adminPort + '\'' +
                ", version='" + version + '\'' +
                ", count='" + count + '\'' +
                ", memory='" + memory + '\'' +
                '}';
    }
}

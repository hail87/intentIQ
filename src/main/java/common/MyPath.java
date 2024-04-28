package common;

public enum MyPath {

    RESOURCES_PATH ("src/resources/");

    private final String path;

    MyPath(String path) {
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}

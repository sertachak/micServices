package com.sha.rabbitdemo.cache;

class DataObject {
    private final String data;

    private DataObject(String data) {
        this.data = data;
    }

    private static int objectCounter = 0;
    // standard constructors/getters

    public static DataObject get(String data) {
        objectCounter++;
        return new DataObject(data);
    }

    public String getData() {
        return data;
    }
}

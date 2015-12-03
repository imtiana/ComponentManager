package com.java.packagemanager;

/**
 * Created by Tiana Im on 2015-12-02.
 */
public class ComponentExceptions {
    public static class NonexistentComponentNameException extends Exception {
        public NonexistentComponentNameException(String msg) {
            super(msg);
        }
    }

    public static class ExistingDependencyException extends Exception {
        public ExistingDependencyException(String msg) {
            super(msg);
        }
    }
}

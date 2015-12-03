package com.java.packagemanager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tiana Im on 2015-12-02.
 */
public class Component {
    private String name;
    private boolean isInstalled;
    private Set<Component> dependencySet;
    private Set<Component> supportSet;

    public Component(String name) {
        this.name = name;
        isInstalled = false;
        dependencySet = new HashSet<>();
        supportSet = new HashSet<>();
    }

    public void changeName(String newName) {
        name = newName;
        return;
    }

    public void Install() {
        if (!isInstalled) {
            isInstalled = true;
        }
    }

    public void Remove() {
        // TO DO
    }

    @Override
    public String toString() {
        return "Name: '" + this.name +
                "', Installed: '" + this.isInstalled +
                "', Depends on: '" + this.dependencySet.toString() +
                "', Supports: '" + this.supportSet.toString() + "'";
    }

}

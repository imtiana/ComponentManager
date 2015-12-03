package com.java.packagemanager;

import java.util.*;

/**
 * Created by Tiana Im on 2015-12-02.
 */
public class ComponentManager {
    private Map<String, Component> allComponents;
    private Set<String> installedComp;
    private Map<String, Set<String>> dependencyMap;
    private Map<String, Set<String>> supportMap;


    public ComponentManager() {
        allComponents = new HashMap<>();
        installedComp = new HashSet<>();
        dependencyMap = new HashMap<>();
        supportMap = new HashMap<>();
    }

    public void createComponent(String cname) throws ComponentExceptions.ComponentAlreadyExistsException {
        if (allComponents.containsKey(cname)) {
            throw new ComponentExceptions.ComponentAlreadyExistsException("Component " + cname + " already exists.");
        }
        else {
            Component newComponent = new Component(cname);
            allComponents.put(cname, newComponent);
        }
    }

    public void makeDepenency (String comp, ArrayList<String> depComps) {
        populateDependencyHelper(comp, depComps);
        populateSupportHelper(comp, depComps);
    }

    private void populateDependencyHelper(String comp, ArrayList<String> depComps) {
        if (!dependencyMap.containsKey(comp)) {
            Set<String> dep = new HashSet<>();
            for (String d : depComps) {
                dep.add(d);
            }
            dependencyMap.put(comp, dep);

            for (String d : depComps) {
                if (!dependencyMap.containsKey(d)) {
                    dependencyMap.put(d, new HashSet<>());
                }
            }
        }
        else { // update dependency for existing components
            for (String d : depComps) {
                dependencyMap.get(comp).add(d);

                if (!dependencyMap.containsKey(d)) {
                    dependencyMap.put(d, new HashSet<>());
                }
            }
        }
    }

    private void populateSupportHelper(String comp, ArrayList<String> depComps) {
        for (String d : depComps) {
            if (!supportMap.containsKey(d)) {
                Set<String> supports = new HashSet<>();
                supports.add(comp);
                supportMap.put(d, supports);
            }
            else {
                supportMap.get(d).add(comp);
            }
        }
    }

    public void installComponent(String comp) {
        System.out.println("INSTALL " + comp);

        if (installedComp.contains(comp)) {
            System.out.println(comp + " is already installed.");
            return;
        }

        // single new component such as foo
        if (!dependencyMap.containsKey(comp)) {
            makeDepenency(comp, new ArrayList<>());
            installSingleComponent(comp);
        }
        else {
            installComponentHelper(comp);
        }
    }

    private void installComponentHelper(String c) {
        Set<String> depencencies = dependencyMap.get(c);

        if (depencencies.size() == 0) {
            installSingleComponent(c);
            return;
        }
        else {
            for (String d : depencencies) {
                if (!installedComp.contains(d)) {
                    installComponentHelper(d);
                }
            }
        }
    }

    public void removeComponent(String comp) throws ComponentExceptions.NonexistentComponentNameException {
        System.out.println("REMOVE " + comp);

        if (!dependencyMap.containsKey(comp)) {
            throw new ComponentExceptions.NonexistentComponentNameException("Component name does not exist");
        }

        if (!installedComp.contains(comp)) {
            System.out.println(comp + " is already removed.");
            return;
        }

        removeComponentHelper(comp);
    }

    private void removeComponentHelper(String c) {
        Set<String> supported = supportMap.get(c);

        if (supported.size() == 0) {
            removeSingleComponent(c);
            return;
        }
        else {
            for (String s : supported) {
                // to do: need to handle case where you CAN recursively remove all unconnected components

                if (supportMap.get(c).size() != 0) {
                    System.out.println(c + " is still needed");
                }
            }
        }
    }

    private void installSingleComponent(String c) {
        installedComp.add(c);
        System.out.println("   Installing " + c);
    }

    private void removeSingleComponent(String c) {
        installedComp.remove(c);
        dependencyMap.remove(c);
        supportMap.remove(c);
        System.out.println("   Removing " + c);
    }

    public void listInstalledComponents() {
        System.out.println("LIST");

        for (String ic : installedComp) {
            System.out.println(ic);
        }
    }

}

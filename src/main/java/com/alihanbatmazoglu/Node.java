package com.alihanbatmazoglu;


import java.util.List;

public class Node extends Thread {
    private String name;
    public List<Node> dependencies;

    public Node(String name, List<Node> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
    }


private void waitForPrerequisites() throws InterruptedException {
    if (dependencies.size() > 0) {
        String waitingFor = String.format("Node %s is waiting for ", name);
        int count = 0;
        for (Node node : dependencies) {
            count++;
            waitingFor += node.name + (count == dependencies.size() ? "" : ",");
        }

        System.out.println(waitingFor);

        Node[] threads = dependencies.toArray(new Node[dependencies.size()]);

        int index = 0;
        while (count > 0) {
            Node node = threads[index];
            if (node.isAlive()) {
                Thread.sleep((long) (Math.random() * 2000));
            } else {
                count--;
                index++;
            }
        }
    }
}


    @Override
    public void run() {
        try {
            waitForPrerequisites();
            System.out.println(String.format("Node %s is being started", name));
            Thread.sleep((long) (Math.random() * 2000));
            System.out.println(String.format("Node %s is completed", name));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void perform() {
       this.start();
    }
}


//    public void run() {
//        System.out.println("Node " + name + " is being started");
//        perform();
//        for (Node dependency : dependencies) {
//            System.out.println("Node " + name + " is waiting for " + dependency.name);
//            try {
//                dependency.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("Node " + name + " is completed");
//    }



















////////////////////////////////////////////////////
//import java.util.HashSet;
//import java.util.Set;
//
//public class Node extends Thread {
//    private char name;
//    private HashSet<Node> dependencies;
//
//    public Node(char name) {
//        this.name = name;
//        this.dependencies = new HashSet<>();
//    }
//
//    public void addDependency(Node node) {
//        dependencies.add(node);
//    }
//
//    @Override
//    public void run() {
//
//        System.out.println("Node" + name + " is being started");
//
//        for (Node dependency : dependencies) {
//            System.out.println("Node" + name + " is waiting for " + dependency.name);
//            try {
//                dependency.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        perform();
//
//        System.out.println("Node" + name + " is completed");
//    }
//
//    private void perform() {
//        try {
//            Thread.sleep((long) (Math.random() * 2000));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
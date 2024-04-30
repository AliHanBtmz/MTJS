package com.alihanbatmazoglu;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2 || !args[0].equals("-i")) {
            System.out.println("Usage: java -jar Project2.jar -i input.txt");
            return;
        }
        String inputFile = args[1];
        final Main instance = new Main();

        Map<String, Node> nodeMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("->");

                if (parts.length==1){
                    String nodeName = parts[0];
                    Node node = nodeMap.computeIfAbsent(nodeName, k -> new Node(k, new ArrayList<>()));
                    continue;
                }
                String nodeName = parts[1];
                Node node = nodeMap.computeIfAbsent(nodeName, k -> new Node(k, new ArrayList<>()));
                String[] dependencyNames = parts[0].split(",");
                for (String dependencyName : dependencyNames) {
                    Node dependency = nodeMap.computeIfAbsent(dependencyName, k -> new Node(k, new ArrayList<>()));
                    node.dependencies.add(dependency);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        instance.executeThreads(nodeMap);


    }


    private void executeThreads(Map<String, Node> nodeMap){
        for (Node node : nodeMap.values()) {
            node.perform();
        }
    }
}










//////////////////////////////////////////////////////////////////////////////
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        if (args.length != 2 || !args[0].equals("-i")) {
//            System.out.println("Usage: java -jar Project2.jar -i input.txt");
//            return;
//        }
//
//        String inputFileName = args[1];
//
//        Map<Character, Node> nodes = new HashMap<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split("->");
//
//                if (parts.length == 1) {
//                    char nodeName = parts[0].charAt(0);
//
//                    Node node = nodes.computeIfAbsent(nodeName, k -> new Node(nodeName));
//                    continue;
//                }
//                char nodeName = parts[1].charAt(0);
//                Node node = nodes.computeIfAbsent(nodeName, k -> new Node(nodeName));
//
//                String[] dependencies = parts[0].split(",");
//                for (String dep : dependencies) {
//                    node.addDependency(nodes.computeIfAbsent(dep.charAt(0), k -> new Node(dep.charAt(0))));
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        for (Node node : nodes.values()) {
//            node.start();
//        }
//
//        for (Node node : nodes.values()) {
//            try {
//                node.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
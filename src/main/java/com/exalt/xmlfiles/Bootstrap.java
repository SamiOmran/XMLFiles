package com.exalt.xmlfiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
public class Bootstrap {
    private static File[] allFiles;
    static final ArrayList<Device> allDevices = new ArrayList<>();

    public Bootstrap() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("files");
        final File directory;

        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            directory =  new File(resource.toURI());
        }
        allFiles = directory.listFiles();
    }

    /**
     * for each iteration we need to declare
     *      DocumentBuilder and Document objects,
     *   this function creates them in each iteration
     * @param file xml file to read it
     * @return xml object inside Document object
     * @throws Exception documentBuilder handles exception
     */
    private Document declareNeededObjects(File file) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(file);
    }

    private String getParentName(NodeList parentNode) {
        for (int i = 0; i < parentNode.getLength(); i++) {
            Node node = parentNode.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase("name")) {
                String value = node.getTextContent();
                return (value.equals(""))? null : value;
            }
        }
        return null;
    }

    /**
     * at the startup this function reads all xml files
     *   in the directory and store all devices in arrayList
     *   with its features and the inherited ones.
     */
    @Autowired
    private void readStoreAllFiles() {
        String id, name, parent;
        NodeList parentNode, tempList;
        Document document;
        Node node;
        Device newDevice;
        Element elementDevice;
        ArrayList<String> deviceFeatures = new ArrayList<>();

        for (File file : allFiles ) {
            try {
                // parse XML file
                document = declareNeededObjects(file);
                document.getDocumentElement().normalize();
                node = document.getElementsByTagName("device").item(0);
                elementDevice = (Element) node;

                id = elementDevice.getElementsByTagName("id").item(0).getTextContent();
                name = elementDevice.getElementsByTagName("name").item(0).getTextContent();
                parentNode = elementDevice.getElementsByTagName("parent").item(0).getChildNodes();
                parent = getParentName(parentNode);
                tempList = elementDevice.getElementsByTagName("features");
                deviceFeatures = getFeaturesFromNodeList(tempList);

            if (parent != null) {
                deviceFeatures.addAll(getParentFeatures(parentNode, deviceFeatures));
            }
                // remove duplicates, then convert it to arrayList
            deviceFeatures = (ArrayList<String>) deviceFeatures.stream().distinct().collect(Collectors.toList());
            newDevice = new Device(id, name, parent, deviceFeatures);
            allDevices.add(newDevice);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    /**
     * this function gets the parent's features (recursively)
     * @param parentNode parent tag
     * @param childFeatures the child features list
     * @throws Exception .
     */
    private ArrayList<String> getParentFeatures(NodeList parentNode, ArrayList<String> childFeatures) throws Exception {
        String parentAsStr = getParentName(parentNode);
        final String EXTENSION = ".xml";
        File parentFile = null;
        Document document;
        NodeList tempList;

        if (parentAsStr != null) {
            for (File file : allFiles) {
                if (file.getName().equals(parentAsStr + EXTENSION)) {
                    parentFile = file;
                    break;
                }
            }
            document = declareNeededObjects(parentFile);
            getParentFeatures(document.getElementsByTagName("parent"), childFeatures);

            tempList = document.getElementsByTagName("features");
            childFeatures.addAll(getFeaturesFromNodeList(tempList));
        }

        return childFeatures;
    }

    /**
     *  transfer the features from the NodeList to ArrayList
     * @param nodeList list of features in form NodeList
     * @return arrayList of the features
     */
    private ArrayList<String> getFeaturesFromNodeList(NodeList nodeList) {
        ArrayList<String> features = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            features.add(nodeList.item(i).getTextContent());
        }
        return features;
    }
}


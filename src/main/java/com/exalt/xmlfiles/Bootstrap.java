package com.exalt.xmlfiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.print.Doc;
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

    /**
     * constructor initializing required fields and read the directory
     * @throws URISyntaxException exception if error happened during reading path
     */
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

    /**
     *  get parent name as a string
     * @param parentNode the parent tag
     * @return a String name
     */
    private String getParentName(NodeList parentNode) {
        String value = "";
        for (int i = 0; i < parentNode.getLength(); i++) {
            Node node = parentNode.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase("name")) {
                value = node.getTextContent();
            }
        }
        return value;
    }

    /**
     * at the startup this function reads all xml files
     *   in the directory and store all devices in arrayList
     *   with its features and the inherited ones.
     */
    @Bean
    public void readStoreAllFiles() {
        String id, name, parentAsStr;
        NodeList parentNodeList, tempList;
        Document document;
        Device newDevice;
        ArrayList<String> deviceFeatures;

        for (File file : allFiles ) {
            try {
                // parse XML file
                document = declareNeededObjects(file);
                document.getDocumentElement().normalize();

                id = document.getElementsByTagName("id").item(0).getTextContent();
                name = document.getElementsByTagName("name").item(0).getTextContent();
                parentNodeList = document.getElementsByTagName("parent").item(0).getChildNodes();
                parentAsStr = getParentName(parentNodeList);
                tempList = document.getElementsByTagName("features").item(0).getChildNodes();
                deviceFeatures = getFeaturesFromNodeList(tempList);

//            if (!parentAsStr.equals("")) {
//                deviceFeatures.addAll(getParentFeatures(document, parentNodeList, deviceFeatures));
//            }
                // remove duplicates, then convert it to arrayList
            newDevice = new Device(id, name, parentAsStr, deviceFeatures, document);
            allDevices.add(newDevice);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        for (Device dev: allDevices) {
            ArrayList<String> temp = getParentFeatures(dev);
            dev.setFeatures(temp);
        }

    }

    /**
     * this function gets the parent's features (recursively)
     * @param parentAsTag parent tag
     * @param childFeatures the child features list
     * @throws Exception .
     */
//    private ArrayList<String> getParentFeatures(Node root, NodeList parentAsTag, ArrayList<String> childFeatures) throws Exception {
//        String parentAsStr = getParentName(parentAsTag);
//        final String EXTENSION = ".xml";
//        String fileName = parentAsStr + EXTENSION;
//        File parentFile = null;
//        Document document;
//        NodeList tempList;
//        Element device;
//
//        if (!parentAsStr.equals("")) {
//            for (File file : allFiles) {
//                if (file.getName().equals(fileName)) {
//                    parentFile = file;
//                    break;
//                }
//            }
//            document = declareNeededObjects(parentFile);
//            device = (Element) document.getElementsByTagName("device").item(0);
//
//            parentAsTag = device.getElementsByTagName("parent");
//
//            childFeatures.addAll(getParentFeatures(device, parentAsTag, childFeatures));
//        } else {
//            tempList = getFeaturesList(root);
//            childFeatures.addAll(getFeaturesFromNodeList(tempList));
//        }
//
//        return childFeatures;
//    }

    /**
     *  transfer the features from the NodeList (<features></features>) to ArrayList
     * @param nodeList = (<features></features>) list of features in form NodeList
     * @return arrayList of transferred features
     */
    private ArrayList<String> getFeaturesFromNodeList(NodeList nodeList) {
        ArrayList<String> features = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equalsIgnoreCase("feature"))
                features.add(node.getTextContent());
        }
        return features;
    }

    /**
     * get features tag
     * @param root the whole node
     * @return features list <features></features>
     */
//    private NodeList getFeaturesList(Node root) {
//        NodeList list = root.getChildNodes();
//        Node node;
//        for (int i = 0; i < list.getLength(); i++) {
//            node = list.item(i);
//            if (node.getNodeName().equals("features")){
//                return node.getChildNodes();
//            }
//        }
//        return list;
//    }

    private ArrayList<String> getParentFeatures(Device device) {
        ArrayList<String> features;
        features = device.getFeatures();

        if (device.getParent() != "") {
            Device temp = new Device();
            for (Device dev : allDevices) {
                if (dev.getName().equals(device.getParent())) {
                    temp = dev;
                }
            }
            ArrayList<String> parentFeatures = new ArrayList<>(getParentFeatures(temp));
            features.addAll(parentFeatures);
        }
        return features;
    }
}




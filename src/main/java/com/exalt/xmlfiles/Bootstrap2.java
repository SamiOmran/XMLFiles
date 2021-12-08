package com.exalt.xmlfiles;

import com.exalt.xmlfiles.model.DeviceProfile;
import com.exalt.xmlfiles.model.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

@Component
public class Bootstrap2 {
    public static final ArrayList<DeviceProfile> allDevices = new ArrayList<>();
    private static final List<File> allFiles = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * constructor initializing required fields
     *
     * @throws URISyntaxException exception if error happened during reading path
     */
    public Bootstrap2() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("ifm_device_profiles");
        File rootFolder;

        if (resource == null) {
            throw new IllegalArgumentException("Directory not found!");
        } else {
            rootFolder = new File(resource.toURI());
        }
        extractFiles(rootFolder.listFiles());
    }

    /**
     * read main directory, and store all files
     * inside all sub directories
     *
     * @param folder directory with nested sub directory or files
     */
    public void extractFiles(File[] folder) {
        final String fileName = "xmpdevice.xml";
        for (File temp : folder) {
            if (temp.isFile() && temp.getName().equalsIgnoreCase(fileName)) {
                allFiles.add(temp);
            } else if (temp.isDirectory()) {
                extractFiles(temp.listFiles());
            }
        }
    }

    /**
     * get parent name as a string
     *
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
     * in the directory and store all devices in arrayList
     * with its features and the inherited ones.
     */
    @Bean
    public void readStoreAllFiles() {
        String parentVersion = " ", groupId = " ", artifactId = " ";
        DeviceProfile newDevice, deviceParent;
        HashSet<Feature> dependencies;

        Node dependenciesList;
        Document document;
        int i = 0;
        for (File file : allFiles) {
            try {
                // parse XML file
                document = Bootstrap.declareNeededObjects(file);
                document.getDocumentElement().normalize();

                parentVersion = document.getElementsByTagName("parent").item(0).getChildNodes().item(5).getTextContent();
                System.out.println(parentVersion);
                artifactId = document.getElementsByTagName("artifactId").item(1).getTextContent();
                groupId = document.getElementsByTagName("groupId").item(1).getTextContent();
                dependenciesList = document.getElementsByTagName("dependencies").item(0);
                dependencies = getFeaturesFromNodeList(dependenciesList, parentVersion);

                newDevice = new DeviceProfile(parentVersion, groupId, artifactId, dependencies);
                allDevices.add(newDevice);
                i++;
            } catch (NullPointerException n) {
                n.printStackTrace();
                logger.info("Checking where is the error: " + parentVersion + ", " + artifactId + ", " + groupId + ". ");
                logger.info("Error ~!! " + i);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
//
//    // set deviceParent reference
//    @Bean
//    public void setParent() {
//        for (DeviceProfile dev : allDevices) {
//            if (!dev.getParentName().equals(""))
//                for (DeviceProfile parent : allDevices) {
//                    if (parent.getName().equals(dev.getParentName())) {
//                        dev.setDeviceParent(parent);
//                    }
//                }
//        }
//    }
//
//    // Inherit parent's features
//    @Bean
//    public void inheritParentFeatures() {
//        for (DeviceProfile dev : allDevices) {
//            ArrayList<String> temp = getParentFeatures(dev);
//            dev.setFeatures(temp);
//        }
//    }
//
    /**
     *  transfer the features from the Node (<dependencies></dependencies>) to HashSet
     * @param node the dependencies list
     * @return HashSet of transferred features
     */
    private HashSet<Feature> getFeaturesFromNodeList(Node node, String parentVersion) {
        HashSet<Feature> features = new HashSet<>();
        Feature feature;
        NodeList nodeList = node.getChildNodes();
        String groupId, artifactId, version;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = nodeList.item(i);

            if (tempNode.getNodeName().equalsIgnoreCase("dependency") &&
                    tempNode.getChildNodes().item(7).getTextContent().equals("feature")) {
                // tempList to access dependency tags
                NodeList tempList = tempNode.getChildNodes();

                groupId = tempList.item(1).getTextContent();
                artifactId = tempList.item(3).getTextContent();
                version = tempList.item(5).getTextContent();
                version = (parentVersion.equalsIgnoreCase("${project.version}")) ? version : parentVersion;

                feature = new Feature(groupId, artifactId, version);
                features.add(feature);
            }
        }
        return features;
    }

//
//    private ArrayList<String> getParentFeatures(DeviceProfile leaf) {
//        ArrayList<String> features;
//        features = leaf.getFeatures();
//        DeviceProfile temp = new DeviceProfile();
//
//        if (leaf.getDeviceParent() != null) {
//            for (DeviceProfile parent : allDevices) {
//                temp = new DeviceProfile();
//                if (parent == leaf.getDeviceParent()) {
//                    temp = parent;
//                    break;
//                }
//            }
//            ArrayList<String> parentFeatures = getParentFeatures(temp);
//            features.addAll(parentFeatures);
//        }
//        return features;
//    }

}

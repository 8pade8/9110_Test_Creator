package sample;

import java.io.*;
import java.util.ArrayList;

public class Con2Generator {

    private static ArrayList<String> generateCon(Test9110 test) {
        ArrayList<String> chains = new ArrayList<>();
        for (Connection connection : test.adaptersConnects) {
            for (int i = 1; i <= connection.tool1Connector.getConnectorType().getContactsCount(); i++) {
                chains.add(connection.tool2.getName()+"_"+connection.tool2Number+"_"+connection.tool2Connector.name+":"+
                        i+"="+connection.tool1Connector.name+":"+i);
            }
        }
        return chains;
    }

    public static void writeConFile(Test9110 test, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        ArrayList<String> list = generateCon(test);
        for (int i = 0; i <list.size() ; i++) {
            fileWriter.write(list.get(i));
                fileWriter.write(";\r\n");
        }
        fileWriter.close();

    }
}

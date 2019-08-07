package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class OffGenerator {

    public static void writeOffFile(Test9110 test, ArrayList<Connection> connections, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        ArrayList<String> list = generateCh(test, connections);
        for (int i = 0; i <list.size() ; i++) {
            fileWriter.write(list.get(i));
        }
        fileWriter.close();

    }

    private static ArrayList<String> generateCh(Test9110 test, ArrayList<Connection> connections) {
        ArrayList<Connection> summ = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        summ.addAll(connections);
        summ.addAll(test.adaptersConnects);

        Iterator<Connection> it = summ.iterator();
        while (it.hasNext()) {
            Connection connection = it.next();
            if (connection.tool1.getType().equals("Замыкатель") || connection.tool2.getType().equals("Замыкатель")) {
                it.remove();
            }
        }

        ChainTracer chainTracer = new ChainTracer(test, 1, test.usedTestConnectors, summ);
        chainTracer.chainTrace();
        ArrayList<Chain> chains = chainTracer.getChains();
        int i = 1;
        for (Chain chain : chains) {
            StringBuilder sb = new StringBuilder("#"+i+"=");
            for (Connection con : test.adaptersConnects) {
                if (chain.inputConnector.name.equals(con.tool1Connector.name)) {
                    sb.append(con.tool2+"_"+con.tool2Number+"_"+ con.tool2Connector.name + ":" + chain.in+",");
                    break;
                }
            }
            for (Connection con : test.adaptersConnects) {
                if (chain.outputConnector.name.equals(con.tool1Connector.name)) {
                    sb.append(con.tool1+"_"+con.tool1Number+"_"+con.tool1Connector.name + ":" + chain.out+";\r\n");
                    break;
                }
            }
            list.add(sb.toString());
            i++;

        }

        for (Connection connection : test.adaptersConnects) {
            for (int j=1;j<=connection.tool1Connector.getConnectorType().getContactsCount()+1;j++)
                if (!contains(connection, j,chains)) {
                  StringBuilder  sb = new StringBuilder("#"+i+"="+connection.tool2+"_"+connection.tool2Number+"_"+ connection.tool2Connector.name + ":" +j+";\r\n");
                    list.add(sb.toString());
                    i++;
                }
        }

        return list;
    }

    private static boolean contains(Connection connection, int contact, ArrayList<Chain> chains) {
        for (Chain ch : chains) {
            if (ch.in == contact && ch.inputConnector.name.equals(connection.tool1Connector.name) &&
                    ch.inputConnector.getConnectorType().type.equals(connection.tool1Connector.connectorType.type)) {
                return true;
            }
            if (ch.out == contact && ch.outputConnector.name.equals(connection.tool1Connector.name) &&
                    ch.outputConnector.getConnectorType().type.equals(connection.tool1Connector.connectorType.type)) {
                return true;
            }
        }
        return false;
    }
}

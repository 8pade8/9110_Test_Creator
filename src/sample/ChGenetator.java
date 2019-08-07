package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ChGenetator {

    public static void writeChFile(Test9110 test, ArrayList<Connection> connections, String fileName) throws IOException {
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
        ChainTracer chainTracer = new ChainTracer(test, 1, test.usedTestConnectors, summ);
        chainTracer.chainTrace();
        ArrayList<Chain> chains = chainTracer.getChains();
        for (Chain chain : chains) {
            StringBuilder sb = new StringBuilder();
            for (Connection con : test.adaptersConnects) {
                if (chain.inputConnector.name.equals(con.tool1Connector.name)) {
                    sb.append("["+con.tool2+"_"+con.tool2Number+"_"+ con.tool2Connector.name + ":" + chain.in+"],");
                    break;
                }
            }
            for (Connection con : test.adaptersConnects) {
                if (chain.outputConnector.name.equals(con.tool1Connector.name)) {
                    sb.append("["+con.tool2+"_"+con.tool2Number+"_"+con.tool2Connector.name + ":" + chain.out+"]");
                    break;
                }
            }
            list.add(sb.toString());
        }
        list = analize(list);

        return list;
    }

    private static ArrayList<String> analize(ArrayList<String> chains) {
        ArrayList<String> resChains = new ArrayList<>();
        int i = 1;

        for (String chain : chains) {
            String firstContact = chain.split(",")[0];
            String secondContact = chain.split(",")[1];
            boolean exist = false;
            for (String resChain : resChains) {
                if (resChain.contains(firstContact) || resChain.contains(secondContact)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                StringBuilder sb = new StringBuilder("#"+i+++"=");
                sb.append(firstContact+","+secondContact);
                for (String ch : chains) {
                    if (ch.contains(firstContact) && ch.contains(secondContact)) {
                        continue;
                    }
                    if (ch.contains(firstContact)) {
                        String temp = ch.replace(firstContact, "");
                        temp = temp.replace(",", "");
                        if (!sb.toString().contains(temp)){
                        sb.append("," + temp);}
                    }
                    if (ch.contains(secondContact)) {
                        String temp = ch.replace(secondContact, "");
                        temp = temp.replace(",", "");
                        if (!sb.toString().contains(temp)){
                        sb.append("," + temp);}
                    }

                }
                sb.append(";\r\n");

                resChains.add(sb.toString());
            }
        }

        ArrayList<String> nChains = new ArrayList<>();
        for (String resChain:resChains
             ) {
            resChain = resChain.replace("[", "");
            resChain = resChain.replace("]", "");
            nChains.add(resChain);
        }
        return nChains;
    }

}

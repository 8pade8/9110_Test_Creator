package sample;

import java.util.ArrayList;
import java.util.Iterator;

public class ChainTracer {
  private  ArrayList<Chain> chains;
    Connectable startTool;
    ArrayList<Connector> traceConnectors;
    ArrayList<Connection> connections;
    Connector startToolConnector;
    int startToolNumber;
    int currentContact;


    public ChainTracer(Connectable tool, int toolNumber, ArrayList<Connector> traceConnectors, ArrayList<Connection> connections) {
        this.startTool = tool;
        this.traceConnectors = traceConnectors;
        this.connections = connections;
        this.startToolNumber = toolNumber;
    }

    public void chainTrace() {
        chains = new ArrayList<>();
        for (Connector toolConnector : traceConnectors) {
            startToolConnector = toolConnector;
            for (int i = 1; i <= toolConnector.getConnectorType().getContactsCount()+1; i++) /*+1 может не надо??*/{
                currentContact = i;
                trace(startTool,startToolNumber,startToolConnector,currentContact);
            }
        }
    }


    private void trace(Connectable tool, Integer toolNumber, Connector connector, int contact) {
//        System.out.println(tool+"("+toolNumber+")"+connector+":"+contact);
        for (Connection connection : connections) {

            if (connection.tool1.getType().equals(tool.getType()) && tool.getName().equals(tool.getName())
                    && connection.tool1Number == toolNumber && connection.tool1Connector.name.equals(connector.name)) {
                if (tryMakeChain(connection.tool2, connection.tool2Number, connection.tool2Connector, contact)) {
                    break;
                }
                for (Chain chain : connection.tool2.getChains()) {
                    if (chain.inputConnector.name.equals(connection.tool2Connector.name) && chain.in == contact) {
                        trace(connection.tool2, connection.tool2Number, chain.outputConnector, chain.out);

                    }
                    if (chain.outputConnector.name.equals(connection.tool2Connector.name) && chain.out == contact) {
                        trace(connection.tool2, connection.tool2Number, chain.inputConnector, chain.in);
                    }
                }
            }
            if (connection.tool2.getType().equals(tool.getType()) && connection.tool2.getName().equals(tool.getName())
                    && connection.tool2Number == toolNumber && connection.tool2Connector.name.equals(connector.name)) {
                if (tryMakeChain(connection.tool1, connection.tool1Number, connection.tool1Connector, contact)) {
                    break;
                }
                for (Chain chain : connection.tool1.getChains()) {
                    if (chain.inputConnector.name.equals(connection.tool1Connector.name) && chain.in == contact) {
                        trace(connection.tool1, connection.tool1Number, chain.outputConnector, chain.out);
                    }
                    if (chain.outputConnector.name.equals(connection.tool1Connector.name) && chain.out == contact) {
                        trace(connection.tool1, connection.tool1Number, chain.inputConnector, chain.in);

                    }
                }
            }
        }
    }

        public ArrayList<Chain> getChains () {
            clean();
            return chains;
        }

        private boolean tryMakeChain (Connectable tool,int numb, Connector connector,int contact){
            if (tool.getType().equals(startTool.getType()) && tool.getName().equals(startTool.getName()) &&
                    numb == startToolNumber) {
                chains.add(new Chain(startToolConnector, currentContact, connector, contact));
                return true;
            } else {
                return false;
            }

        }

        private void clean() {
            ArrayList<Chain> resChains = new ArrayList<>();
            for (Chain chain : chains) {
                boolean res = true;
                if (chain.inputConnector.name.equals(chain.outputConnector.name) && chain.in == chain.out) {
                    continue;
                }
                for (Chain resChain : resChains) {
                    if (resChain.isChain(chain)) {
                        res = false;
                        break;
                    }

                }
                if (res) {
                    resChains.add(chain);
                }
            }
            chains = resChains;
    }
}











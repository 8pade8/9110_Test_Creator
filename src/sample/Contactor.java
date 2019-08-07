package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Contactor implements Connectable, Serializable {
    String name;
    Connector connector;
    int count;
    ArrayList<Chain> chains;

    public Contactor(String name, Connector connector) {
        this.name = name;
        this.connector = connector;
        this.count = 1;
        this.chains = new ArrayList<>();

    }

    public Contactor(String name, Connector connector, int count) {
        this.name = name;
        this.connector = connector;
        this.count  = count;
        this.chains = new ArrayList<>();
    }

    public boolean hasConnector(Connector connectorType) {
        return this.connector.connectorType.equals(connectorType);
    }

    @Override
    public String getType() {
        return "Замыкатель";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addChains(String s) {
        String temp[] = s.split("\\s*;\\s*");
        for (int i = 0; i <temp.length ; i++) {
            if (temp[i] != null && !temp[i].equals("")) {
            addChainsOneString(temp[i]);
            }
        }
        makeBindChains();
        clean();
    }

    private void addChainsOneString(String s) {
        ChainParser cp = new ChainParser(s);
        if (cp.parralel) {
            for (int i = 0; i <cp.in.size() ; i++) {
                addChain(cp.inputName,cp.in.get(i),cp.outputName,cp.out.get(i));
            }
        } else{
            for (Integer i:cp.in) {
                for (Integer j : cp.out) {
                    addChain(cp.inputName,i,cp.outputName,j);
                }
            }
        }
    }

    private void addChain(String inputConnectorName, int in,String outputConnectorName,int out) {
        chains.add(new Chain(connector(inputConnectorName), in, connector(outputConnectorName), out));
    }

    private Connector connector(String connectorName) {

            if (this.connector.name.equals(connectorName)) {
                return connector;
            }
        System.out.println("Разъем "+connectorName+" отсутсвует у замыкателя "+name);
            return null;
    }

    public ArrayList<Chain> getChains() {
        return chains;
    }

    @Override
    public ArrayList<Connector> getConnectors() {
        return new ArrayList<Connector>(){{
            add(connector);}};
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != Contactor.class) {
            return false;
        }

        Contactor c = (Contactor) obj;

        if (c.name.equals(name)) {
            return true;
        }
        return false;

    }

    private void makeBindChains() {
        while (true) {
            ArrayList<Chain> addChain = new ArrayList<>();
            for (Chain chain : chains) {
                addChain.addAll(makeBindOneChain(chain));
                addChain.addAll(makeBindOneChain(chain.reverse(chain)));
            }
            if (addChain.size() == 0) {
                break;
            }
            chains.addAll(addChain);
        }

    }

    private ArrayList<Chain> makeBindOneChain(Chain chain) {
        ArrayList<Chain> addChains = new ArrayList<>();
        String searchConnector = chain.outputConnector.name;
        int searchContact = chain.out;
        for (Chain ch : chains) {
            if (chain.isChain(ch)) {
                continue;
            } else {
                if (ch.inputConnector.name.equals(searchConnector) && ch.in == searchContact) {
                    Chain nChain = new Chain(chain.inputConnector, chain.in, ch.outputConnector, ch.out);
                    if (!hasChain(nChain)) {
                        addChains.add(nChain);
                    }
                }
                if (ch.outputConnector.name.equals(searchConnector) && ch.out == searchContact) {
                    Chain nChain = new Chain(chain.inputConnector, chain.in, ch.inputConnector, ch.in);
                    if (!hasChain(nChain)) {
                        addChains.add(nChain);
                    }
                }
            }
        }
        return addChains;
    }

    private boolean hasChain(Chain chain) {
        for (Chain ch : chains) {
            if (ch.isChain(chain)) {
                return true;
            }
        }
        return false;
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

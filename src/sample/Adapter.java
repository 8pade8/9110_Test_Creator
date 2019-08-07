package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.SocketHandler;

public class Adapter implements Connectable, Serializable {
    private String name;
    private ArrayList<Connector> inputConnectorsList;
    private ArrayList<Connector> outputConnectorsList;
    private ArrayList<Chain> chains;
    private int count;

    public Adapter(String name, ArrayList<Connector> outputConnectorsList, ArrayList<Connector> inputConnectorsList) {
        this.name = name;
        this.inputConnectorsList = inputConnectorsList;
        this.outputConnectorsList = outputConnectorsList;
        this.chains = new ArrayList<>();
    }

    public Adapter(String name, ArrayList<Connector> outputConnectorsList) {
        this.name = name;
        this.outputConnectorsList = outputConnectorsList;
        count =1;
        this.chains = new ArrayList<>();
    }

    public Adapter(String name, ArrayList<Connector> outputConnectorsList,ArrayList<Connector> inputConnectorsList, int count) {
        this.name = name;
        this.outputConnectorsList = outputConnectorsList;
        this.inputConnectorsList = inputConnectorsList;
        this.count =count;
        this.chains = new ArrayList<>();
    }

     public boolean hasConnectorType(String connectorTypeName) {
        for (Connector connector: outputConnectorsList
             ) {
            if (connector.connectorType.equals(connectorTypeName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasConnectorType(ConnectorType connectorType) {
        for (Connector connector: outputConnectorsList
        ) {
            if (connector.connectorType.type.equals(connectorType.type)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Connector> getOutputConnectorsList() {
        return outputConnectorsList;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String getType() {
        return "Адаптер";
    }

    public ArrayList<Connector> getAnswerConnectors(ConnectorType connectorType) {
        ArrayList<Connector> resultConnectors = new ArrayList<>();
        for (Connector connector: outputConnectorsList) {
            if (connector.getConnectorType().equals(connectorType.answerConnectorType)) {
                resultConnectors.add(connector);
            }
        }
        return resultConnectors;
    }
    @Override
    public boolean equals(Object adapter) {
        if (adapter == null) {
            return false;
        } else if (adapter.getClass()!=Adapter.class){
            return false;
        }
        return ((Adapter)adapter).name.equals(name);
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
        for (Connector c : inputConnectorsList) {
            if (c.name.equals(connectorName)) {
                return c;
            }
        }
        for (Connector d : outputConnectorsList) {
            if (d.name.equals(connectorName)) {
                return d;
            }
        }
        System.out.println("Разъем"+connectorName+"отсутсвует у адапетра"+name);
        return null;
    }

    public ArrayList<Chain> getChains() {
        return chains;
    }

    @Override
    public ArrayList<Connector> getConnectors() {
        ArrayList<Connector> res = inputConnectorsList;
        res.addAll(outputConnectorsList);
        return res;
    }

    public ArrayList<Connector> getInputConnectorsList() {
        return inputConnectorsList;
    }

    public void info() {
        System.out.println(name);
        System.out.println("Входные разъемы:");
        System.out.println(inputConnectorsList);
        System.out.println(outputConnectorsList);
        System.out.println(chains);

    }
}

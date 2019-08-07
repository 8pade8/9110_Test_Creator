package sample;

import java.util.ArrayList;

public class ControlObject implements Connectable {
    String name;
    ArrayList<Connector> inputConnectors;
    ArrayList<Connector> outputConnectors;
    ArrayList<Chain> chains;

    public ControlObject() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInputConnectors(ArrayList<Connector> inputConnectors) {
        this.inputConnectors = inputConnectors;
    }

    public void setOutputConnectors(ArrayList<Connector> outputConnectors) {
        this.outputConnectors = outputConnectors;
    }

    public ControlObject(String name, ArrayList<Connector> inputConnectors, ArrayList<Connector> outputConnectors, ArrayList<Chain> chains) {
        this.name = name;
        this.inputConnectors = inputConnectors;
        this.outputConnectors = outputConnectors;
        this.chains = chains;
    }

    public ControlObject(String name, ArrayList<Connector> inputConnectors, ArrayList<Connector> outputConnectors) {
        this.name = name;
        this.inputConnectors = inputConnectors;
        this.outputConnectors = outputConnectors;
        this.chains = new ArrayList<Chain>();
    }

    @Override
    public String getType() {
        return "ОК";
    }

    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Chain> getChains() {
        return chains;
    }

    @Override
    public ArrayList<Connector> getConnectors() {
        ArrayList<Connector> res = inputConnectors;
        res.addAll(outputConnectors);
        return res;
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
        if (outputConnectors.size() == 0 && inputConnectors.size() == 0) {
            System.out.println("Устройство не имеет разъемов.");
            return null;
        }
            for (Connector c : inputConnectors) {
                if (c.name.equals(connectorName)) {
                    return c;
                }
            }

        for (Connector d : outputConnectors) {
            if (d.name.equals(connectorName)) {
                return d;
            }
        }
        System.out.println("Разъем "+connectorName+" отсутсвует у ОК "+name);
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}

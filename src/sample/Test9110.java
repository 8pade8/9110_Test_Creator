package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class Test9110 implements Connectable {
    String name;
    ArrayList<Connector> connectors;
    ArrayList<Connection> adaptersConnects;
    HashMap<Adapter, Integer> usedAdapters;
    ArrayList<Connector> usedTestConnectors;

    public Test9110(ArrayList<Connector> connectors) {
        this.connectors = connectors;
        name = "Test-9110";
    }

    public Test9110(String name, ArrayList<Connector> connectors) {
        this.name = name;
        this.connectors = connectors;
    }

    public void findConnectors(ArrayList<Connection> data) {
        adaptersConnects = new ArrayList<>();
        usedAdapters = new HashMap<>();
        usedTestConnectors = new ArrayList<>();
        Adapter temp;
        for (Connection d : data) {
            if (d.tool2.getClass() == Adapter.class){
                temp = (Adapter)d.tool2;
                if (usedAdapters.containsKey(temp)&&usedAdapters.get(temp)==d.tool2Number) {
                    continue;
                } else {
                    addCoonects(temp, d.tool2Number);
                    usedAdapters.put(temp, d.tool2Number);
                }
            }
            if (d.tool1.getClass() == Adapter.class){
                temp = (Adapter)d.tool1;
                if (usedAdapters.containsKey(temp)&&usedAdapters.get(temp)==d.tool1Number) {
                    continue;
                } else {
                    addCoonects(temp, d.tool1Number);
                    usedAdapters.put(temp, d.tool1Number);
                }
            }
        }
    }

    private void addCoonects(Adapter adapter, int adnumber) {
        for (Connector connector:adapter.getInputConnectorsList()) {
            ConnectorType answer = connector.connectorType.answerConnectorType;
            for (Connector testCon:connectors) {
                if (testCon.getConnectorType().equals(answer) && !usedTestConnectors.contains(testCon)) {
                    adaptersConnects.add(new Connection(this, 1, testCon,adapter,adnumber,connector));
                    usedTestConnectors.add(testCon);
                    break;
                }
            }
        }
    }

    public ArrayList<Connection> getAdaptersConnects() {
        return adaptersConnects;
    }

    @Override
    public String getType() {
        return "Test-9110";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Chain> getChains() {
        return null;
    }

    @Override
    public ArrayList<Connector> getConnectors() {
        return connectors;
    }

    public Connector connector(String connectorName) {
        for (Connector connector : connectors) {
            if (connector.name.equals(connectorName)) {
                return connector;
            }
        }
        System.out.println(this.name+" не имеет разъема "+connectorName);
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}

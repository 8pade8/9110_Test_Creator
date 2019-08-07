package sample;

import java.io.Serializable;

public class Connector implements Serializable {
    String name;
    ConnectorType connectorType;


    public Connector(String name, ConnectorType connectorType) {
        this.name = name;
        this.connectorType = connectorType;

    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }

    @Override
    public String toString() {
        return name+" "+connectorType.type;
    }

    }

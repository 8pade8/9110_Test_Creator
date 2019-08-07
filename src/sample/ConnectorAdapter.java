package sample;

public class ConnectorAdapter{
    Connector connector;
    Adapter adapter;

    public ConnectorAdapter(Connector connector, Adapter adapter) {
        this.connector = connector;
        this.adapter = adapter;
    }

    public Connector getConnector() {
        return connector;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean equals(Object connectorAdapter) {
        if (((ConnectorAdapter)connectorAdapter).adapter.getName().equals(adapter.getName()) && ((ConnectorAdapter)connectorAdapter).connector.name == connector.name) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return adapter+" "+connector;
    }
}

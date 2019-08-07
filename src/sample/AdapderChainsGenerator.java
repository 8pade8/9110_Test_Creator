package sample;

public class AdapderChainsGenerator {
    public static String generateChains(Adapter adapter) {
        StringBuilder chains = new StringBuilder();
        int counter = 2;
        int m = 1;
        for (Connector connector: adapter.getOutputConnectorsList()){
            if (connector.name.contains((String.valueOf(counter)))){
                chains.append("X1:" + m + "-" + (m + connector.connectorType.getContactsCount() - 1) +
                        "=" + connector.name + ":1" + "-" + connector.connectorType.getContactsCount() + ";\n\r");
                        m=m+connector.connectorType.getContactsCount();
                        counter++;
                }
            }
        return chains.toString();
    }
}

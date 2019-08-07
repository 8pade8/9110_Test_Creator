package sample;

public class ContactorChainsGenerator {
    public static String generateChains(Contactor contactor) {
        Connector connector = contactor.connector;
        StringBuilder chains = new StringBuilder();
        int count = connector.connectorType.contactsCount;

        for (int i = 1; i <= count; i+=2) {
            if (i + 2 == count) {
                chains.append(connector.name + ":" + i + "=" + connector.name + ":" + (i + 1) + ";\n\r");
                chains.append(connector.name + ":" + i + "=" + connector.name + ":" + (i + 2) + ";\n\r");
                break;
            }
            chains.append(connector.name + ":" + i + "=" + connector.name + ":" + (i + 1)+";\n\r" );

        }

        return chains.toString();
    }
}


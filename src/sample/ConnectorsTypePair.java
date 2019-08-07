package sample;

import java.util.*;

public class ConnectorsTypePair {
    private ConnectorType firstConnectorType;
    private ConnectorType secondConnectorType;
    private String ConnectorTypeName;

    public ConnectorsTypePair(String firstConnectorName,
                              String secondConnectName, int сonnectorContactCount) {
        firstConnectorType = new ConnectorType(firstConnectorName, сonnectorContactCount);
        secondConnectorType = new ConnectorType(secondConnectName, сonnectorContactCount);
        firstConnectorType.setAnswerConnectorType(secondConnectorType);
        secondConnectorType.setAnswerConnectorType(firstConnectorType);
        setConnectorPairName(firstConnectorType.type);
    }

    public ConnectorsTypePair(ConnectorType firstType, ConnectorType secondType) {
        firstConnectorType = firstType;
        secondConnectorType = secondType;
        setConnectorPairName(firstConnectorType.type);
    }

    public ConnectorType getFirstConnectorType() {
        return firstConnectorType;
    }

    public ConnectorType getSecondConnectorType() {
        return secondConnectorType;
    }

    @Override
    public String toString() {
        return ConnectorTypeName;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != ConnectorsTypePair.class) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        ConnectorsTypePair cnp = (ConnectorsTypePair) obj;

        if (cnp.ConnectorTypeName.equals(ConnectorTypeName)) {
            return true;
        }

        if (cnp.firstConnectorType.equals(firstConnectorType) && cnp.secondConnectorType.equals(secondConnectorType)) {
            return true;
        }
        if (cnp.firstConnectorType.equals(secondConnectorType) && cnp.secondConnectorType.equals(firstConnectorType)) {
            return true;
        }
        return false;
    }

    private void setConnectorPairName(String s) {
        s = s.replace("Розетка"," ");
        s = s.replace("Вилка"," ");
        s = s.trim();
        ConnectorTypeName = s;
    }

    public static ArrayList<ConnectorType> extractConnectorTypesList(ArrayList<ConnectorsTypePair> pairs) {
        ArrayList<ConnectorType> result = new ArrayList<>();
        for (ConnectorsTypePair pair : pairs) {
            result.add(pair.getFirstConnectorType());
            result.add(pair.getSecondConnectorType());
        }
        return result;
    }

    public static ArrayList<ConnectorsTypePair> packConnectorsList(ArrayList<ConnectorType> types) {
        ArrayList<ConnectorsTypePair> pairs = new ArrayList<ConnectorsTypePair>();
        ConnectorsTypePair temp;
        for (ConnectorType type : types) {
            temp = new ConnectorsTypePair(type, type.answerConnectorType);
            if (!pairs.contains(temp)){
                pairs.add(temp);}
        }
        return pairs;
    }

    public void setConnectorTypeName(String connectorTypeName) {
        ConnectorTypeName = connectorTypeName;
        if (firstConnectorType.type.contains("Розетка")) {
            firstConnectorType.type = "Розетка "+connectorTypeName;
        } else
        if (firstConnectorType.type.contains("Вилка")) {
            firstConnectorType.type = "Вилка "+connectorTypeName;
        }
        if (secondConnectorType.type.contains("Розетка")) {
            secondConnectorType.type = "Розетка "+connectorTypeName;
        } else
        if (secondConnectorType.type.contains("Вилка")) {
            secondConnectorType.type = "Вилка "+connectorTypeName;
        }
    }

    public String getConnectorTypeName() {
        return ConnectorTypeName;
    }
}

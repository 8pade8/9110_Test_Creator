package sample;

import java.io.Serializable;

public class ConnectorType implements Serializable {
    String type;
    int contactsCount;
    ConnectorType answerConnectorType;

    public ConnectorType(String type, int contactsCount) {
        this.type = type;
        this.contactsCount = contactsCount;
    }

    public boolean equals(ConnectorType connectorType) {
        return connectorType.type.equals(type);
    }

    public boolean isConnectorType(String connectorTypeName) {
        return connectorTypeName.equals(type);
    }

    public ConnectorType getAnswerConnectorType() {
        return answerConnectorType;
    }

    public int getContactsCount() {
        return contactsCount;
    }

    public void setAnswerConnectorType(ConnectorType answerConnectorType) {
        this.answerConnectorType = answerConnectorType;
    }



    @Override
    public String toString() {
        return type;
    }


}

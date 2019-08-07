package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ResourseSaver {
    ArrayList<Adapter> adapters;
    ArrayList<ConnectorType> connectors;
    ArrayList<Contactor> contactors;
    String adaptersFileName = "Resources\\adapters.res";
    String connectorsFileName = "Resources\\connectors.res";
    String contactorsFileName = "Resources\\contactors.res";
    String okGeneratorFileName = "Resources\\okDefault.res";
    OkGenerator okGenerator;

    public ResourseSaver() {
        adapters = new ArrayList<>();
        contactors = new ArrayList<>();
        connectors = new ArrayList<>();
        okGenerator = new OkGenerator();

    }

    public ArrayList<Adapter> getAdapters() {
        return adapters;
    }

    public void setAdapters(ArrayList<Adapter> adapters) {
        this.adapters = adapters;
    }

    public ArrayList<ConnectorType> getConnectors() {
        return connectors;
    }

    public void setConnectors(ArrayList<ConnectorType> connectors) {
        this.connectors = connectors;
    }

    public ArrayList<Contactor> getContactors() {
        return contactors;
    }

    public void setContactors(ArrayList<Contactor> contactors) {
        this.contactors = contactors;
    }

    public void saveAdapters() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(adaptersFileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл "+adaptersFileName+" не найден.");
            return;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Adapter a : adapters) {
            try {
                objectOutputStream.writeObject(a);
            } catch (IOException e) {
                System.out.println("Файл "+adaptersFileName+" не найден.");
            }
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {

            System.out.println("Файл "+adaptersFileName+" не найден.");

        }
    }

    public void saveConnectors() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(connectorsFileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл "+connectorsFileName+" не найден.");
            return;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ConnectorType a : connectors) {
            try {
                objectOutputStream.writeObject(a);
            } catch (IOException e) {
                System.out.println("Файл "+connectorsFileName+" не найден.");
            }
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {

            System.out.println("Файл "+contactorsFileName+" не найден.");

        }

    }

    public void saveContactors() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(contactorsFileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл "+contactorsFileName+" не найден.");
            return;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Contactor a : contactors) {
            try {
                objectOutputStream.writeObject(a);
            } catch (IOException e) {
                System.out.println("Файл "+contactorsFileName+" не найден.");
            }
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {

            System.out.println("Файл "+adaptersFileName+" не найден.");

        }

    }

    public void loadAdapters() {
        try {
            FileInputStream fileInputStream = new FileInputStream(adaptersFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            while (fileInputStream.available() > 0) {
                try {
                    adapters.add((Adapter) objectInputStream.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Файл "+adaptersFileName+" не найден.");
        }
    }
    public void loadConnectors() {

        try {
            FileInputStream fileInputStream = new FileInputStream(connectorsFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            while (fileInputStream.available() > 0) {
                try {
                    connectors.add((ConnectorType) objectInputStream.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Файл "+connectorsFileName+" не найден.");
        }
    }

    public void loadContactors() {
        try {
            FileInputStream fileInputStream = new FileInputStream(contactorsFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            while (fileInputStream.available() > 0) {
                try {
                    contactors.add((Contactor) objectInputStream.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Файл "+contactorsFileName+" не найден.");
        }

}

    public void loadOkDefault() {
        try {
            FileInputStream fileInputStream = new FileInputStream(okGeneratorFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            while (fileInputStream.available() > 0) {
                try {
                  okGenerator = (OkGenerator) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Файл "+okGeneratorFileName+" не найден.");
        }
    }

    public void saveOkDefault() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(okGeneratorFileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл "+okGeneratorFileName+" не найден.");
            return;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

            try {
                objectOutputStream.writeObject(okGenerator);
            } catch (IOException e) {
                System.out.println("Файл "+okGeneratorFileName+" не найден.");
            }

        try {
            objectOutputStream.close();
        } catch (IOException e) {

            System.out.println("Файл "+okGeneratorFileName+" не найден.");

        }
    }

    public void saveAll(){
        saveAdapters();
        saveConnectors();
        saveContactors();
    }

    public void loadAll() {
        loadAdapters();
        loadConnectors();
        loadContactors();
        loadOkDefault();
    }

    public ConnectorType getConnectorType(String coonnectorTypeName) {
        for (ConnectorType connectorType : connectors) {
            if (connectorType.type.equals(coonnectorTypeName)) {
                return connectorType;
            }
        }
        return null;
    }

    public void removeAdapter(String adapterName) {
        Iterator<Adapter> iterator = adapters.iterator();
        while (iterator.hasNext()) {
            Adapter adapter = iterator.next();
            if (adapter.getName().equals(adapterName)) {
                iterator.remove();
            }
        }
    }

    public void removeConnector(String connectorName) {
        Iterator<ConnectorType> iterator = connectors.iterator();
        while (iterator.hasNext()) {
            ConnectorType connectorType = iterator.next();
            if (connectorType.type.equals(connectorName)) {
                iterator.remove();
            }
        }
    }

    public void updateAdapters(ConnectorType oldConnector, ConnectorsTypePair newConnectorTypePair) {
        for (Adapter adapter : adapters) {
            for (Connector connector : adapter.getInputConnectorsList()) {
                if (connector.connectorType.type.equals(oldConnector.type)&&oldConnector.type.contains("Розетка")) {
                    if (newConnectorTypePair.getFirstConnectorType().type.contains("Розетка")){
                        connector.connectorType = newConnectorTypePair.getFirstConnectorType();
                    }
                    if (newConnectorTypePair.getSecondConnectorType().type.contains("Розетка")){
                        connector.connectorType = newConnectorTypePair.getSecondConnectorType();
                    }
                    System.out.println("Изменен тип разъема "+ connector.name+" адаптера "+adapter);
                }
                if (connector.connectorType.type.equals(oldConnector.type)&&oldConnector.type.contains("Вилка")) {
                    if (newConnectorTypePair.getFirstConnectorType().type.contains("Вилка")){
                        connector.connectorType = newConnectorTypePair.getFirstConnectorType();
                    }
                    if (newConnectorTypePair.getSecondConnectorType().type.contains("Вилка")){
                        connector.connectorType = newConnectorTypePair.getSecondConnectorType();
                    }
                    System.out.println("Изменен тип разъема "+ connector.name+" адаптера "+adapter);
                }
            }
            for (Connector connector : adapter.getOutputConnectorsList()) {
                if (connector.connectorType.type.equals(oldConnector.type)&&oldConnector.type.contains("Розетка")) {
                    if (newConnectorTypePair.getFirstConnectorType().type.contains("Розетка")){
                        connector.connectorType = newConnectorTypePair.getFirstConnectorType();
                    }
                    if (newConnectorTypePair.getSecondConnectorType().type.contains("Розетка")){
                        connector.connectorType = newConnectorTypePair.getSecondConnectorType();
                    }
                    System.out.println("Изменен тип разъема "+ connector.name+" адаптера "+adapter);
                }
                if (connector.connectorType.type.equals(oldConnector.type)&&oldConnector.type.contains("Вилка")) {
                    if (newConnectorTypePair.getFirstConnectorType().type.contains("Вилка")){
                        connector.connectorType = newConnectorTypePair.getFirstConnectorType();
                    }
                    if (newConnectorTypePair.getSecondConnectorType().type.contains("Вилка")){
                        connector.connectorType = newConnectorTypePair.getSecondConnectorType();
                    }
                    System.out.println("Изменен тип разъема "+ connector.name+" адаптера "+adapter);
                }
            }
        }
    }

    public void updateContactors(ConnectorType oldConnector, ConnectorsTypePair newConnectorTypePair) {
        for (Contactor contactor : contactors) {
                Connector connector = contactor.connector;
                if (connector.connectorType.type.equals(oldConnector.type)&&oldConnector.type.contains("Розетка")) {
                    if (newConnectorTypePair.getFirstConnectorType().type.contains("Розетка")){
                        connector.connectorType = newConnectorTypePair.getFirstConnectorType();
                    }
                    if (newConnectorTypePair.getSecondConnectorType().type.contains("Розетка")){
                        connector.connectorType = newConnectorTypePair.getSecondConnectorType();
                    }
                    System.out.println("Изменен тип разъема "+ connector.name+" замыкателя "+contactor);
                }
                if (connector.connectorType.type.equals(oldConnector.type)&&oldConnector.type.contains("Вилка")) {
                    if (newConnectorTypePair.getFirstConnectorType().type.contains("Вилка")){
                        connector.connectorType = newConnectorTypePair.getFirstConnectorType();
                    }
                    if (newConnectorTypePair.getSecondConnectorType().type.contains("Вилка")){
                        connector.connectorType = newConnectorTypePair.getSecondConnectorType();
                    }
                    System.out.println("Изменен тип разъема "+ connector.name+" замыкателя "+contactor);
                }
            }

    }

    public void removeContactor(String name) {
        Iterator<Contactor> iterator = contactors.iterator();
        while (iterator.hasNext()) {
            Contactor contactor = iterator.next();
            if (contactor.name.equals(name)) {
                iterator.remove();
            }
        }
    }

    public ArrayList<Connectable> analizeUseConnector(ConnectorType connectorType) {
        ArrayList<Connectable> res = new ArrayList<>();
        for (Adapter adapter : adapters) {
            for (Connector connector : adapter.getInputConnectorsList()) {
                if (connector.connectorType.type.equals(connectorType.type)) {
                    res.add(adapter);
                }
            }
            for (Connector connector : adapter.getOutputConnectorsList()) {
                if (connector.connectorType.type.equals(connectorType.type)) {
                    res.add(adapter);
                }
            }
        }
        for (Contactor contactor : contactors) {
            if (contactor.connector.connectorType.type.equals(connectorType.type)) {
                res.add(contactor);
            }
        }
        return res;
    }

    public void addConnector(ConnectorType connectorType) {
        if (connectors.contains(connectorType)) {
            connectors.remove(connectorType);
        }
        connectors.add(connectorType);
    }
}

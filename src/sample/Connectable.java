package sample;

import java.util.ArrayList;

public interface Connectable {
    String getType();
    String getName();
    ArrayList<Chain> getChains();
    ArrayList<Connector> getConnectors();
}

package sample;

public class Connection {

    Connector tool1Connector;
    Connector tool2Connector;
    Connectable tool1;
    Connectable tool2;
    int tool1Number;
    int tool2Number;

    public Connection(Connectable tool1, int tool1Number, Connector tool1Connector, Connectable tool2, int tool2Number, Connector tool2Connector) {
        this.tool1 = tool1;
        this.tool2 = tool2;
        this.tool1Number = tool1Number;
        this.tool2Number = tool2Number;
        this.tool1Connector = tool1Connector;
        this.tool2Connector = tool2Connector;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Для "+ tool1.getType()+ "("+tool1Number+") "+ tool1.getName()+" разъема " + tool1Connector.name + " " + tool1Connector.connectorType.type + " " + "подключить " + tool2.getType() +
                " "+ tool2.getName()+"("+ tool2Number +")"+" разъем " + tool2Connector.name + " " + tool2Connector.connectorType.type+"\n\r");
        return sb.toString();
    }
}

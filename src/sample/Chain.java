package sample;

import java.io.Serializable;

public class Chain implements Serializable {
    Connector inputConnector;
    int in;
    Connector outputConnector;
    int out;

    public Chain(Connector inputConnector, int in, Connector outputConnector, int out) {
        this.inputConnector = inputConnector;
        this.in = in;
        this.outputConnector = outputConnector;
        this.out = out;
    }

    @Override
    public String toString() {
        return inputConnector.name+":"+in+" = "+outputConnector.name+":"+out+";\r\n";
    }

    public Chain reverse(Chain chain) {
        return new Chain(chain.outputConnector, chain.out, chain.inputConnector, chain.in);
    }

    public boolean isChain(Chain chain) {
        if (isAnalogChain(chain) || isAnalogChain(reverse(chain))) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isAnalogChain(Chain chain) {
        if (inputConnector.name.equals(chain.inputConnector.name) &&
                in == chain.in &&
                outputConnector.name.equals(chain.outputConnector.name) &&
                        out == chain.out) {
            return true;
        }
        return false;
    }

}

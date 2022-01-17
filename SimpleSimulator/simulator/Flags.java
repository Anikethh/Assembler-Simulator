package simulator;

public class Flags {
    private int V, L, G, E;

    public Flags() {
        clear();
    }

    public void clear() {
        V = L = G = E = 0;
    }

    public int getV() {
        return V;
    }

    public void setV(int v) {
        V = v;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getE() {
        return E;
    }

    public void setE(int e) {
        E = e;
    }

    public String getDataBinary() {
        return "000000000000" + V + L + G + E;
    }
}

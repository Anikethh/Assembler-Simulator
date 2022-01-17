package simulator;

import java.util.ArrayList;

public class Memory {
    private static ArrayList<String> array;

    public static void init(ArrayList<String> input) {
        array = new ArrayList<>(input);
        int size = input.size();
        for (int i = 0; i < 256 - size; i++) {
            array.add("0000000000000000");
        }
    }

    public static void dump() {
        for(String binary : array) {
            System.out.println(binary);
        }
    }

    public static String getBinary(int index) {
        return array.get(index);
    }

    public static void setData(int data, int index) {
        String res = Integer.toBinaryString(data);
        int l = res.length();
        for (int i = 0; i < 16 - l; i++) {
            res = "0" + res;
        }
        array.set(index, res);
    }
}

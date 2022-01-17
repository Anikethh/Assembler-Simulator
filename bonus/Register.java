package bonus;

public class Register {
   private int data;
   private static final int MAX = (int) Math.pow(2, 16);

   public Register() {
      data = 0;
   }

   public int getData() {
      return data;
   }

   public boolean setData(int value) {
      if(value > MAX) {
         data = value % MAX;
         return true;
      }
      if(value < 0) {
         data = 0;
         return true;
      }
      data = value;
      return false;
   }

   public String getDataBinary() {
      String res = Integer.toBinaryString(data);
      int l = res.length();
      for (int i = 0; i < 16 - l; i++) {
         res = "0" + res;
      }
      return res;
   }

}

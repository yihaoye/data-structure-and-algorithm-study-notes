// https://www.youtube.com/watch?v=RDpyv1HET-o
public class Main {
    public static boolean isSameSubnet(String ip1, String ip2) {
        String subNetMask = "255.255.255.0";
        String[] mask = subNetMask.split("\\.");
        String[] ip1Arr = ip1.split("\\.");
        String[] ip2Arr = ip2.split("\\.");

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < mask.length; i++) {
            int v1 = Integer.valueOf(ip1Arr[i]) & Integer.valueOf(mask[i]);
            int v2 = Integer.valueOf(ip2Arr[i]) & Integer.valueOf(mask[i]);
            sb1.append(Integer.toBinaryString(v1)).append(" ");
            sb2.append(Integer.toBinaryString(v2)).append(" ");
        }

        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
        return sb1.toString().equals(sb2.toString());
    }

    public static void main(String[] args) {
        String ip1 = "192.168.1.3";
        String ip2 = "192.168.1.6";

        System.out.println(isSameSubnet(ip1, ip2));
    }
}

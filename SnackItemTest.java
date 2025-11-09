public class SnackItemTest {
    public static void main(String[] args) {
        // Basic smoke test for SnackItem
        SnackItem s = new SnackItem("TestSnack", 10.5, "");
        if (!s.getName().equals("TestSnack")) {
            System.err.println("FAIL: name");
            System.exit(1);
        }
        if (Double.compare(s.getPrice(), 10.5) != 0) {
            System.err.println("FAIL: price");
            System.exit(1);
        }
        s.setQuantity(3);
        if (s.getQuantity() != 3) {
            System.err.println("FAIL: quantity");
            System.exit(1);
        }
        System.out.println("PASS: SnackItem basic tests");
    }
}

// MidnightMano - converted from SnacksOrder
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class SnackItem {
   private String name;
   private double price;
   private String imageUrl;
   private int quantity;

   public SnackItem(String name, double price, String imageUrl) {
      this.name = name;
      this.price = price;
      this.imageUrl = imageUrl;
      this.quantity = 0;
   }

   public String getName() { return name; }
   public double getPrice() { return price; }
   public String getImageUrl() { return imageUrl; }
   public int getQuantity() { return quantity; }
   public void setQuantity(int q) { this.quantity = q; }
}

public class MidnightMano extends JFrame {
   private ArrayList<SnackItem> snacks;
   private JPanel mainPanel;
   private JPanel cartPanel;
   private JLabel totalLabel;
   private DecimalFormat df = new DecimalFormat("0.00");
   private NumberFormat currencyFmt = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
   private static final Path CART_FILE = Paths.get("cart.txt");
   private static final Path LAST_ORDER_FILE = Paths.get("last_order.txt");

   public MidnightMano() {
      this.setTitle("🌙 Midnight Mano - Snack Delivery");
      this.setDefaultCloseOperation(3);
      this.setSize(800, 600);
      this.setLocationRelativeTo((Component)null);
      this.snacks = new ArrayList<>();
      this.initializeSnacks();
      this.loadCartFromFile();
      this.setLayout(new BorderLayout());
      JPanel var1 = new JPanel();
      var1.setBackground(new Color(25, 25, 35));
      JLabel var2 = new JLabel("Welcome to Midnight Mano!");
      var2.setFont(new Font("Arial", 1, 24));
      var2.setForeground(Color.WHITE);
      var1.add(var2);
      this.add(var1, "North");
   this.mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));
      this.mainPanel.setBackground(new Color(35, 35, 45));
   this.displaySnacks();
      this.cartPanel = new JPanel();
      this.cartPanel.setLayout(new BoxLayout(this.cartPanel, 1));
      this.cartPanel.setBackground(new Color(45, 45, 55));
      this.cartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      this.totalLabel = new JLabel("Total: ₹0.00");
      this.totalLabel.setForeground(Color.WHITE);
      this.totalLabel.setFont(new Font("Arial", 1, 18));
      JButton var3 = new JButton("Checkout");
      var3.addActionListener((var1x) -> {
         this.handleCheckout();
      });
      this.cartPanel.add(new JLabel("Shopping Cart"));
      this.cartPanel.add(Box.createVerticalGlue());
      this.cartPanel.add(this.totalLabel);
      this.cartPanel.add(var3);
   JScrollPane var4 = new JScrollPane(this.mainPanel);
      JScrollPane var5 = new JScrollPane(this.cartPanel);
      var5.setPreferredSize(new Dimension(200, 0));
      this.add(var4, "Center");
      this.add(var5, "East");
   this.updateCartDisplay();
      this.setVisible(true);
   }

   private void initializeSnacks() {
      this.snacks.add(new SnackItem("Chips", 25.00, "chips.png"));
      this.snacks.add(new SnackItem("Oreo", 35.00, "oreo.png"));
      this.snacks.add(new SnackItem("Maggie", 45.00, "maggie.png"));
      this.snacks.add(new SnackItem("Kurkure", 25.00, "kurkure.png"));
      this.snacks.add(new SnackItem("Bourbon", 25.00, "bourbon.png"));
      this.snacks.add(new SnackItem("Hide & Seek", 35.00, "hideandseek.png"));
      this.snacks.add(new SnackItem("Jim Jam", 30.00, "jimjam.png"));
      this.snacks.add(new SnackItem("Aloo Bhujia", 25.00, "aloobhujia.png"));
   }

   private void displaySnacks() {
      this.mainPanel.removeAll();
      Iterator<SnackItem> var1 = this.snacks.iterator();

      while(var1.hasNext()) {
         SnackItem var2 = var1.next();
         JPanel var3 = this.createSnackPanel(var2);
         this.mainPanel.add(var3);
      }

      this.mainPanel.revalidate();
      this.mainPanel.repaint();
   }

   private JPanel createSnackPanel(SnackItem var1) {
      JPanel var2 = new JPanel();
      var2.setLayout(new BoxLayout(var2, 1));
      var2.setBackground(new Color(45, 45, 55));
      var2.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 70), 2));
      // optional image/icon
      JLabel iconLabel = new JLabel();
      String img = var1.getImageUrl();
      if (img != null && !img.isEmpty()) {
         try {
            Path p = Paths.get(img);
            if (Files.exists(p)) {
               Image loaded = ImageIO.read(p.toFile()).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
               iconLabel.setIcon(new ImageIcon(loaded));
               iconLabel.setAlignmentX(0.5f);
               var2.add(iconLabel);
            }
         } catch (IOException var11) {
            // ignore image loading problems
         }
      }
      JLabel var3 = new JLabel(var1.getName());
      var3.setForeground(Color.WHITE);
      var3.setAlignmentX(0.5F);
   JLabel priceLabel = new JLabel(this.currencyFmt.format(var1.getPrice()));
      priceLabel.setForeground(Color.WHITE);
      priceLabel.setAlignmentX(0.5F);
      JPanel var5 = new JPanel();
      var5.setBackground(new Color(45, 45, 55));
      JButton var6 = new JButton("-");
      JLabel var7 = new JLabel("0");
      var7.setForeground(Color.WHITE);
      JButton var8 = new JButton("+");
      var6.addActionListener((var3x) -> {
         int qty = Integer.parseInt(var7.getText());
         if (qty > 0) {
            var7.setText(String.valueOf(qty - 1));
            this.updateCart(var1, qty - 1);
         }

      });
      var8.addActionListener((var3x) -> {
         int qty = Integer.parseInt(var7.getText());
         var7.setText(String.valueOf(qty + 1));
         this.updateCart(var1, qty + 1);
      });
      var5.add(var6);
      var5.add(var7);
      var5.add(var8);
      var5.setAlignmentX(0.5F);
      var2.add(Box.createVerticalStrut(10));
      var2.add(var3);
      var2.add(Box.createVerticalStrut(5));
      var2.add(priceLabel);
      var2.add(Box.createVerticalStrut(10));
      var2.add(var5);
      var2.add(Box.createVerticalStrut(10));
      return var2;
   }

   private void loadCartFromFile() {
      try {
         if (Files.exists(CART_FILE)) {
            List<String> lines = Files.readAllLines(CART_FILE);
            for (String line : lines) {
               String[] parts = line.split(":");
               if (parts.length == 2) {
                  String name = parts[0].trim();
                  int qty = Integer.parseInt(parts[1].trim());
                  for (SnackItem s : this.snacks) {
                     if (s.getName().equalsIgnoreCase(name)) {
                        s.setQuantity(qty);
                     }
                  }
               }
            }
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   private void saveCartToFile() {
      try {
         List<String> out = new ArrayList<>();
         for (SnackItem s : this.snacks) {
            if (s.getQuantity() > 0) {
               out.add(s.getName() + ":" + s.getQuantity());
            }
         }
         Files.write(CART_FILE, out);
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   private void saveLastOrder(String summary) {
      try {
         Files.write(LAST_ORDER_FILE, List.of(summary));
      } catch (IOException var3) {
         var3.printStackTrace();
      }
   }

   private void updateCart(SnackItem var1, int var2) {
      var1.setQuantity(var2);
      this.updateCartDisplay();
   }

   private void updateCartDisplay() {
      double var1 = 0.0;
      this.cartPanel.removeAll();
      this.cartPanel.add(new JLabel("Shopping Cart"));
      this.cartPanel.add(Box.createVerticalStrut(10));
      Iterator<SnackItem> var3 = this.snacks.iterator();

      while(var3.hasNext()) {
         SnackItem var4 = var3.next();
         if (var4.getQuantity() > 0) {
            double var5 = var4.getPrice() * (double)var4.getQuantity();
            var1 += var5;
            String var10002 = var4.getName();
            JLabel var7 = new JLabel(var10002 + " x" + var4.getQuantity() + " = " + this.currencyFmt.format(var5));
            var7.setForeground(Color.WHITE);
            this.cartPanel.add(var7);
         }
      }

      JLabel var10000 = this.totalLabel;
   String var10001 = this.currencyFmt.format(var1);
   var10000.setText("Total: " + var10001);
      JButton var8 = new JButton("Checkout");
      var8.addActionListener((var1x) -> {
         this.handleCheckout();
      });
      this.cartPanel.add(Box.createVerticalGlue());
      this.cartPanel.add(this.totalLabel);
      this.cartPanel.add(Box.createVerticalStrut(10));
      this.cartPanel.add(var8);
      this.cartPanel.revalidate();
      this.cartPanel.repaint();
   }

   private void handleCheckout() {
      double var1 = 0.0;
      StringBuilder var3 = new StringBuilder("Order Summary:\n\n");
      Iterator<SnackItem> var4 = this.snacks.iterator();

      while(var4.hasNext()) {
         SnackItem var5 = var4.next();
         if (var5.getQuantity() > 0) {
            double var6 = var5.getPrice() * (double)var5.getQuantity();
            var1 += var6;
            var3.append(var5.getName()).append(" x").append(var5.getQuantity()).append(" = ").append(this.currencyFmt.format(var6)).append("\n");
         }
      }

   var3.append("\nTotal: ").append(this.currencyFmt.format(var1));
      if (var1 > 0.0) {
         int var8 = JOptionPane.showConfirmDialog(this, var3.toString() + "\n\nConfirm your order?", "Checkout", 0);
         if (var8 == 0) {
            JOptionPane.showMessageDialog(this, "Order placed successfully!\nThank you for choosing Midnight Mano!", "Order Confirmation", 1);
            this.resetCart();
         }
      } else {
         JOptionPane.showMessageDialog(this, "Your cart is empty!", "Empty Cart", 1);
      }

   }

   private void resetCart() {
      Iterator<SnackItem> var1 = this.snacks.iterator();

      while(var1.hasNext()) {
         SnackItem var2 = var1.next();
         var2.setQuantity(0);
      }

      this.updateCartDisplay();
      this.displaySnacks();
   }

   public static void main(String[] var0) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      SwingUtilities.invokeLater(() -> {
         new MidnightMano();
      });
   }
}
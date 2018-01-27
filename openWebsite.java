public class openWebsite {

   public static void main(String[] args) {
      try {
         String url = "http://www.google.com";
         java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
         }
      catch (java.io.IOException e) {
         System.out.println(e.getMessage());
         }
      }

   }

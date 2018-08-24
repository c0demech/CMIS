class GreenApp {
  private static String apikey = "2439212338";
  private static void setapikey(String newkey){
  apikey = newkey;
  }

  public static void main(String[] args){
  GreenApp myapp = new GreenApp();
  GreenApp.setapikey("4829838333");
  System.out.println("MyAPI Key: " + myapp.apikey);
  System.out.println("API Key: " + GreenApp.apikey);
  }
}

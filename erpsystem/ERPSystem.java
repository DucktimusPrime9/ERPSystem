package erpsystem;

/**
 *
 * @author Lupe
 */
interface ERPSystem
{
   // define the interface
   void openApp();
   void setCountry(String country);
   void setRegion(String region);
   String getCountry();
   String getRegion();

}

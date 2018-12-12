package Modele;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DAOTest {
    
    private static DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection ;	
    private DAO myDAO;
    
    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        
        // On crée la connection vers la base de test "in memory"
        myDataSource = getDataSource();
	myConnection = myDataSource.getConnection();
        
        // On initialise la base avec le contenu d'un fichier de test
        String sqlFilePath = DAOTest.class.getResource("TestData.sql").getFile();
        System.out.println(sqlFilePath);
	SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
	sqlFile.setConnection(myConnection);
	sqlFile.execute();
	sqlFile.closeReader();
        
        // On crée l'objet à tester
	myDAO = new DAO(myDataSource);
        
    }
    
    @After
    public void tearDown() throws SQLException {
        myConnection.close();
    }

    private DataSource getDataSource() {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
	ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
	ds.setUser("sa");
	ds.setPassword("sa");
	return ds;
    }
    
    @Test
    public void CustomerTest() throws SQLException {
        Customer c = myDAO.Customer("apple@example.com");
        System.out.println(c);
        assertEquals("Apple",c.getName());
    }
    
    @Test
    public void CustomerNameTest() throws SQLException {
        String name = myDAO.nameOfCustomer(30);
        assertEquals("IBM",name);
    }
    
    @Test
    public void NumberCustomerTest() throws SQLException {
        int n = myDAO.numberCustomer();
        assertEquals(3,n);
    }
    
    @Test
    public void AllEmailsTest() throws SQLException {
        List<String> email = myDAO.allEmails();
        assertEquals(myDAO.numberCustomer(),email.size());
    }
    
    @Test
    public void UpdateNameTest() throws SQLException {
        String name1 = myDAO.nameOfCustomer(10);
        assertEquals("Apple",name1);
        myDAO.updateName("Google", 10);
        String name2 = myDAO.nameOfCustomer(10);
        assertEquals("Google",name2);
    }
    
    @Test
    public void ListCustomerIDTest() throws SQLException {
        List<Integer> id = myDAO.listCustomerID();
        assertEquals(myDAO.numberCustomer(),id.size());
    }
    
    @Test
    public void NumberProductTest() throws SQLException {
        int n = myDAO.numberProduct();
        assertEquals(2,n);
    }
    
    @Test
    public void NumberDiscountTest() throws SQLException {
        int n = myDAO.numberDiscount();
        assertEquals(2,n);
    }
    
    @Test
    public void findProductTest() throws SQLException{
        Product p = myDAO.findProduct(1);
        assertEquals(p.getDescription(),"Accounting Application");
    }
    
    @Test
    public void allProductTest() throws SQLException{
        List<Product> p = myDAO.AllProduct();
        assertEquals(p.size(),2);
        assertEquals(p.get(0).getDescription(),"Accounting Application");
    }
    
    @Test
    public void insertProductTest() throws SQLException{
        myDAO.insertProduct(45, 666, "SW", 5.5, 20, 5.5, true, "Description en cours");
        
        assertEquals(myDAO.numberProduct(),3);
   }
   
    @Test
    public void deleteProductTest() throws SQLException, Exception{
        myDAO.insertProduct(45, 666, "SW", 5.5, 20, 5.5, true, "Description en cours");
        myDAO.deleteProduct(45);
        assertEquals(myDAO.numberProduct(),2);
    }
    
    @Test
    public void AllCodesTest() throws SQLException {
        List<Discount> codes = myDAO.allCodes();
        assertEquals(myDAO.numberDiscount(),codes.size());
    }
    
    @Test
    public void insertDiscountTest() throws Exception{
        myDAO.addDiscount_Code("F", 45);
        assertEquals(myDAO.numberDiscount(),3);
    }
    
    @Test
    public void deleteDiscountTest() throws Exception{
        myDAO.addDiscount_Code("F", 45);
        myDAO.deleteDiscount_Code("F");
        assertEquals(myDAO.numberDiscount(),2);
    }
    
    @Test
    public void prodByClientTest() throws Exception{
        List<Order> l = myDAO.ProductByClient(10);
        int p = l.get(0).getProductID();
        assertEquals(1,p);
        assertEquals(1,l.size());
    }
    
    @Test 
    public void costByClientTest() throws Exception{
        double c = myDAO.costByClient(30);
        assertEquals(13600*5, c,0.0);
        
    }
    
    @Test
    public void updateAreaTest() throws Exception{
        Customer c = myDAO.Customer("apple@example.com");
        String address1 = c.getAddressLine1();
        String address2 = c.getAddressLine2();
        String city = c.getCity();
        String State = c.getState();
        assertEquals(address1,"1. Apple Park Way");
        assertEquals(address2,"Apple Park");
        assertEquals(city,"Cupertino");
        assertEquals(State,"CA");
        myDAO.updateArea(10,"Address 1","Address 2","Albi","FR");
        Customer c2 = myDAO.Customer("apple@example.com");
        String address1T = c2.getAddressLine1();
        String address2T = c2.getAddressLine2();
        String cityT = c2.getCity();
        String StateT = c2.getState();
        assertEquals(address1T,"Address 1");
        assertEquals(address2T,"Address 2");
        assertEquals(cityT,"Albi");
        assertEquals(StateT,"FR");
    }
    
    @Test
    public void updateCustomerTest() throws Exception{
        Customer c = myDAO.Customer("apple@example.com");
        String name = c.getName();
        String dc = c.getDiscoutCode();
        int zip = c.getZip();
        String address1 = c.getAddressLine1();
        String address2 = c.getAddressLine2();
        String city = c.getCity();
        String State = c.getState();
        String Phone = c.getPhone();
        String Fax = c.getFax();
        String email = c.getEmail();
        int credit = c.getCreditLimit();
        assertEquals(name,"Apple");
        assertEquals(dc,"N");
        assertEquals(zip,95100);
        assertEquals(address1,"1. Apple Park Way");
        assertEquals(address2,"Apple Park");
        assertEquals(city,"Cupertino");
        assertEquals(State,"CA");
        System.out.println(Phone);
        assertEquals(Phone,"305-555-0001");
        
        
        assertEquals(Fax,"305-555-0011");
        assertEquals(email,"apple@example.com");
        assertEquals(credit,500);

        myDAO.updateCustomer(10, "N", 95100, "Girafe", "adress1", "adress2", "City", "FR", "558-956-5854", "558-956-5855", "girafe@example.com", 500);
        Customer c2 = myDAO.Customer("girafe@example.com");
        String nameT = c2.getName();
        String dcT = c2.getDiscoutCode();
        int zipT = c2.getZip();
        String address1T = c2.getAddressLine1();
        String address2T = c2.getAddressLine2();
        String cityT = c2.getCity();
        String StateT = c2.getState();
        String PhoneT = c2.getPhone();
        String FaxT = c2.getFax();
        String emailT = c2.getEmail();
        int creditT = c2.getCreditLimit();
        assertEquals(nameT,"Girafe");
        assertEquals(dcT,"N");
        assertEquals(zipT,95100);
        assertEquals(address1T,"adress1");
        assertEquals(address2T,"adress2");
        assertEquals(cityT,"City");
        assertEquals(StateT,"FR");
        assertEquals(PhoneT,"558-956-5854");
        assertEquals(FaxT,"558-956-5855");
        assertEquals(emailT,"girafe@example.com");
        assertEquals(creditT,500);
    }
    
    @Test 
    public void quantityByClientTest() throws Exception{
        int c = myDAO.quantityByClient(30);
        assertEquals(c,5);
    }
    
    @Test 
    public void allOrderTest() throws Exception{
        int c = myDAO.allOrder();
        assertEquals(c,2);
    }
    
    @Test
    public void findOrderTest() throws Exception{
        Order c = myDAO.findOrder(22112018);
        assertEquals(c.getCustomerID(),10);
    }
    @Test
    public void insertOrderTest() throws Exception{
        int c = myDAO.allOrder();
        assertEquals(c,2);
        myDAO.insertOrder(5485, 10, 1, 10, (float) 25.00, "2018-11-29","2018-12-01","UPS");
        int c2 = myDAO.allOrder();
        assertEquals(c2,c+1);
    }
    
    @Test
    public void updateOrderTest() throws Exception{
        Order c = myDAO.findOrder(22112018);
        myDAO.updateOrder(22112018, 10, 2, 24, (float) 54.00, "2018-11-30", "2018-12-05");
        Order c2 = myDAO.findOrder(22112018);
        assertEquals(c.getProductID(),1);
        assertEquals(c2.getProductID(),2);
    }
    @Test
    public void deleteOrderTest() throws Exception{
        int c = myDAO.allOrder();
        assertEquals(c,2);
        myDAO.insertOrder(5485, 10, 1, 10, (float) 25.00, "2018-11-29","2018-12-01","UPS");
        int c2 = myDAO.allOrder();
        assertEquals(c2,c+1);
        myDAO.deleteOrder(5485);
        int c3 = myDAO.allOrder();
        assertEquals(c3,c);
    }
    @Test
    public void CustomerCATest() throws Exception{
        Map<String, Double>  res=myDAO.CustomerCA();
        assertEquals(res.size(),2);
        assertEquals(res.get("IBM"),5*13600,0.0);
        
        
    }
    @Test
    public void StateCATest() throws Exception{
        Map<String, Double> res=myDAO.StateCA();
        assertEquals(res.size(),2);
        assertEquals(res.get("NY"),5*13600,0.0);
    }
    
    @Test
    public void ProductCATest() throws Exception{
        Map<String, Double>  res=myDAO.ProductCA();
        assertEquals(res.size(),2);
        assertEquals(res.get("Cables"),5*13600,0.0);
    }
    
    @Test
    public void ManByProductTest() throws Exception{
        assertEquals(myDAO.ManbyProduct(1),"Lennox International Inc.");
    }
    
    @Test
    public void PriceProductTest() throws Exception{
        int p = myDAO.ProductPrice(2);
        assertEquals(p,13600);
    }
    
    @Test 
    public void maxOrdernumTest() throws Exception{
        int m = myDAO.maxOrderNum();
        assertEquals(m,22112018);
}
}
    





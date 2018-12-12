package Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DAO {

    private final DataSource myDataSource;

    public DAO(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    //Méthodes
    public Customer Customer(String email) throws SQLException {
        Customer c = null;
        String sql = "SELECT * FROM Customer WHERE email = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, email); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("CUSTOMER_ID");
                    String dc = rs.getString("DISCOUNT_CODE");
                    int zip = rs.getInt("ZIP");
                    String name = rs.getString("NAME");
                    String adress = rs.getString("ADDRESSLINE1");
                    String adress2 = rs.getString("ADDRESSLINE2");
                    String city = rs.getString("CITY");
                    String state = rs.getString("STATE");
                    String phone = rs.getString("PHONE");
                    String fax = rs.getString("FAX");
                    int credit = rs.getInt("CREDIT_LIMIT");
                    c = new Customer(id, dc, zip, name, adress, adress2, city, state, phone, fax, email, credit);
                }
            }
        }
        return c;
    }

    public String nameOfCustomer(int id) throws SQLException {
        String result = null;
        String sql = "SELECT Name FROM Customer WHERE CUSTOMER_ID = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, id); // On fixe le 1° paramètre de la requête
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getString("Name");
                }
            }
        }
        return result;
    }

    public int numberCustomer() throws SQLException {
        int result = 0;
        // Une requête SQL paramétrée
        String sql = "SELECT COUNT(*) AS Number FROM Customer";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) { // Tant qu'il y a des enregistrements
                result = rs.getInt("NUMBER");
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }

    public int updateName(String name, int id) throws SQLException {

        // Une requête SQL paramétrée
        String sql = "UPDATE CUSTOMER SET NAME=? WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setString(1, name);
            stmt.setInt(2, id);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
    }
    
    public int updateArea(int id,String address1,String address2, String city, String State) throws SQLException{
        String sql = "UPDATE CUSTOMER SET ADDRESSLINE1 = ?, ADDRESSLINE2 = ?, CITY = ?, STATE = ? WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setString(1, address1);
            stmt.setString(2, address2);
            stmt.setString(3, city);
            stmt.setString(4, State);
            stmt.setInt(5, id);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
    }
    
    public int updateCustomer(int id,String dc, int zip, String name, String address1,String address2, String city, String State, String Phone, String Fax,String Email, int credit) throws SQLException{
        String sql = "UPDATE CUSTOMER SET DISCOUNT_CODE=?,ZIP=?,NAME=?, ADDRESSLINE1 = ?, ADDRESSLINE2 = ?, CITY = ?, STATE = ?, PHONE=?,FAX=?,EMAIL=?,CREDIT_LIMIT=? WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setString(1,dc);
            stmt.setInt(2,zip);
            stmt.setString(3,name);
            stmt.setString(4, address1);
            stmt.setString(5, address2);
            stmt.setString(6, city);
            stmt.setString(7, State);
            stmt.setString(8, Phone);
            stmt.setString(9, Fax);
            stmt.setString(10, Email);
            stmt.setInt(11,credit);
            stmt.setInt(12, id);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
    }

    public List<String> allEmails() throws SQLException {
        List<String> result = new LinkedList<>();
        String sql = "SELECT EMAIL FROM Customer";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String email = rs.getString("EMAIL");

                result.add(email);
            }
        }
        return result;
    }

    public List<Integer> listCustomerID() throws SQLException {
        List<Integer> list = new LinkedList();
        String sql = "SELECT Customer_ID FROM Customer AS Liste";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("Customer_ID"));
            }
        }
        return list;
    }
    
    public double costByClient(int id) throws SQLException{
        double res=0;
        String sql = "SELECT SUM(PRODUCT.PURCHASE_COST*PURCHASE_ORDER.QUANTITY) AS COUT FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID and PURCHASE_ORDER.CUSTOMER_ID=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    res = rs.getDouble("COUT");
                }
            }
        }
        return res;
    }
       
    public int quantityByClient(int id) throws SQLException{
        int res=0;
        String sql = "SELECT SUM(Quantity) AS NUMBER FROM Purchase_Order WHERE Customer_ID=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, id); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    res = rs.getInt("NUMBER");
                }
            }
        }
        return res;
    }
        
    public List<Order> ProductByClient(int id) throws SQLException{
        List<Order> list = new LinkedList();
        String sql = "SELECT * FROM Purchase_Order WHERE Customer_ID=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, id); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int ordernum= rs.getInt("ORDER_NUM");
                    int prod_id = rs.getInt("PRODUCT_ID");
                    int quantity = rs.getInt("Quantity");
                    float cost = rs.getFloat("SHIPPING_COST");
                    String sale_d = rs.getString("SALES_DATE");
                    String shipping_d = rs.getString("SHIPPING_DATE");
                    Order o = new Order(ordernum,id,prod_id,quantity,cost,sale_d,shipping_d);
                    list.add(o);
                }
            }
        }
        return list;
    }

    //Requetes pour les produits
    public int numberProduct() throws SQLException {
        int result = 0;
        // Une requête SQL paramétrée
        String sql = "SELECT COUNT(*) AS Number FROM Product";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) { // Tant qu'il y a des enregistrements
                result = rs.getInt("NUMBER");
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }
    
    public List<Product> AllProduct() throws SQLException{
        List<Product> result = new LinkedList();
        // Une requête SQL paramétrée
        String sql = "SELECT * FROM Product";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { // Tant qu'il y a des enregistrements
                Product p = new Product(rs.getInt("Product_ID"),
                            rs.getInt("MANUFACTURER_ID"),
                            rs.getString("PRODUCT_CODE"),
                            rs.getFloat("PURCHASE_COST"),
                            rs.getInt("QUANTITY_ON_HAND"),
                            rs.getFloat("MARKUP"),
                            rs.getBoolean("AVAILABLE"),
                            rs.getString("Description"));
                result.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }

    public Product findProduct(int productId) throws SQLException {
        Product result = null;

        String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, productId); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // est-ce qu'il y a un résultat ? (pas besoin de "while", 
                    // il y a au plus un enregistrement)
                    // On récupère les champs de l'enregistrement courant
                    result = new Product(productId,
                            rs.getInt("MANUFACTURER_ID"),
                            rs.getString("PRODUCT_CODE"),
                            rs.getFloat("PURCHASE_COST"),
                            rs.getInt("QUANTITY_ON_HAND"),
                            rs.getFloat("MARKUP"),
                            rs.getBoolean("AVAILABLE"),
                            rs.getString("Description"));
                }
            }
        }
        return result;
    }

    //Faut que le man_id soit dans MANUFACTURER et que le code soit dans PRODUCT_CODE
    public void insertProduct(int id, int man_id, String code, double Purchase, int quantity, double markup, boolean available, String Description) throws SQLException {
        // Une requête SQL paramétrée
        String sql = "INSERT INTO PRODUCT VALUES(?, ?, ?,?,?,?,?,?)";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setInt(1, id);
            stmt.setInt(2, man_id);
            stmt.setString(3, code);
            stmt.setDouble(4, Purchase);
            stmt.setInt(5, quantity);
            stmt.setDouble(6, markup);
            stmt.setBoolean(7, available);
            stmt.setString(8, Description);

            stmt.executeUpdate();
        }
    }

    public int deleteProduct(int id) throws Exception {

        // Une requête SQL paramétrée
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID= ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setInt(1, id);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    //Méthodes pour les bons de commandes
    public int numberDiscount() throws SQLException {
        int result = 0;
        // Une requête SQL paramétrée
        String sql = "SELECT COUNT(*) AS Number FROM Discount_Code";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) { // Tant qu'il y a des enregistrements
                result = rs.getInt("NUMBER");
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }

    public List<Discount> allCodes() throws SQLException {

        List<Discount> result = new LinkedList<>();

        String sql = "SELECT * FROM DISCOUNT_CODE ORDER BY DISCOUNT_CODE";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("DISCOUNT_CODE");
                float rate = rs.getFloat("RATE");
                Discount c = new Discount(id, rate);
                result.add(c);
            }
        }
        return result;
    }

    public int deleteDiscount_Code(String Code) throws Exception {

        // Une requête SQL paramétrée
        String sql = "DELETE FROM DISCOUNT_CODE AS AUX WHERE DISCOUNT_CODE= ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setString(1, Code);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    public int updateDiscount_Code(String code, Float taux) throws Exception {

        // Une requête SQL paramétrée
        String sql = "UPDATE APP.DISCOUNT_CODE SET RATE = ? WHERE DISCOUNT_CODE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setFloat(1, taux);
            stmt.setString(2, code);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    public int addDiscount_Code(String Code, float Taux) throws Exception {

        // Une requête SQL paramétrée
        String sql = "INSERT INTO DISCOUNT_CODE VALUES (?,?)";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setString(1, Code);
            stmt.setFloat(2, Taux);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    //Partie PURCHASE_ORDER
    
    public Order findOrder(int ordernum) throws SQLException {
        Order result = null;

        String sql = "SELECT * FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, ordernum); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // est-ce qu'il y a un résultat ? (pas besoin de "while", 
                    // il y a au plus un enregistrement)
                    // On récupère les champs de l'enregistrement courant
                    int c_id = rs.getInt("CUSTOMER_ID");
                    int p_id = rs.getInt("PRODUCT_ID");
                    int q = rs.getInt("QUANTITY");
                    float sp = rs.getFloat("SHIPPING_COST");
                    String sd = rs.getString("SALES_DATE");
                    String sh_d = rs.getString("SHIPPING_DATE");
                    result = new Order(ordernum,c_id,p_id,q,sp,sd,sh_d);
                }
            }
        }
        return result;
    }
    
    //c_id le customer ID doit déjà existé ainsi que le p_id qui est le product_ID
    public void insertOrder(int ordernum, int c_id, int p_id, int quantity, float shipping, String sale_d, String shipping_d, String Company) throws SQLException {
        // Une requête SQL paramétrée
        String sql = "INSERT INTO PURCHASE_ORDER VALUES(?, ?, ?,?,?,?,?,?)";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
                stmt.setInt(1, ordernum);
                stmt.setInt(2, c_id);
                stmt.setInt(3, p_id);
                stmt.setInt(4, quantity);
                stmt.setFloat(5, shipping);
                stmt.setString(6, sale_d);
                stmt.setString(7, shipping_d);
                stmt.setString(8, Company);

            stmt.executeUpdate();
        }
    }

    public int deleteOrder(int ordernum) throws Exception {

        // Une requête SQL paramétrée
        String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setInt(1, ordernum);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }
    
    public int updateOrder(int ordernum,int c_id, int p_id, int quantity,float shipp_cost, String sale_d, String shipp_d) throws SQLException{
        String sql = "UPDATE PURCHASE_ORDER SET ORDER_NUM=?,PRODUCT_ID=?,QUANTITY=?,SHIPPING_COST=?,SALES_DATE=?,SHIPPING_DATE=? WHERE CUSTOMER_ID=?;";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Définir la valeur du paramètre
            stmt.setInt(1, ordernum);
            stmt.setInt(2,p_id);
            stmt.setInt(3, quantity);
            stmt.setFloat(4,shipp_cost);
            stmt.setString(5,sale_d);
            stmt.setString(6,shipp_d);
            stmt.setInt(7,c_id);

            return stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
    }
    
    public int allOrder() throws SQLException {

        int result = 0;

        String sql = "SELECT COUNT(*) AS NUMBER FROM PURCHASE_ORDER";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) { // Tant qu'il y a des enregistrements
                result = rs.getInt("NUMBER");
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }
    
    public String ManbyProduct(int product) throws SQLException {
        String result = null;

        String sql = "SELECT NAME AS NOM \n" +
"    FROM MANUFACTURER \n" +
"        INNER JOIN PRODUCT \n" +
"            ON PRODUCT.MANUFACTURER_ID=MANUFACTURER.MANUFACTURER_ID and PRODUCT.PRODUCT_ID=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, product); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // est-ce qu'il y a un résultat ? (pas besoin de "while", 
                    // il y a au plus un enregistrement)
                    // On récupère les champs de l'enregistrement courant
                    result = rs.getString("NOM");
                }
            }
        }
        return result;
    }
    
    public int ProductPrice(int product) throws SQLException {
        int result = 0;

        String sql = "SELECT PURCHASE_COST AS COST FROM PRODUCT WHERE PRODUCT_ID = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, product); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // est-ce qu'il y a un résultat ? (pas besoin de "while", 
                    // il y a au plus un enregistrement)
                    // On récupère les champs de l'enregistrement courant
                    result = rs.getInt("COST");
                }
            }
        }
        return result;
    }
    
    public int maxOrderNum() throws SQLException {
        int result = 0;

        String sql = "SELECT MAX(ORDER_NUM) AS MAXI FROM PURCHASE_ORDER";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) { // Tant qu'il y a des enregistrements
                result = rs.getInt("MAXI");
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }
    //ADMINISTRATEUR
    
    
    //Classer par ordre alphabétique des noms des clients
    public Map<String, Double> CustomerCA() throws SQLException{
        Map<String, Double> result = new HashMap<>();
        // Une requête SQL paramétrée
        String sql = "SELECT CUSTOMER.\"NAME\" AS NAME,SUM(PRODUCT.PURCHASE_COST*PURCHASE_ORDER.QUANTITY) AS COUT \n" +
"    FROM PURCHASE_ORDER \n" +
"        INNER JOIN PRODUCT ON PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID \n" +
"            INNER JOIN CUSTOMER ON PURCHASE_ORDER.CUSTOMER_ID=CUSTOMER.CUSTOMER_ID\n" +
"                GROUP BY CUSTOMER.\"NAME\" \n" +
"";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { // Tant qu'il y a des enregistrements
                
                result.put(rs.getString("NAME"),rs.getDouble("COUT"));
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }
    
    //Classer par ordre alphabétique des etats
    public Map<String, Double> StateCA() throws SQLException{
        Map<String, Double> result = new HashMap();
        // Une requête SQL paramétrée
        String sql = "SELECT CUSTOMER.STATE AS STATE,SUM(PRODUCT.PURCHASE_COST*PURCHASE_ORDER.QUANTITY) AS COUT\n" +
"    FROM CUSTOMER INNER JOIN PURCHASE_ORDER ON CUSTOMER.CUSTOMER_ID=PURCHASE_ORDER.CUSTOMER_ID\n" +
"        INNER JOIN PRODUCT ON PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID\n" +
"            GROUP BY CUSTOMER.STATE";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { // Tant qu'il y a des enregistrements
                
                result.put(rs.getString("STATE"),rs.getDouble("COUT"));
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }
    
    //Classer par ordre alphabétique des description.
    public Map<String, Double> ProductCA() throws SQLException{
        Map<String, Double> result = new HashMap();
        // Une requête SQL paramétrée
        String sql = "SELECT PRODUCT_CODE.DESCRIPTION AS DESCRIPTION,SUM(PRODUCT.PURCHASE_COST*PURCHASE_ORDER.QUANTITY) AS COUT\n" +
"    FROM PRODUCT_CODE INNER JOIN PRODUCT ON PRODUCT.PRODUCT_CODE=PRODUCT_CODE.PROD_CODE \n" +
"        INNER JOIN PURCHASE_ORDER ON PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID\n" +
"            GROUP BY PRODUCT_CODE.DESCRIPTION";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { // Tant qu'il y a des enregistrements
        
                result.put(rs.getString("DESCRIPTION"),rs.getDouble("COUT"));
            }

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
        return result;
    }
}

package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.DrinkkiRaakaAine;
import tikape.runko.domain.RaakaAine;

public class DrinkkiRaakaAineDao {
    
    private Database database;
    
    public DrinkkiRaakaAineDao(Database database) {
        this.database = database;
    }

//    public DrinkkiRaakaAine findOne(Integer key) throws SQLException {
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DrinkkiRaakaAine WHERE id = ?");
//        stmt.setObject(1, key);
//
//        ResultSet rs = stmt.executeQuery();
//        boolean hasOne = rs.next();
//        if (!hasOne) {
//            return null;
//        }
//
//        Integer id = rs.getInt("id");
//        String nimi = rs.getString("nimi");
//
//        DrinkkiRaakaAine dra = new DrinkkiRaakaAine(id, nimi);
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return dra;
//    }
//    public List<DrinkkiRaakaAine> findAll() throws SQLException {
//
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DrinkkiRaakaAine");
//
//        ResultSet rs = stmt.executeQuery();
//
//        List<DrinkkiRaakaAine> raakaAineet = new ArrayList<>();
//
//        while (rs.next()) {
//            Integer id = rs.getInt("id");
//            String nimi = rs.getString("nimi");
//
//            raakaAineet.add(new DrinkkiRaakaAine(id, nimi));
//        }
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return raakaAineet;
//    }
//    public void delete(Integer key) throws SQLException {
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Drinkki WHERE id = ?");
//        stmt.setInt(1, key);
//        stmt.executeUpdate();
//
//        stmt.close();
//        connection.close();
//    }
    public List<DrinkkiRaakaAine> getByDrinkki(Drinkki drinkki) throws SQLException {
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DrinkkiRaakaAine"
                + " WHERE drinkki_id = ?");
        stmt.setInt(1, drinkki.getId());
        ResultSet rs = stmt.executeQuery();
        
        List<DrinkkiRaakaAine> dras = new ArrayList();
        
        while (rs.next()) {
            Integer drinkkiId = rs.getInt("drinkki_id");
            Integer raakaAineId = rs.getInt("raakaaine_id");
            Integer jarjestys = rs.getInt("jarjestys");
            Double maara = rs.getDouble("maara");
            String yksikko = rs.getString("yksikko");
            String ohje = rs.getString("ohje");
            
            RaakaAine raakaAine = raakaAineDao.findOne(raakaAineId);
            dras.add(new DrinkkiRaakaAine(drinkki, raakaAine, jarjestys, maara, yksikko, ohje));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return dras;
    }
    
    public DrinkkiRaakaAine getByDrinkkiAndRaakaAine(Drinkki drinkki, RaakaAine raakaAine) throws SQLException {
        String query = "SELECT * FROM DrinkkiRaakaAine WHERE drinkki_id = ? AND raakaaine_id = ?";
        DrinkkiRaakaAine dra;
        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, drinkki.getId());
            stmt.setObject(2, raakaAine.getId());
            
            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }
            
            dra = new DrinkkiRaakaAine(drinkkiDao.findOne(rs.getInt("drinkki_id")),
                    raakaAineDao.findOne(rs.getInt("raakaaine_id")),
                    rs.getInt("jarjestys"), rs.getDouble("maara"), 
                    rs.getString("yksikko"), rs.getString("ohje"));
        }
        return dra;
    }
    
    public void saveOrUpdate(DrinkkiRaakaAine dra) throws SQLException {
        if (getByDrinkkiAndRaakaAine(dra.getDrinkki(), dra.getRaakaAine()) == null) {
            System.out.println("draDao -> saveOrUpdate: save " + dra.getRaakaAine().getNimi());
            save(dra);
        }
        System.out.println("draDao -> saveOrUpdate: update " + dra.getRaakaAine().getNimi());
        update(dra);
    }
    
    public void save(DrinkkiRaakaAine dra) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO DrinkkiRaakaAine"
                + "(drinkki_id, raakaaine_id, jarjestys, maara, yksikko, ohje) VALUES (?,?,?,?,?,?)");
        stmt.setInt(1, dra.getDrinkki().getId());
        stmt.setInt(2, dra.getRaakaAine().getId());
        stmt.setInt(3, dra.getJarjestys());
        stmt.setDouble(4, dra.getMaara());
        stmt.setString(5, dra.getYksikko());
        stmt.setString(6, dra.getOhje());
        stmt.executeUpdate();

//        ResultSet generatedKeys = stmt.getGeneratedKeys();
//        int id = generatedKeys.getInt(1);
        stmt.close();
        conn.close();

//        return findOne(id);
    }
    
    public DrinkkiRaakaAine update(DrinkkiRaakaAine dra) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE DrinkkiRaakaAine SET "
                + "jarjestys = ?, maara = ?, yksikko = ?, ohje = ? "
                + "WHERE drinkki_id = ? AND raakaaine_id = ?");
        stmt.setInt(1, dra.getJarjestys());
        stmt.setDouble(2, dra.getMaara());
        stmt.setString(3, dra.getYksikko());
        stmt.setString(4, dra.getOhje());
        stmt.setInt(5, dra.getDrinkki().getId());
        stmt.setInt(6, dra.getRaakaAine().getId());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
        return dra;
    }
}

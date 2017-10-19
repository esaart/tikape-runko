package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;

public class DrinkkiDao implements Dao<Drinkki, Integer> {

    private Database database;

    public DrinkkiDao(Database database) {
        this.database = database;
    }

    @Override
    public Drinkki findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Drinkki o = new Drinkki(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Drinkki> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki");

        ResultSet rs = stmt.executeQuery();

        List<Drinkki> drinkit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            drinkit.add(new Drinkki(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return drinkit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Drinkki WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public Drinkki saveOrUpdate(Drinkki object) throws SQLException {
        if (object.getId() == null) {
            return save(object);
        } else {
            return update(object);
        }
    }

    private Drinkki save(Drinkki drinkki) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Drinkki"
                + "(nimi) VALUES (?)");
        stmt.setString(1, drinkki.getNimi());
        stmt.executeUpdate();
        
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        int id = generatedKeys.getInt(1);
        
        stmt.close();
        conn.close();
        
        return findOne(id);
    }

    private Drinkki update(Drinkki drinkki) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Drinkki SET "
                + "nimi = ? WHERE id = ?");
        stmt.setString(1, drinkki.getNimi());
        stmt.setInt(2, drinkki.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
        return drinkki;
    }
}

package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;

public class DrinkkiDao implements Dao<Drinkki, Integer> {

    private final Database database;

    public DrinkkiDao(Database database) {
        this.database = database;
    }

    @Override
    public Drinkki findOne(Integer key) throws SQLException {
        String query = "SELECT * FROM Drinkki WHERE id = ?";
        Drinkki drinkki;

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }

            drinkki = new Drinkki(rs.getInt("id"), rs.getString("nimi"));
        }
        return drinkki;
    }

    @Override
    public List<Drinkki> findAll() throws SQLException {
        List<Drinkki> drinkit = new ArrayList<>();

        try (Connection conn = database.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Drinkki");

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nimi = rs.getString("nimi");

                drinkit.add(new Drinkki(id, nimi));
            }
        }
        return drinkit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String query = "DELETE FROM Drinkki WHERE id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
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
        String query = "INSERT INTO Drinkki (nimi) VALUES (?)";
        Drinkki searhced;

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, drinkki.getNimi());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int id = generatedKeys.getInt(1);
            searhced = findOne(id);
        }
        return searhced;
    }

    private Drinkki update(Drinkki drinkki) throws SQLException {
        String query = "UPDATE Drinkki SET nimi = ? WHERE id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, drinkki.getNimi());
            stmt.setInt(2, drinkki.getId());

            stmt.executeUpdate();
        }
        return drinkki;
    }
}

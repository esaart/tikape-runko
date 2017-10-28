package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.RaakaAine;

public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        String query = "SELECT * FROM RaakaAine WHERE id = ?";
        RaakaAine raakaAine;

        try (Connection connection = database.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }
            raakaAine = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        }
        return raakaAine;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {

        List<RaakaAine> raakaAineet = new ArrayList<>();;
        try (Connection connection = database.getConnection();
                Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM RaakaAine");

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nimi = rs.getString("nimi");

                raakaAineet.add(new RaakaAine(id, nimi));
            }
        }
        return raakaAineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String query = "DELETE FROM RaakaAine WHERE id = ?";

        try (Connection connection = database.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
    }

    @Override
    public RaakaAine saveOrUpdate(RaakaAine object) throws SQLException {
        if (object.getId() == null) {
            return save(object);
        } else {
            return update(object);
        }
    }

    private RaakaAine save(RaakaAine raakaAine) throws SQLException {
        String query = "INSERT INTO RaakaAine (nimi) VALUES (?)";
        RaakaAine searched;

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, raakaAine.getNimi());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int id = generatedKeys.getInt(1);
            searched = findOne(id);
        }
        return searched;
    }

    private RaakaAine update(RaakaAine raakaAine) throws SQLException {
        String query = "UPDATE RaakaAine SET nimi = ? WHERE id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, raakaAine.getNimi());
            stmt.setInt(2, raakaAine.getId());

            stmt.executeUpdate();
        }
        return raakaAine;
    }
}

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

    public List<DrinkkiRaakaAine> getByDrinkki(Drinkki drinkki) throws SQLException {
        String query = "SELECT * FROM DrinkkiRaakaAine WHERE drinkki_id = ?";
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        List<DrinkkiRaakaAine> dras = new ArrayList();

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, drinkki.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer raakaAineId = rs.getInt("raakaaine_id");
                Integer jarjestys = rs.getInt("jarjestys");
                Double maara = rs.getDouble("maara");
                String yksikko = rs.getString("yksikko");
                String ohje = rs.getString("ohje");

                RaakaAine raakaAine = raakaAineDao.findOne(raakaAineId);
                dras.add(new DrinkkiRaakaAine(drinkki, raakaAine, jarjestys, maara, yksikko, ohje));
            }
        }
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

    public DrinkkiRaakaAine saveOrUpdate(DrinkkiRaakaAine dra) throws SQLException {
        if (getByDrinkkiAndRaakaAine(dra.getDrinkki(), dra.getRaakaAine()) == null) {
            return save(dra);
        }
        return update(dra);
    }

    public DrinkkiRaakaAine save(DrinkkiRaakaAine dra) throws SQLException {
        String query = "INSERT INTO DrinkkiRaakaAine"
                + "(drinkki_id, raakaaine_id, jarjestys, maara, yksikko, ohje)"
                + " VALUES (?,?,?,?,?,?)";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dra.getDrinkki().getId());
            stmt.setInt(2, dra.getRaakaAine().getId());
            stmt.setInt(3, dra.getJarjestys());
            stmt.setDouble(4, dra.getMaara());
            stmt.setString(5, dra.getYksikko());
            stmt.setString(6, dra.getOhje());
            stmt.executeUpdate();
        }
        return dra;
    }

    public DrinkkiRaakaAine update(DrinkkiRaakaAine dra) throws SQLException {
        String query = "UPDATE DrinkkiRaakaAine "
                + "SET jarjestys = ?, maara = ?, yksikko = ?, ohje = ? "
                + "WHERE drinkki_id = ? AND raakaaine_id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dra.getJarjestys());
            stmt.setDouble(2, dra.getMaara());
            stmt.setString(3, dra.getYksikko());
            stmt.setString(4, dra.getOhje());
            stmt.setInt(5, dra.getDrinkki().getId());
            stmt.setInt(6, dra.getRaakaAine().getId());
            stmt.executeUpdate();
        }
        return dra;
    }

    public void delete(Integer drinkkiId, Integer raakaAineId) throws SQLException {
        String query = "DELETE FROM DrinkkiRaakaAine WHERE drinkki_id = ? "
                + "AND raakaaine_id = ?";

        try (Connection connection = database.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, drinkkiId);
            stmt.setInt(2, raakaAineId);
            stmt.executeUpdate();
        }
    }
}

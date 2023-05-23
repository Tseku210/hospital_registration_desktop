package model;

import java.sql.SQLException;
import java.util.List;

public interface NurseDAO {
    void addNurse(Nurse nurse) throws SQLException;

    void removeNurse(Nurse nurse) throws SQLException;

    List<Nurse> getNurses() throws SQLException;

    Nurse authenticate(String username, String password) throws SQLException;
}

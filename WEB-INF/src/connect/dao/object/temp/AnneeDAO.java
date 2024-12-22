package connect.dao.object.temp;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connect.dao.DAOExecuter;
import main.object.temp.Annee;

public class AnneeDAO {
    private Map<String, Connection> myConnections;
    private Class<?> c;

    public void setC(Class<?> c) {
        this.c = c;
    }

    public Class<?> getC() {
        return c;
    }

    public void setMyConnections(Map<String, Connection> myConnections) {
        Annee aa = new Annee();
        setC(aa.getClass());
        this.myConnections = myConnections;
    }

    public Map<String, Connection> getMyConnections() {
        return myConnections;
    }

    public Annee getAnneeById(int value){
        Map<String, Object> mo = new HashMap<>();
        mo.put("ID_ANNEE", value);
        DAOExecuter daoE = new DAOExecuter(getMyConnections());
        List<Object> answer = daoE.getFROM("mysql", "ANNEE", getC(), mo, true);
        boolean estNulle = false;
        if (answer.size() == 0) {
            estNulle = true;
        }
        
        return estNulle ? null : (Annee) answer.get(0);
    }

    public AnneeDAO(Map<String, Connection> mc){
        setMyConnections(mc);   
    }

}

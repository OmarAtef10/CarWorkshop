package Context;

import Service.IDataBaseService;

public class DBContext {
    private IDataBaseService dbService;
    private static DBContext dbContext;

    private DBContext(){}

    public static synchronized DBContext getDBContext(){
        if(dbContext==null){
            dbContext = new DBContext();
        }
        return dbContext;
    }

    public IDataBaseService getDbService() {
        return dbService;
    }

    public void setDbService(IDataBaseService dbService) {
        this.dbService = dbService;
    }
}

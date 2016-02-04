package pylypiv.errorGZ.dao;

public class Factory {
     
      private static PersonDAO personDAO = null;
      private static Factory instance = null;

      public static synchronized Factory getInstance(){
            if (instance == null){
              instance = new Factory();
            }
            return instance;
      }

      public PersonDAO getPersonDAO(){
            if (personDAO == null){
              personDAO = new PersonDAOImpl();
            }
            return personDAO;
      }  
}
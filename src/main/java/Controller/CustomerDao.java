package Controller;

import Context.DBContext;
import Model.Customer;

public class CustomerDao {

    public boolean addCustomer(Customer customer){
        return DBContext.getDBContext().getDbService().addCustomer(customer);
    }

    public Customer getCustomer(int customerId){
        return DBContext.getDBContext().getDbService().getCustomer(customerId);
    }

    public boolean deleteCustomer(int customerId){
        return DBContext.getDBContext().getDbService().deleteCustomer(customerId);
    }
}

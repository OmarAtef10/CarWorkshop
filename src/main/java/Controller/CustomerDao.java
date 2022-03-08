package Controller;

import Context.DBContext;
import Model.Customer;

public class CustomerDao {

    public boolean addCustomer(Customer customer){
        return DBContext.getDBContext().getDbService().addCustomer(customer);
    }

    public Customer getCustomer(String phone){
        return DBContext.getDBContext().getDbService().getCustomer(phone);
    }

    public boolean deleteCustomer(String phone){
        return DBContext.getDBContext().getDbService().deleteCustomer(phone);
    }


    public boolean updateCustomer(Customer customer){
        return DBContext.getDBContext().getDbService().updateCustomer(customer);
    }
}

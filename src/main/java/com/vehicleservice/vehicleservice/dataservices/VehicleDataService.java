package com.vehicleservice.vehicleservice.dataservices;

import com.vehicleservice.vehicleservice.managers.VehicleDataManager;
import com.vehicleservice.vehicleservice.models.database.Customer;
import com.vehicleservice.vehicleservice.models.database.RentedVehicle;
import com.vehicleservice.vehicleservice.models.database.Store;
import com.vehicleservice.vehicleservice.models.database.Vehicle;
import com.vehicleservice.vehicleservice.models.dto.RentDTO;
import com.vehicleservice.vehicleservice.models.dto.VehicleDTO;
import com.vehicleservice.vehicleservice.models.dto.VehicleStateDTO;
import com.vehicleservice.vehicleservice.models.resource.CustomerResource;
import com.vehicleservice.vehicleservice.models.resource.StateResource;
import com.vehicleservice.vehicleservice.models.resource.StoreResource;
import com.vehicleservice.vehicleservice.models.resource.VehicleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class VehicleDataService {

    @Autowired
    private VehicleDataManager vehicleDataManager;

    public List<StoreResource> readStores() {
        List<StoreResource> storeResources = new ArrayList<>();

        vehicleDataManager.readStores()
                .forEach(store -> storeResources.add(convertEntryToResource(store)));

        return storeResources;
    }

    public List<VehicleResource> readVehicles(String state) {
        List<VehicleResource> vehicleResources = new ArrayList<>();

        vehicleDataManager.readVehicles(state)
            .forEach(vehicle -> vehicleResources.add(convertEntryToResource(vehicle)));

        return vehicleResources;
    }

    public List<CustomerResource> readCustomers() {
        List<CustomerResource> customerResources = new ArrayList<>();

        vehicleDataManager.readCustomers()
                .forEach(customer -> customerResources.add(convertEntryToResource(customer)));

        return customerResources;
    }

    public StateResource createRent(RentDTO rentDTO) {
        try {
            vehicleDataManager.createRent(rentDTO.getCustomerID(), rentDTO.getVehicleID(), rentDTO.getEmployeeID(), rentDTO.getStartDate(), rentDTO.getEndDate());
            StateResource resource = new StateResource(200, "OK");
            return resource;
        }catch (Exception e){
            StateResource resource = new StateResource(500, "Internal Error");
            return resource;
        }
    }

    public StateResource updateVehicleState(VehicleStateDTO vehicleStateDTO) {
        try {
            vehicleDataManager.updateVehicleState(vehicleStateDTO.getVehicleID(), vehicleStateDTO.getState());
            StateResource resource = new StateResource(200, "OK");
            return resource;
        }catch(Exception e){
            StateResource resource = new StateResource(500, "Internal Error");
            return resource;
        }
    }

    //Converters
    private StoreResource convertEntryToResource(Store store) {
        StoreResource storeResource = new StoreResource();

        storeResource.setName(store.getName());

        return storeResource;
    }

    private CustomerResource convertEntryToResource(Customer customer) {
        CustomerResource customerResource = new CustomerResource();

        customerResource.setCustomerID(customer.getCustomerID());
        customerResource.setFirstName(customer.getFirstName());
        customerResource.setLastName(customer.getLastName());
        customerResource.setStreet(customer.getAddress().getStreet());
        customerResource.setZipcode(customer.getAddress().getZipcode());

        return customerResource;
    }

    private VehicleResource convertEntryToResource(Vehicle vehicle) {
        VehicleResource vehicleResource = new VehicleResource();

        vehicleResource.setVehicleID(vehicle.getVehicleID());
        vehicleResource.setVehicleName(vehicle.getName());
        vehicleResource.setCarSerialNumber(vehicle.getSerialNumber());
        vehicleResource.setVehicleTypeID(vehicle.getVehicleType().getVehicleTypeID());
        vehicleResource.setVehicleTypeName(vehicle.getVehicleType().getName());
        vehicleResource.setVehicleProducerID(vehicle.getProducer().getProducerID());
        vehicleResource.setVehicleProducerName(vehicle.getProducer().getName());
        vehicleResource.setBelongingStoreID(vehicle.getStore().getStoreID());
        vehicleResource.setVehiclePower(vehicle.getPower());

        return vehicleResource;
    }
}

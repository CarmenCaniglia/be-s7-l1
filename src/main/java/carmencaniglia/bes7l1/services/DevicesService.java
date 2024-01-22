package carmencaniglia.bes7l1.services;

import carmencaniglia.bes7l1.entities.Device;
import carmencaniglia.bes7l1.entities.State;
import carmencaniglia.bes7l1.exceptions.NotFoundException;
import carmencaniglia.bes7l1.payloads.devices.DeviceDTO;
import carmencaniglia.bes7l1.repositories.DevicesDAO;
import carmencaniglia.bes7l1.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class DevicesService {
    @Autowired
    private DevicesDAO devicesDAO;
    @Autowired
    private UsersDAO usersDAO;

    public Page<Device> getDevices(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return devicesDAO.findAll(pageable);
    }

    public Device save(DeviceDTO body){
        Device device = new Device();
        device.setCategory(body.category());
        device.setState(State.AVAILABLE);
        return devicesDAO.save(device);
    }

    public Device findById(long id){
        return devicesDAO.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    public void findByIdAndDelete (long id){
        Device found = this.findById(id);
        devicesDAO.delete(found);
    }

    public Device findByIdAndUpdate(long id, Device body){
        Device found = this.findById(id);
        found.setCategory(body.getCategory());
        found.setState(body.getState());
        return devicesDAO.save(found);
    }

    public Device assignDevice(long deviceId, long userId){
        Device device = devicesDAO.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
        device.setUser(usersDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId)));
        device.setState(State.ASSIGNED);
        return devicesDAO.save(device);
    }

    public Device setToMaintenance(long deviceId){
        Device device = findById(deviceId);
        device.setState(State.MAINTENANCE);
        return devicesDAO.save(device);
    }

    public Device setToDismissed(long deviceId){
        Device device = findById(deviceId);
        device.setState(State.DISMISSED);
        return devicesDAO.save(device);
    }

    public Page<Device> getDevicesByState(State state, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return devicesDAO.findByState(state, pageable);
    }

    public Page<Device> getDevicesByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return devicesDAO.findDevicesByUserId(userId, pageable);
    }
}

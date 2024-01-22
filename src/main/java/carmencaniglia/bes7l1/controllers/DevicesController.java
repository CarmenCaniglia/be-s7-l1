package carmencaniglia.bes7l1.controllers;

import carmencaniglia.bes7l1.entities.Device;
import carmencaniglia.bes7l1.exceptions.BadRequestException;
import carmencaniglia.bes7l1.payloads.devices.DeviceDTO;
import carmencaniglia.bes7l1.payloads.devices.DeviceResDTO;
import carmencaniglia.bes7l1.services.DevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")

public class DevicesController {
    @Autowired
    private DevicesService devicesService;

    @GetMapping
    public Page<Device> getDevice(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy){
        return devicesService.getDevices(page, size, orderBy);
    }
    @GetMapping("/{deviceId}")
    public Device getDeviceById(@PathVariable long deviceId){
        return devicesService.findById(deviceId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResDTO createDevice(@RequestBody @Validated DeviceDTO newDevicePayload, BindingResult validation){
        if(validation.hasErrors()) {
            throw new BadRequestException("Invalid device payload");
        }else{
            Device newDevice = devicesService.save(newDevicePayload);
            return new DeviceResDTO(newDevice.getId());
        }
    }

    @PutMapping("/{deviceId}")
    public Device updateDevice(@PathVariable long deviceId,@RequestBody Device updateDevicePayload){
        return devicesService.findByIdAndUpdate(deviceId, updateDevicePayload);
    }
    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAndDeleteUser(@PathVariable long deviceId){
        devicesService.findByIdAndDelete(deviceId);
    }
}

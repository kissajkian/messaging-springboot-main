package br.com.santander.app.service;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.infra.dataprovider.DevicesService;
import br.com.santander.infra.db.model.Devices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@AllArgsConstructor
public class DeviceService implements br.com.santander.domain.usecase.CreateOrUpdateDeviceUseCase {

    @Autowired
    private final DevicesService devicesService;

    @Override
    public Devices execute(DeviceContracted device) {
        return devicesService.saveDetails(mapping(device));
    }

    private Devices mapping(DeviceContracted dto){
        Devices devices = new Devices();
        devices.setDevice(dto.getDevice());
        devices.setReleasedate(Date.valueOf(dto.getReleaseDate()));
        devices.setClientname(dto.getClientName());
        return devices;
    }
}

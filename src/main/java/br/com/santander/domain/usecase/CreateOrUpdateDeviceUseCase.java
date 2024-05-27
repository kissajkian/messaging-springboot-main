package br.com.santander.domain.usecase;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.infra.db.model.Devices;

public interface CreateOrUpdateDeviceUseCase {

    public Devices execute(DeviceContracted device);

}
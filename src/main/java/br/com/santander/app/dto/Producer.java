package br.com.santander.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Producer {

    private String device;
    private boolean released;

    public Producer()
    {

    }

    public Producer(String device, boolean released)
    {
        this.device = device;
        this.released = released;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }
}
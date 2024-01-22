package carmencaniglia.bes7l1.payloads.devices;

import carmencaniglia.bes7l1.entities.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DeviceDTO(
        @NotEmpty(message = "Category cannot be empty")
        @Size(min = 3, max = 30, message = "Category must be between 3 e 30 chars")
        String category,
        State state){

}

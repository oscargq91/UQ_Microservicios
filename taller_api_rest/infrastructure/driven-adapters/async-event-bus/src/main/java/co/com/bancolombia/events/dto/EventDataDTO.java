package co.com.bancolombia.events.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EventDataDTO{

    private String summary;
    private String description;
    private String type;

}

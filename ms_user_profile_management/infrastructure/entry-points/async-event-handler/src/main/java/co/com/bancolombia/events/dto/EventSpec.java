package co.com.bancolombia.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EventSpec {
    protected String specVersion;
    protected String type;
    protected String source;
    protected String id;
    protected String time;
    protected String invoker;
    protected String dataContentType;
    protected EventDataDTO data;
}

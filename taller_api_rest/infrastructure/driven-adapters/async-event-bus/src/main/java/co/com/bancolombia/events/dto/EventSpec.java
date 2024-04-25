package co.com.bancolombia.events.dto;

import lombok.Data;

@Data
public class EventSpec {
    protected String type;
    protected String specVersion;
    protected String source;
    protected String id;
    protected String time;
    protected String dataContentType;
    protected String invoker;
    protected EventDataDTO data;
}

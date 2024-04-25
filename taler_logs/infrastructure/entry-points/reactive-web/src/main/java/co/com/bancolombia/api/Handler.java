package co.com.bancolombia.api;

import co.com.bancolombia.api.common.ValidationRequest;
import co.com.bancolombia.api.dto.LogDTO;
import co.com.bancolombia.model.log.Log;
import co.com.bancolombia.usecase.log.LogUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class Handler {
 private  final LogUseCase useCase;
 private final ValidationRequest validationRequest;
    private static final String ID = "application";

    @NonNull
    public Mono<ServerResponse> createLog(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LogDTO.class)
                .flatMap(validationRequest::validateData)
                .map(this::getLog)
                .flatMap(useCase::createLog)
                .map(this::getLogDTO)
                .flatMap(userResponseDTO -> ServerResponse.status(201).bodyValue(userResponseDTO));
    }
    @NonNull
    public Mono<ServerResponse> findAllLogs(ServerRequest serverRequest) {
        String numberHeader = getheader(serverRequest, "page-number");
        String sizeheader = getheader(serverRequest, "page-size");
        int number = Integer.parseInt(numberHeader==null ? "0" : numberHeader);
        int size = Integer.parseInt(sizeheader==null || sizeheader.equals("0") ? "9999" : sizeheader);
        LocalDateTime minDate =getLocalDateTimeHeader(serverRequest, "min-date");
        LocalDateTime maxDate =getLocalDateTimeHeader(serverRequest, "max-date");

        Pageable pageable = PageRequest.of(number, size, Sort.by("timestamp").ascending());
        return useCase.getLogs(minDate,maxDate,pageable)
                .map(this::getLogDTO)
                .collectList()
                .flatMap(logDTOS -> ServerResponse.ok().bodyValue(logDTOS));

    }
    @NonNull
    public Mono<ServerResponse> findAllLogsByApplication(ServerRequest serverRequest) {
        String application = serverRequest.pathVariable(ID);
        String numberHeader = getheader(serverRequest, "page-number");
        String sizeheader = getheader(serverRequest, "page-size");
        int number = Integer.parseInt(numberHeader==null ? "0" : numberHeader);
        int size = Integer.parseInt(sizeheader==null || sizeheader.equals("0") ? "9999" : sizeheader);
        LocalDateTime minDate =getLocalDateTimeHeader(serverRequest, "min-date");
        LocalDateTime maxDate =getLocalDateTimeHeader(serverRequest, "max-date");
        Pageable pageable = PageRequest.of(number, size, Sort.by("timestamp").ascending());
        return useCase.getLogsByApplication(application,minDate,maxDate,pageable)
                .map(this::getLogDTO)
                .collectList()
                .flatMap(logDTOS -> ServerResponse.ok().bodyValue(logDTOS));

    }

    public Log getLog(LogDTO logDTO){
        return Log.builder()
                .description(logDTO.getDescription())
                .summary(logDTO.getSummary())
                .timestamp(LocalDateTime.now())
                .type(logDTO.getType())
                .module(logDTO.getModule())
                .application(logDTO.getApplication())
                .build();
    }

    public LogDTO getLogDTO(Log log){
        return LogDTO.builder()
                .id(log.getId())
                .description(log.getDescription())
                .summary(log.getSummary())
                .timestamp(log.getTimestamp())
                .type(log.getType())
                .module(log.getModule())
                .application(log.getApplication())
                .build();
    }
    public static String getheader(ServerRequest serverRequest, String  headerName) {
        return serverRequest.headers().firstHeader(headerName);
    }

    public static LocalDateTime getLocalDateTimeHeader(ServerRequest serverRequest, String  headerName) {
        String headerValue = getheader(serverRequest, headerName);
        if (headerValue != null) {
            return LocalDateTime.parse(headerValue, DateTimeFormatter.ISO_DATE_TIME);
        } else if (headerName.equals("min-date")) {
            return LocalDateTime.now().minusYears(1);
        }else{
            return LocalDateTime.now().plusYears(1);
        }

    }

}

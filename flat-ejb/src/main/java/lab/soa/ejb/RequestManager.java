package lab.soa.ejb;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lab.soa.presentation.dto.responses.ProxyResponseDto;

@Singleton
@Startup
public class RequestManager {
    private static final Logger log = LoggerFactory.getLogger(RequestManager.class);
    private Client client;
    private String targetBaseUrl;

    @PostConstruct
    public void init() {
        try {
            targetBaseUrl = System.getenv().getOrDefault(
                "TARGET_SERVICE_BASE_URL",
                "http://localhost:33620"
            );
            client = ClientBuilder.newBuilder().build();
            log.info("RequestManager initialized, target={}", targetBaseUrl);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize RequestManager", e);
        }
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.close();
        }
    }

    public ProxyResponseDto executeGet(
        String path,
        Map<String, String[]> queryParams
    ) {
        String url = buildUrl(path);
        log.info("Proxy GET -> {}", url);
        WebTarget target = client.target(url);
        target = addQueryParams(target, queryParams);
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
        try (Response upstream = builder.get()) {
            return ProxyResponseDto.builder()
                .status(upstream.getStatus())
                .mediaType(
                    upstream.getMediaType() != null
                        ? upstream.getMediaType().toString()
                        : MediaType.APPLICATION_XML
                )
                .body(upstream.readEntity(String.class))
                .build();
        } catch (Exception e) {
            log.error("Upstream request failed", e);
            throw e;
        }
    }

    private String buildUrl(String path) {
        if (targetBaseUrl.endsWith("/") && path.startsWith("/")) {
            return targetBaseUrl.substring(0, targetBaseUrl.length() - 1) + path;
        }
        if (!targetBaseUrl.endsWith("/") && !path.startsWith("/")) {
            return targetBaseUrl + "/" + path;
        }
        return targetBaseUrl + path;
    }

    private WebTarget addQueryParams(
        WebTarget target,
        Map<String, String[]> queryParams
    ) {
        if (queryParams != null) {
            for (Map.Entry<String,String[]> entry: queryParams.entrySet()) {
                for (String value: entry.getValue()) {
                    target = target.queryParam(entry.getKey(), value);
                }
            }
        }
        return target;
    }
}

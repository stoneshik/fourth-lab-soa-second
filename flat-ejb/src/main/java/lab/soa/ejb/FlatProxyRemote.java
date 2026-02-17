package lab.soa.ejb;

import jakarta.ejb.Remote;
import lab.soa.presentation.dto.responses.ProxyResponseDto;

@Remote
public interface FlatProxyRemote {
    ProxyResponseDto proxyFindWithBalcony(
        String priceType,
        String balconyType
    );
    ProxyResponseDto proxyGetOrderedByTimeToMetro(
        String transportType,
        String sortType,
        Integer page,
        Integer size
    );
}

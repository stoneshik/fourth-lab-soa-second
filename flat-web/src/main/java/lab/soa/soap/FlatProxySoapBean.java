package lab.soa.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jws.WebService;
import lab.soa.ejb.FlatProxyRemote;
import lab.soa.ejb.RequestManager;
import lab.soa.presentation.dto.responses.ProxyResponseDto;
import lab.soa.soap.fault.ServiceFaultException;

@Stateless
@WebService(
    endpointInterface = "lab.soa.soap.FlatProxySoap",
    serviceName = "FlatProxyService"
)
public class FlatProxySoapBean implements FlatProxySoap {
    private static final Logger log = LoggerFactory.getLogger(RequestManager.class);

    @EJB(lookup = "java:global/flat-ejb-1.0.0/FlatProxyBean!lab.soa.ejb.FlatProxyRemote")
    private FlatProxyRemote proxyEjb;

    @Override
    public ProxyResponseDto findWithBalcony(
        String priceType,
        String balconyType
    ) throws ServiceFaultException {
        try {
            ProxyResponseDto response = proxyEjb.proxyFindWithBalcony(priceType, balconyType);
            log.info(String.format("findWithBalcony response from first: %s", response));
            return response;
        } catch (Exception e) {
            throw new ServiceFaultException("Internal server error");
        }
    }

    @Override
    public ProxyResponseDto getOrderedByTimeToMetro(
        String transportType,
        String sortType,
        Integer page,
        Integer size
    ) throws ServiceFaultException {
        try {
            ProxyResponseDto response = proxyEjb.proxyGetOrderedByTimeToMetro(
                transportType,
                sortType,
                page,
                size
            );
            log.info(String.format("getOrderedByTimeToMetro response from first: %s", response));
            return response;
        } catch (Exception e) {
            throw new ServiceFaultException("Internal server error");
        }
    }
}

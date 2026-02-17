package lab.soa.soap;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jws.WebService;
import lab.soa.ejb.FlatProxyRemote;
import lab.soa.presentation.dto.responses.ProxyResponseDto;
import lab.soa.soap.fault.ServiceFaultException;

@Stateless
@WebService(
    endpointInterface = "lab.soa.soap.FlatProxySoap",
    serviceName = "FlatProxyService"
)
public class FlatProxySoapBean implements FlatProxySoap {
    @EJB(lookup = "java:global/flat-ejb-1.0.0/FlatProxyBean!lab.soa.ejb.FlatProxyRemote")
    private FlatProxyRemote proxyEjb;

    @Override
    public ProxyResponseDto findWithBalcony(
        String priceType,
        String balconyType
    ) throws ServiceFaultException {
        try {
            return proxyEjb.proxyFindWithBalcony(priceType, balconyType);
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
            return proxyEjb.proxyGetOrderedByTimeToMetro(
                transportType,
                sortType,
                page,
                size
            );
        } catch (Exception e) {
            throw new ServiceFaultException("Internal server error");
        }
    }
}

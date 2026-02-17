package lab.soa.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import lab.soa.presentation.dto.responses.ProxyResponseDto;
import lab.soa.soap.fault.ServiceFaultException;

@WebService
public interface FlatProxySoap {
    @WebMethod
    ProxyResponseDto findWithBalcony(
        String priceType,
        String balconyType
    ) throws ServiceFaultException;

    @WebMethod
    ProxyResponseDto getOrderedByTimeToMetro(
        String transportType,
        String sortType,
        Integer page,
        Integer size
    ) throws ServiceFaultException;
}

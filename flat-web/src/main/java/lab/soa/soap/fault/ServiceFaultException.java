package lab.soa.soap.fault;

import jakarta.xml.ws.WebFault;

@WebFault(name = "ServiceFault")
public class ServiceFaultException extends Exception {
    private final SoapError fault;

    public ServiceFaultException(String message){
        super(message);
        this.fault = new SoapError(message, java.time.LocalDateTime.now().toString());
    }

    public SoapError getFaultInfo(){
        return fault;
    }
}

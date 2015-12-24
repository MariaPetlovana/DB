package me.az1.dblab.Common;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.rmi.RemoteException;

@WebService // Endpoint Interface
@SOAPBinding(style = SOAPBinding.Style.RPC) // Needed for the WSDL
public interface DatabaseControllerWeb extends DatabaseController {
    public String GetDatabase() throws RemoteException;
}

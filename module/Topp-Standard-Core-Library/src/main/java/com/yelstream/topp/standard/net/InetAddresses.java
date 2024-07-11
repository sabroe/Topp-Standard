package com.yelstream.topp.standard.net;

import com.yelstream.topp.standard.io.IOExceptions;
import com.yelstream.topp.standard.util.function.SupplierWithException;
import com.yelstream.topp.standard.util.function.SupplierWithExceptions;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@UtilityClass
public class InetAddresses {



    public static List<InetAddress> resolve(List<SupplierWithException<InetAddress, IOException>> elements) throws IOException {
        return SupplierWithExceptions.resolve(elements, IOExceptions::create);
    }

    public static List<InetAddress> resolveDistinct(List<SupplierWithException<InetAddress,IOException>> elements) throws IOException {
        return SupplierWithExceptions.resolveDistinct(elements,IOExceptions::create);
    }

    public static List<SupplierWithException<InetAddress,IOException>> fromList(List<InetAddress> addresses) {
        return SupplierWithExceptions.fromList(addresses);
    }

    public static List<SupplierWithException<InetAddress,IOException>> distinct(List<SupplierWithException<InetAddress,IOException>> elements) throws IOException {
        return SupplierWithExceptions.distinct(elements,IOExceptions::create);
    }
}

package org.mobicents.servers.diameter.location.data;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author <a href="mailto:aferreiraguido@gmail.com"> Alejandro Ferreira Guido </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SubscriberInformation {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberInformation.class);

    public ArrayList<SubscriberElement> subscribers = new ArrayList<>();

    SubscriberInformation() {
    }

    public static SubscriberInformation load() {
        try {
            String subscriberLocationDataFilename = "subscriber-location-data.json";
            String localSubscriberDataFullName = System.getProperty("user.dir") + "/" + subscriberLocationDataFilename;

            File file = new File(localSubscriberDataFullName);
            BufferedReader bufferedReader;
            if (file.exists()) {
                logger.info("Trying to load subscribers from '{}' local file.", localSubscriberDataFullName);
                bufferedReader = new BufferedReader(new FileReader(file));
            } else {
                logger.info("Loading subscribers from internal 'resources/{}' file.", subscriberLocationDataFilename);
                ClassLoader classLoader = SubscriberInformation.class.getClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream(subscriberLocationDataFilename);
                assert inputStream != null;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            }

            SubscriberInformation subscriberInformation = new Gson().fromJson(bufferedReader, SubscriberInformation.class);
            logger.info("Loaded {} records from location subscriber file.", subscriberInformation.subscribers.size());

            return subscriberInformation;
        } catch (Exception e){
            logger.warn("Subscriber location information load error!", e);
        }

        return new SubscriberInformation();
    }

    public SubscriberElement getElementBySubscriber(String imsi, String msisdn) throws Exception {

        if (msisdn.equalsIgnoreCase("0") || imsi.equalsIgnoreCase("0") )
            throw new Exception("ApplicationUnsupported");

        for (SubscriberElement subscriber: subscribers) {
            if (subscriber.imsi.equals(imsi) || subscriber.msisdn.equals(msisdn)) {
                if (imsi.isEmpty() && subscriber.msisdn.equals(msisdn)) {
                    return subscriber;
                } else if (msisdn.isEmpty() && subscriber.imsi.equals(imsi)) {
                    return subscriber;
                } else if (subscriber.imsi.equals(imsi) && subscriber.msisdn.equals(msisdn)) {
                    return subscriber;
                } else {
                    throw new Exception("SubscriberIncoherentData");
                }
            }
        }
        throw new Exception("SubscriberNotFound");
    }

    public String getUserDataBySubscriber(String msisdn) throws Exception {

        if (msisdn.equalsIgnoreCase("900000000001")) {
            throw new Exception("OperationNotAllowed");
        }
        if (msisdn.equalsIgnoreCase("0")) {
            throw new Exception("ApplicationUnsupported");
        }
        if (msisdn.equalsIgnoreCase("1234")) {
            throw new Exception("ShUserDataUnsupported");
        }
        try {
            String localSubscriberUserDataFile = System.getProperty("user.dir") + "/sh-user-data/" + msisdn + ".xml";
            File file = new File(localSubscriberUserDataFile);
            BufferedReader bufferedReader;
            if (file.exists()) {
                logger.info("Loading subscriber user data from '{}' local file.", localSubscriberUserDataFile);
                bufferedReader = new BufferedReader(new FileReader(file));
            } else {
                localSubscriberUserDataFile = "sh-user-data/" + msisdn + ".xml";
                logger.info("Loading subscriber user data from internal 'resources/{}' file.", localSubscriberUserDataFile);
                ClassLoader classLoader = SubscriberInformation.class.getClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream(localSubscriberUserDataFile);
                assert inputStream != null;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            }
            StringBuilder subscriberUserDataBuffer = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                subscriberUserDataBuffer.append(line).append("\n");
            }
            logger.info("Loaded {} bytes from subscriber user data file.", subscriberUserDataBuffer.length());

            return subscriberUserDataBuffer.toString();

        } catch (Exception e) {
            logger.warn("Subscriber information load error, not found!");
            throw new Exception("SubscriberNotFound");
        }
    }

}

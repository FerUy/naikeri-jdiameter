package org.mobicents.servers.diameter.location.data;

import org.mobicents.servers.diameter.location.data.elements.AdditionalLocationEstimate;
import org.mobicents.servers.diameter.location.data.elements.AdditionalServingNode;
import org.mobicents.servers.diameter.location.data.elements.LocationEstimate;
import org.mobicents.servers.diameter.location.data.elements.VelocityEstimate;
import org.mobicents.servers.diameter.location.data.elements.ServingNode;

/**
 * @author <a href="mailto:aferreiraguido@gmail.com"> Alejandro Ferreira Guido </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SubscriberElement {

    public String imsi;
    public String msisdn;
    public String lmsi;
    public ServingNode servingNode;
    public AdditionalServingNode additionalServingNode;
    public String gmlcAddress;
    public String pprAddress;
    public Long riaFlags;

    public Integer locationResult = 2001;

    public LocationEstimate locationEstimate;
    public AdditionalLocationEstimate addLocationEstimate;
    public Integer accuracyFulfilmentIndicator;
    public Long ageOfLocationEstimate;
    public VelocityEstimate velocityEstimate;
    public String eutranPositioningData;
    public String eutranCellGlobalIdentity;
    public String geranPositioningData;
    public String geranGanssPositioningData;
    public String cellGlobalIdentity;
    public String utranPositioningData;
    public String utranGanssPositioningData;
    public String utranAdditionalPositioningData;
    public String serviceAreaIdentity;
    public ServingNode targetServingNodeForHandover;
    public Long plaFlags;
    public String esmlcCellInfoEcgi;
    public Long esmlcCellInfoCpi;
    public String civicAddress;
    public Long barometricPressure;

    public Long lrrFlags;
    public Integer deferredMtLrDataLocationType;
    public Long deferredMtLrDataTerminationCause;
    public ServingNode deferredMtLrDataServingNode;

    public String imei;
    public String lcsEpsClientNameString;
    public Integer lcsEpsClientNameFormatInd;
    public Integer pseudonymIndicator;
    public Long lcsServiceTypeId;
    public Integer lcsQosClass;
    public Long reportingAmount;
    public Long reportingInterval;
    public String oneXRttRcid;
    public Long delayedLocationDataTerminationCause;
    public ServingNode delayedLocationDataServingNode;
    public String amfInstanceId;
}
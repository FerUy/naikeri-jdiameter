package org.mobicents.servers.diameter.location.points;

import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.ResultCode;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.slg.ServerSLgSession;
import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.common.impl.app.slg.LocationReportRequestImpl;
import org.jdiameter.common.impl.app.slg.ProvideLocationAnswerImpl;
import org.jdiameter.common.impl.app.slg.SLgSessionFactoryImpl;
import org.jdiameter.server.impl.app.slg.SLgServerSessionImpl;
import org.restcomm.protocols.ss7.map.api.MAPException;
import org.restcomm.protocols.ss7.map.api.service.lsm.AddGeographicalInformation;
import org.restcomm.protocols.ss7.map.api.service.lsm.GeranGANSSpositioningData;
import org.restcomm.protocols.ss7.map.api.service.lsm.PositioningDataInformation;
import org.restcomm.protocols.ss7.map.api.service.lsm.UtranAdditionalPositioningData;
import org.restcomm.protocols.ss7.map.api.service.lsm.UtranGANSSpositioningData;
import org.restcomm.protocols.ss7.map.api.service.lsm.UtranPositioningDataInfo;
import org.restcomm.protocols.ss7.map.api.service.lsm.VelocityType;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.TypeOfShape;
import org.restcomm.protocols.ss7.map.primitives.CellGlobalIdOrServiceAreaIdFixedLengthImpl;
import org.restcomm.protocols.ss7.map.service.lsm.AddGeographicalInformationImpl;
import org.restcomm.protocols.ss7.map.service.lsm.ExtGeographicalInformationImpl;
import org.restcomm.protocols.ss7.map.service.lsm.GeranGANSSpositioningDataImpl;
import org.restcomm.protocols.ss7.map.service.lsm.PositioningDataInformationImpl;
import org.restcomm.protocols.ss7.map.service.lsm.UtranAdditionalPositioningDataImpl;
import org.restcomm.protocols.ss7.map.service.lsm.UtranGANSSpositioningDataImpl;
import org.restcomm.protocols.ss7.map.service.lsm.UtranPositioningDataInfoImpl;
import org.restcomm.protocols.ss7.map.service.lsm.VelocityEstimateImpl;
import org.restcomm.protocols.ss7.map.service.mobility.subscriberInformation.EUtranCgiImpl;
import org.mobicents.servers.diameter.location.data.SubscriberElement;
import org.mobicents.servers.diameter.location.data.SubscriberInformation;
import org.mobicents.servers.diameter.location.data.elements.EllipsoidPoint;
import org.mobicents.servers.diameter.location.data.elements.PolygonImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static org.mobicents.servers.diameter.utils.TBCDUtil.parseTBCD;
import static org.mobicents.servers.diameter.utils.TBCDUtil.setAreaIdParams;
import static org.mobicents.servers.diameter.utils.TBCDUtil.toTBCDString;
import static org.mobicents.servers.diameter.utils.byteUtils.hexStringToByteArray;

/**
 * @author <a href="mailto:aferreiraguido@gmail.com"> Alejandro Ferreira Guido </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SLgReferencePoint extends SLgSessionFactoryImpl implements NetworkReqListener, EventListener<Request, Answer> {

    private static final Logger logger = LoggerFactory.getLogger(SLgReferencePoint.class);

    private static final int DIAMETER_ERROR_UNREACHABLE_USER = 4221;
    private static final int DIAMETER_ERROR_SUSPENDED_USER = 4222;
    private static final int DIAMETER_ERROR_DETACHED_USER = 4223;
    private static final int DIAMETER_ERROR_POSITIONING_DENIED = 4224;
    private static final int DIAMETER_ERROR_POSITIONING_FAILED = 4225;
    private static final int DIAMETER_ERROR_UNKNOWN_UNREACHABLE = 4226;
    private static final int DIAMETER_AVP_DELAYED_LOCATION_REPORTING_DATA = 2555;
    private static final int DIAMETER_ERROR_USER_UNKNOWN = 5001;
    private static final int DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK = 5490;

    private static final Object[] EMPTY_ARRAY = new Object[]{};

    private final SubscriberInformation subscriberInformation;

    public SLgReferencePoint(SubscriberInformation subscriberInformation) {
        super();

        this.subscriberInformation = subscriberInformation;
    }

    public Answer processRequest(Request request) {
        if (logger.isInfoEnabled()) {
            logger.info("<< Received SLg request [{}]", request);
        }

        try {
            ApplicationId slgAppId = ApplicationId.createByAuthAppId(0, this.getApplicationId());
            SLgServerSessionImpl session = sessionFactory.getNewAppSession(request.getSessionId(), slgAppId, ServerSLgSession.class, EMPTY_ARRAY);
            session.processRequest(request);
        } catch (InternalException e) {
            logger.error(">< Failure handling SLg received request [{}]", request, e);
        }

        return null;
    }

    public void receivedSuccessMessage(Request request, Answer answer) {
        if (logger.isInfoEnabled()) {
            logger.info("<< Received SLg message for request [{}] and answer [{}]", request, answer);
        }
    }

    public void timeoutExpired(Request request) {
        if (logger.isInfoEnabled()) {
            logger.info("<< Received SLg timeout for request [{}]", request);
        }
    }

    @Override
    public void doProvideLocationRequestEvent(ServerSLgSession session, ProvideLocationRequest plr)
        throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

        int resultCode = ResultCode.SUCCESS;

        String msisdn = "";
        String imsi = "";
        Integer lcsReferenceNumber = null;

        if (logger.isInfoEnabled()) {
            logger.info("<> Processing [PLR] Provide-Location-Request for request [{}] from {}@{} with session-id [{}]",
                plr, plr.getOriginHost(), plr.getOriginRealm(), session.getSessionId());
        }

        AvpSet plrAvpSet = plr.getMessage().getAvps();

        if (plr.getMessage().getAvps().getAvp(Avp.LCS_REFERENCE_NUMBER) != null)
            lcsReferenceNumber = plr.getMessage().getAvps().getAvp(Avp.LCS_REFERENCE_NUMBER).getInteger32();

        plrAvpSet.getAvp(Avp.SLG_LOCATION_TYPE).getInteger32();
        if (plrAvpSet.getAvp(Avp.USER_NAME) != null) {
            imsi = plrAvpSet.getAvp(Avp.USER_NAME).getUTF8String();
        }

        if (plrAvpSet.getAvp(Avp.MSISDN) != null) {
            msisdn = toTBCDString(plrAvpSet.getAvp(Avp.MSISDN).getOctetString());
        }

        if (logger.isInfoEnabled()) {
            logger.info("<> Generating [PLA] Provide-Location-Answer response data for MSISDN={}, IMSI={}", msisdn, imsi);
        }

        SubscriberElement subscriberElement = null;
        try {
            subscriberElement = subscriberInformation.getElementBySubscriber(imsi, msisdn);
            if (subscriberElement != null) {
                resultCode = subscriberElement.locationResult;
            } else {
                resultCode = DIAMETER_ERROR_UNREACHABLE_USER;
            }
        } catch (Exception e) {
            if (e.getMessage().equals("SubscriberIncoherentData"))
                resultCode = DIAMETER_ERROR_USER_UNKNOWN;
            if (e.getMessage().equals("SubscriberNotFound"))
                resultCode = DIAMETER_ERROR_USER_UNKNOWN;
            if (e.getMessage().equals("ApplicationUnsupported"))
                resultCode = ResultCode.APPLICATION_UNSUPPORTED;
        }

        ProvideLocationAnswer pla = new ProvideLocationAnswerImpl((Request) plr.getMessage(), resultCode);
        AvpSet plaAvpSet = pla.getMessage().getAvps();

        if (resultCode == ResultCode.SUCCESS || resultCode == DIAMETER_ERROR_POSITIONING_FAILED ||
            resultCode == DIAMETER_ERROR_POSITIONING_DENIED || resultCode == DIAMETER_ERROR_UNREACHABLE_USER) {
            try {
                if (subscriberElement != null) {
                    if (subscriberElement.locationEstimate != null) {
                        if (TypeOfShape.getInstance(subscriberElement.locationEstimate.typeOfShape) != TypeOfShape.Polygon) {
                            plaAvpSet.addAvp(Avp.LOCATION_ESTIMATE,
                                    new ExtGeographicalInformationImpl(TypeOfShape.getInstance(subscriberElement.locationEstimate.typeOfShape),
                                            subscriberElement.locationEstimate.latitude,
                                            subscriberElement.locationEstimate.longitude,
                                            subscriberElement.locationEstimate.uncertainty,
                                            subscriberElement.locationEstimate.uncertaintySemiMajorAxis,
                                            subscriberElement.locationEstimate.uncertaintySemiMinorAxis,
                                            subscriberElement.locationEstimate.angleOfMajorAxis,
                                            subscriberElement.locationEstimate.confidence,
                                            subscriberElement.locationEstimate.altitude,
                                            subscriberElement.locationEstimate.uncertaintyAltitude,
                                            subscriberElement.locationEstimate.innerRadius,
                                            subscriberElement.locationEstimate.uncertaintyInnerRadius,
                                            subscriberElement.locationEstimate.offsetAngle,
                                            subscriberElement.locationEstimate.includedAngle).getData(),10415, true, false);
                        } else if (TypeOfShape.getInstance(subscriberElement.locationEstimate.typeOfShape) == TypeOfShape.Polygon) {
                            EllipsoidPoint ellipsoidPoint1 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude1,
                                    subscriberElement.locationEstimate.longitude1);
                            EllipsoidPoint ellipsoidPoint2 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude2,
                                    subscriberElement.locationEstimate.longitude2);
                            EllipsoidPoint ellipsoidPoint3 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude3,
                                    subscriberElement.locationEstimate.longitude3);
                            EllipsoidPoint ellipsoidPoint4 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude4,
                                    subscriberElement.locationEstimate.longitude4);
                            EllipsoidPoint[] ellipsoidPoints = {ellipsoidPoint1, ellipsoidPoint2, ellipsoidPoint3, ellipsoidPoint4};
                            PolygonImpl polygon = new PolygonImpl();
                            polygon.setData(ellipsoidPoints);
                            AddGeographicalInformation locationEstimate = new AddGeographicalInformationImpl(polygon.getData());
                            plaAvpSet.addAvp(Avp.LOCATION_ESTIMATE, locationEstimate.getData(),10415, true, false);
                        }
                    }

                    if (subscriberElement.accuracyFulfilmentIndicator != null)
                        plaAvpSet.addAvp(Avp.ACCURACY_FULFILMENT_INDICATOR, subscriberElement.accuracyFulfilmentIndicator, 10415, true, false, true);

                    if (subscriberElement.ageOfLocationEstimate != null)
                        plaAvpSet.addAvp(Avp.AGE_OF_LOCATION_ESTIMATE, subscriberElement.ageOfLocationEstimate, 10415, true, false, true);

                    if (subscriberElement.velocityEstimate != null) {
                        plaAvpSet.addAvp(Avp.VELOCITY_ESTIMATE,
                                new VelocityEstimateImpl(VelocityType.getInstance(subscriberElement.velocityEstimate.velocityType),
                                        subscriberElement.velocityEstimate.horizontalSpeed,
                                        subscriberElement.velocityEstimate.bearing,
                                        subscriberElement.velocityEstimate.verticalSpeed,
                                        subscriberElement.velocityEstimate.uncertaintyHorizontalSpeed,
                                        subscriberElement.velocityEstimate.uncertaintyVerticalSpeed).getData(), 10415, true, false);
                    }

                    if (subscriberElement.eutranPositioningData != null) {
                        /* Positioning Data Set examples */
                        // 00000 001 (0x01) Cell ID; Attempted successfully: results not used to generate location - not used
                        // 00010 010 (0x12) E-CID; Attempted successfully: results used to verify but not generate location - not used
                        // 00100 011 (0x23) OTDOA; Attempted successfully: results used to generate location.
                        // 01000 100 (0x44) U-TDOA; Attempted successfully: case where UE supports multiple mobile based positioning methods and the actual method or methods used by the UE cannot be determined;
                        // 00001 000 (0x08) Reserved; Attempted unsuccessfully due to failure or interruption - not used;
                        /* GNSS Positioning Data Set examples */
                        // 01 000 011 (0x42) UE-Assisted; GPS; Attempted successfully: results used to generate location
                        // 00 001 001 (0x09) UE-Based; Galileo; Attempted successfully: results used to verify but not generate location
                        // 11 010 010 (0xd2) Reserved; SBAS; Attempted unsuccessfully due to failure or interruption
                        // 00 011 001 (0x19) UE-Based; Modernized GPS; Attempted successfully: results not used to generate location
                        // 11 100 000 (0xe0) Reserved; QZSS; Attempted unsuccessfully due to failure or interruption
                        // 00 101 100 (0x2c) UE-Based; GLONASS; Attempted successfully: case where UE supports multiple mobile based positioning methods and the actual method or methods used by the UE cannot be determined.
                        // 10 110 010 (0xb2) Conventional; BDS; Attempted successfully: results used to verify but not generate location
                        // 10 111 010 (0xba) Conventional; NavIC; Attempted successfully: results used to verify but not generate location
                        /* Additional Positioning Data Set examples */
                        // 10 000 001 (0x81) Standalone; Barometric Pressure; Attempted successfully: results not used to generate location
                        // 00 001 011 (0x0b) UE-Based; WLAN; Attempted successfully: results used to generate location
                        // 01 010 010 (0x52) UE-Assisted; Bluetooth; Attempted successfully: results used to verify but not generate location
                        // 11 011 000 (0xd8) Reserved; MBS; Attempted unsuccessfully due to failure or interruption
                        // 10 100 100 (0xa4) Standalone; Motion-Sensor(s); Attempted successfully: case where UE supports multiple mobile based positioning methods and the actual method or methods used by the UE cannot be determined.
                        plaAvpSet.addAvp(Avp.EUTRAN_POSITIONING_DATA, hexStringToByteArray(subscriberElement.eutranPositioningData), 10415, true, false);
                    }

                    if (subscriberElement.eutranCellGlobalIdentity != null) {
                        String[] ecgiArray = subscriberElement.eutranCellGlobalIdentity.split("-");
                        Integer[] ecgiParams = setAreaIdParams(ecgiArray, "eUtranCellId");
                        EUtranCgiImpl ecgi = new EUtranCgiImpl();
                        ecgi.setData(ecgiParams[0], ecgiParams[1], ecgiParams[2]);
                        plaAvpSet.addAvp(Avp.ECGI, ecgi.getData(), 10415, true, false);
                    }

                    if (subscriberElement.geranPositioningData != null || subscriberElement.geranGanssPositioningData != null) {
                        AvpSet geranPositioningInfo = plaAvpSet.addGroupedAvp(Avp.GERAN_POSITIONING_INFO, 10415, false, false);
                        if (subscriberElement.geranPositioningData != null) {
                            PositioningDataInformation geranPositioningDataInfo = new PositioningDataInformationImpl(hexStringToByteArray(subscriberElement.geranPositioningData));
                            geranPositioningInfo.addAvp(Avp.GERAN_POSITIONING_DATA, geranPositioningDataInfo.getData(), 10415, false, false);
                        }
                        if (subscriberElement.geranGanssPositioningData != null) {
                            GeranGANSSpositioningData geranGANSSPositioningData = new GeranGANSSpositioningDataImpl(hexStringToByteArray(subscriberElement.geranGanssPositioningData));
                            geranPositioningInfo.addAvp(Avp.GERAN_GANSS_POSITIONING_DATA, geranGANSSPositioningData.getData(), 10415, false, false);
                        }
                    }

                    if (subscriberElement.cellGlobalIdentity != null) {
                        String[] cgiArray = subscriberElement.cellGlobalIdentity.split("-");
                        Integer[] cgiParams = setAreaIdParams(cgiArray, "cellGlobalId");
                        CellGlobalIdOrServiceAreaIdFixedLengthImpl cgi = new CellGlobalIdOrServiceAreaIdFixedLengthImpl();
                        cgi.setData(cgiParams[0], cgiParams[1], cgiParams[2], cgiParams[3]);
                        plaAvpSet.addAvp(Avp.CELL_GLOBAL_IDENTITY, cgi.getData(), 10415, false, false);
                    }

                    if (subscriberElement.utranPositioningData != null || subscriberElement.utranGanssPositioningData != null ||
                            subscriberElement.utranAdditionalPositioningData != null) {
                        AvpSet utranPositioningInfo = plaAvpSet.addGroupedAvp(Avp.UTRAN_POSITIONING_INFO,10415, false, false);
                        if (subscriberElement.utranPositioningData != null) {
                            UtranPositioningDataInfo utranPositioningDataInfo = new UtranPositioningDataInfoImpl(hexStringToByteArray(subscriberElement.utranPositioningData));
                            utranPositioningInfo.addAvp(Avp.UTRAN_POSITIONING_DATA, utranPositioningDataInfo.getData(), 10415, false, false);

                        }
                        if (subscriberElement.utranGanssPositioningData != null) {
                            UtranGANSSpositioningData utranGANSSPositioningData = new UtranGANSSpositioningDataImpl(hexStringToByteArray(subscriberElement.utranGanssPositioningData));
                            utranPositioningInfo.addAvp(Avp.UTRAN_GANSS_POSITIONING_DATA, utranGANSSPositioningData.getData(), 10415, false, false);
                        }
                        if (subscriberElement.utranAdditionalPositioningData != null) {
                            UtranAdditionalPositioningData utranAdditionalPositioningData = new UtranAdditionalPositioningDataImpl(hexStringToByteArray(subscriberElement.utranAdditionalPositioningData));
                            utranPositioningInfo.addAvp(Avp.UTRAN_ADDITIONAL_POSITIONING_DATA, utranAdditionalPositioningData.getData(), 10415, false, false);
                        }
                    }

                    if (subscriberElement.serviceAreaIdentity != null) {
                        String[] saiArray = subscriberElement.serviceAreaIdentity.split("-");
                        Integer[] saiParams = setAreaIdParams(saiArray, "cellGlobalId");
                        CellGlobalIdOrServiceAreaIdFixedLengthImpl sai = new CellGlobalIdOrServiceAreaIdFixedLengthImpl();
                        sai.setData(saiParams[0], saiParams[1], saiParams[2], saiParams[3]);
                        plaAvpSet.addAvp(Avp.SERVICE_AREA_IDENTITY, sai.getData(), 10415, false, false);
                    }

                    if (subscriberElement.targetServingNodeForHandover != null) {
                        AvpSet targetServingNodeForHandover = plaAvpSet.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
                        if (subscriberElement.targetServingNodeForHandover.sgsnNumber != null)
                            targetServingNodeForHandover.addAvp(Avp.SGSN_NUMBER, parseTBCD(subscriberElement.targetServingNodeForHandover.sgsnNumber), 10415, true, false);
                        if (subscriberElement.targetServingNodeForHandover.sgsnName != null)
                            targetServingNodeForHandover.addAvp(Avp.SGSN_NAME, subscriberElement.targetServingNodeForHandover.sgsnName, 10415, false, false,false);
                        if (subscriberElement.targetServingNodeForHandover.sgsnRealm != null)
                            targetServingNodeForHandover.addAvp(Avp.SGSN_REALM, subscriberElement.targetServingNodeForHandover.sgsnRealm, 10415, false, false,false);
                        if (subscriberElement.targetServingNodeForHandover.mmeName != null)
                            targetServingNodeForHandover.addAvp(Avp.MME_NAME, subscriberElement.targetServingNodeForHandover.mmeName, 10415, true, false,false);
                        if (subscriberElement.targetServingNodeForHandover.mmeRealm != null)
                            targetServingNodeForHandover.addAvp(Avp.MME_REALM, subscriberElement.targetServingNodeForHandover.mmeRealm, 10415, false, false,false);
                        if (subscriberElement.targetServingNodeForHandover.mscNumber != null)
                            targetServingNodeForHandover.addAvp(Avp.MSC_NUMBER, parseTBCD(subscriberElement.targetServingNodeForHandover.mscNumber), 10415, true, false);
                        if (subscriberElement.targetServingNodeForHandover.tgppAAAServerName != null)
                            targetServingNodeForHandover.addAvp(Avp.TGPP_AAA_SERVER_NAME, subscriberElement.targetServingNodeForHandover.tgppAAAServerName, 10415, true, false,false);
                        if (subscriberElement.targetServingNodeForHandover.lcsCapabilitySets != null)
                            targetServingNodeForHandover.addAvp(Avp.LCS_CAPABILITIES_SETS, subscriberElement.targetServingNodeForHandover.lcsCapabilitySets, 10415, true, false, true);
                        if (subscriberElement.targetServingNodeForHandover.gmlcAddress != null) {
                            // IPv4 or IPv6 address of H-GMLC or the V-GMLC associated with the serving node
                            InetAddress servingNodeGmlcAddress = null;
                            try {
                                servingNodeGmlcAddress = InetAddress.getByName(subscriberElement.targetServingNodeForHandover.gmlcAddress);
                            } catch (UnknownHostException e) {
                                logger.error("Unknown host exception when trying to get gmlc address", e);
                            }
                            targetServingNodeForHandover.addAvp(Avp.GMLC_ADDRESS, servingNodeGmlcAddress, 10415, true, false);
                        }
                    }

                    if (subscriberElement.plaFlags != null)
                        plaAvpSet.addAvp(Avp.PLA_FLAGS, subscriberElement.plaFlags, 10415, false, false, true);

                    if (subscriberElement.esmlcCellInfoEcgi != null) {
                        String[] esmlcEcgiArray = subscriberElement.esmlcCellInfoEcgi.split("-");
                        Integer[] ecgiParams = setAreaIdParams(esmlcEcgiArray, "eUtranCellId");
                        EUtranCgiImpl ecgi = new EUtranCgiImpl();
                        ecgi.setData(ecgiParams[0], ecgiParams[1], ecgiParams[2]);
                        if (subscriberElement.esmlcCellInfoCpi != null) {
                            AvpSet esmlcCellInfo = plaAvpSet.addGroupedAvp(Avp.ESMLC_CELL_INFO, 10415, false, false);
                            esmlcCellInfo.addAvp(Avp.ECGI, ecgi.getData(), 10415, false, false);
                            esmlcCellInfo.addAvp(Avp.CELL_PORTION_ID, subscriberElement.esmlcCellInfoCpi, 10415, false, false, true);
                        } else {
                            plaAvpSet.addAvp(Avp.ECGI, ecgi.getData(), 10415, false, false);
                        }
                    }

                    if (subscriberElement.civicAddress != null)
                        plaAvpSet.addAvp(Avp.CIVIC_ADDRESS, subscriberElement.civicAddress, 10415, false, false, false);

                    if (subscriberElement.barometricPressure != null)
                        plaAvpSet.addAvp(Avp.BAROMETRIC_PRESSURE, subscriberElement.barometricPressure, 10415, false, false, true);
                }

            } catch (MAPException e) {
                logger.info(">< MAP Exception while generating SLg Provide-Location-Answer (PLA)\n", e);
            } catch (Exception e) {
                logger.info(">< Exception while generating SLg Provide-Location-Answer (PLA)\n", e);
            }
        }

        if (resultCode == DIAMETER_ERROR_USER_UNKNOWN || resultCode == DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK ||
            resultCode == DIAMETER_ERROR_UNREACHABLE_USER || resultCode == DIAMETER_ERROR_SUSPENDED_USER ||
            resultCode == DIAMETER_ERROR_DETACHED_USER || resultCode == DIAMETER_ERROR_POSITIONING_DENIED ||
            resultCode == DIAMETER_ERROR_POSITIONING_FAILED || resultCode == DIAMETER_ERROR_UNKNOWN_UNREACHABLE) {
            // remove Result_Code AVP and add Experimental-Result-Code AVP
            plaAvpSet.removeAvp(Avp.RESULT_CODE);
            plaAvpSet.addAvp(Avp.AUTH_SESSION_STATE, 0, 0, true, false, true);
            AvpSet experimentalResult = plaAvpSet.addGroupedAvp(Avp.EXPERIMENTAL_RESULT, true, false);
            experimentalResult.addAvp(Avp.EXPERIMENTAL_RESULT_CODE, resultCode, true, true);
            experimentalResult.addAvp(Avp.VENDOR_ID, 10415, true, false);
            if (lcsReferenceNumber != null)
                logger.info(">> Sending [PLA] Provide-Location-Answer with LCS-Reference-Number:{} to {}@{} and result code:{} ({})\n",
                    lcsReferenceNumber, plr.getOriginHost(), plr.getOriginRealm(), resultCode, getSLgExperimentalResultString(resultCode));
            else
                logger.info(">> Sending [PLA] Provide-Location-Answer to {}@{} with experimental result code:{} ({})\n",
                    plr.getOriginHost(), plr.getOriginRealm(), resultCode, getSLgExperimentalResultString(resultCode));
        } else if (resultCode == ResultCode.SUCCESS) {
            if (lcsReferenceNumber != null)
                logger.info(">> Sending [PLA] Provide-Location-Answer with LCS-Reference-Number:{} to {}@{} and result code:{} (SUCCESS)\n",
                    lcsReferenceNumber, plr.getOriginHost(), plr.getOriginRealm(), resultCode);
            else
                logger.info(">> Sending [PLA] Provide-Location-Answer to {}@{} with result code:{} (SUCCESS)\n",
                    plr.getOriginHost(), plr.getOriginRealm(), resultCode);
        } else {
            if (lcsReferenceNumber != null)
                logger.info(">> Sending [PLA] Provide-Location-Answer with LCS-Reference-Number:{} to {}@{} and result code:{}",
                    lcsReferenceNumber, plr.getOriginHost(), plr.getOriginRealm(), resultCode);
            else
                logger.info(">> Sending [PLA] Provide-Location-Answer to {}@{} with result code:{}\n",
                    plr.getOriginHost(), plr.getOriginRealm(), resultCode);
        }
        session.sendProvideLocationAnswer(pla);
    }

    public void sendLocationReportRequest(String subscriberIdentity, Integer locationEventType, Integer lcsReferenceNumber, Boolean isImsi) {

        if (logger.isInfoEnabled()) {
            logger.info("<< Received HTTP request for sending SLg [LRR] Location-Report-Request to GMLC");
            logger.info("<> Generating [LRR] Location-Report-Request data for sending to GMLC");
        }

        SubscriberElement subscriberElement;
        try {

            if (isImsi)
                subscriberElement = subscriberInformation.getElementBySubscriber(subscriberIdentity, "");
            else
                subscriberElement = subscriberInformation.getElementBySubscriber("", subscriberIdentity);

            String sessionId = UUID.randomUUID().toString();
            ServerSLgSession session = (this.sessionFactory).getNewAppSession(sessionId,
                   ApplicationId.createByAuthAppId(10415, 16777255), ServerSLgSession.class, (Object) null);

            LocationReportRequest lrr = new LocationReportRequestImpl(session.getSessions().get(0).createRequest(LocationReportRequest.code,
                    ApplicationId.createByAuthAppId(10415, 16777255), "restcomm.org"));

            AvpSet lrrAvpSet = lrr.getMessage().getAvps();

            if (locationEventType != null)
                lrrAvpSet.addAvp(Avp.LOCATION_EVENT, locationEventType, 10415, true, false, true);

            if (subscriberElement.imsi != null)
                lrrAvpSet.addAvp(Avp.USER_NAME, subscriberElement.imsi, true, false, false);

            if (subscriberElement.msisdn != null)
                lrrAvpSet.addAvp(Avp.MSISDN, parseTBCD(subscriberElement.msisdn), 10415, true, false);

            if (subscriberElement.imei != null)
                lrrAvpSet.addAvp(Avp.TGPP_IMEI, subscriberElement.imei, 10415, true, false, false);

            if (subscriberElement.lcsEpsClientNameString != null && subscriberElement.lcsEpsClientNameFormatInd != null) {
                AvpSet lcsEpsClientName = lrrAvpSet.addGroupedAvp(Avp.LCS_EPS_CLIENT_NAME, 10415, true, false);
                lcsEpsClientName.addAvp(Avp.LCS_NAME_STRING, subscriberElement.lcsEpsClientNameString, 10415, true, false, false);
                lcsEpsClientName.addAvp(Avp.LCS_FORMAT_INDICATOR, subscriberElement.lcsEpsClientNameFormatInd, 10415, true, false, true);
            }

            if (subscriberElement.locationEstimate != null) {
                if (TypeOfShape.getInstance(subscriberElement.locationEstimate.typeOfShape) != TypeOfShape.Polygon) {
                    lrrAvpSet.addAvp(Avp.LOCATION_ESTIMATE,
                        new ExtGeographicalInformationImpl(TypeOfShape.getInstance(subscriberElement.locationEstimate.typeOfShape),
                            subscriberElement.locationEstimate.latitude,
                            subscriberElement.locationEstimate.longitude,
                            subscriberElement.locationEstimate.uncertainty,
                            subscriberElement.locationEstimate.uncertaintySemiMajorAxis,
                            subscriberElement.locationEstimate.uncertaintySemiMinorAxis,
                            subscriberElement.locationEstimate.angleOfMajorAxis,
                            subscriberElement.locationEstimate.confidence,
                            subscriberElement.locationEstimate.altitude,
                            subscriberElement.locationEstimate.uncertaintyAltitude,
                            subscriberElement.locationEstimate.innerRadius,
                            subscriberElement.locationEstimate.uncertaintyInnerRadius,
                            subscriberElement.locationEstimate.offsetAngle,
                            subscriberElement.locationEstimate.includedAngle).getData(), 10415, true, false);
                }  else if (TypeOfShape.getInstance(subscriberElement.locationEstimate.typeOfShape) == TypeOfShape.Polygon) {
                        EllipsoidPoint ellipsoidPoint1 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude1,
                            subscriberElement.locationEstimate.longitude1);
                        EllipsoidPoint ellipsoidPoint2 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude2,
                            subscriberElement.locationEstimate.longitude2);
                        EllipsoidPoint ellipsoidPoint3 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude3,
                            subscriberElement.locationEstimate.longitude3);
                        EllipsoidPoint ellipsoidPoint4 = new EllipsoidPoint(subscriberElement.locationEstimate.latitude4,
                            subscriberElement.locationEstimate.longitude4);
                        EllipsoidPoint[] ellipsoidPoints = {ellipsoidPoint1, ellipsoidPoint2, ellipsoidPoint3, ellipsoidPoint4};
                        PolygonImpl polygon = new PolygonImpl();
                        polygon.setData(ellipsoidPoints);
                        AddGeographicalInformation additionalLocationEstimate = new AddGeographicalInformationImpl(polygon.getData());
                        lrrAvpSet.addAvp(Avp.LOCATION_ESTIMATE, additionalLocationEstimate.getData(),10415, true, false);
                }
            }

            if (subscriberElement.accuracyFulfilmentIndicator != null)
                lrrAvpSet.addAvp(Avp.ACCURACY_FULFILMENT_INDICATOR, subscriberElement.accuracyFulfilmentIndicator, 10415, false, false, true);

            if (subscriberElement.ageOfLocationEstimate != null)
                lrrAvpSet.addAvp(Avp.AGE_OF_LOCATION_ESTIMATE, subscriberElement.ageOfLocationEstimate, 10415, false, false, true);

            if (subscriberElement.velocityEstimate != null) {
                lrrAvpSet.addAvp(Avp.VELOCITY_ESTIMATE,
                    new VelocityEstimateImpl(VelocityType.getInstance(subscriberElement.velocityEstimate.velocityType),
                        subscriberElement.velocityEstimate.horizontalSpeed,
                        subscriberElement.velocityEstimate.bearing,
                        subscriberElement.velocityEstimate.verticalSpeed,
                        subscriberElement.velocityEstimate.uncertaintyHorizontalSpeed,
                        subscriberElement.velocityEstimate.uncertaintyVerticalSpeed).getData(), 10415, false, false);
            }

            if (subscriberElement.eutranPositioningData != null) {
                /* Positioning Data Set examples */
                // 00000 001 (0x01) Cell ID; Attempted successfully: results not used to generate location - not used
                // 00010 010 (0x12) E-CID; Attempted successfully: results used to verify but not generate location - not used
                // 00100 011 (0x23) OTDOA; Attempted successfully: results used to generate location.
                // 01000 100 (0x44) U-TDOA; Attempted successfully: case where UE supports multiple mobile based positioning methods and the actual method or methods used by the UE cannot be determined;
                // 00001 000 (0x08) Reserved; Attempted unsuccessfully due to failure or interruption - not used;
                /* GNSS Positioning Data Set examples */
                // 01 000 011 (0x42) UE-Assisted; GPS; Attempted successfully: results used to generate location
                // 00 001 001 (0x09) UE-Based; Galileo; Attempted successfully: results used to verify but not generate location
                // 11 010 010 (0xd2) Reserved; SBAS; Attempted unsuccessfully due to failure or interruption
                // 00 011 001 (0x19) UE-Based; Modernized GPS; Attempted successfully: results not used to generate location
                // 11 100 000 (0xe0) Reserved; QZSS; Attempted unsuccessfully due to failure or interruption
                // 00 101 100 (0x2c) UE-Based; GLONASS; Attempted successfully: case where UE supports multiple mobile based positioning methods and the actual method or methods used by the UE cannot be determined.
                // 10 110 010 (0xb2) Conventional; BDS; Attempted successfully: results used to verify but not generate location
                // 10 111 010 (0xba) Conventional; NavIC; Attempted successfully: results used to verify but not generate location
                /* Additional Positioning Data Set examples */
                // 10 000 001 (0x81) Standalone; Barometric Pressure; Attempted successfully: results not used to generate location
                // 00 001 011 (0x0b) UE-Based; WLAN; Attempted successfully: results used to generate location
                // 01 010 010 (0x52) UE-Assisted; Bluetooth; Attempted successfully: results used to verify but not generate location
                // 11 011 000 (0xd8) Reserved; MBS; Attempted unsuccessfully due to failure or interruption
                // 10 100 100 (0xa4) Standalone; Motion-Sensor(s); Attempted successfully: case where UE supports multiple mobile based positioning methods and the actual method or methods used by the UE cannot be determined.
                lrrAvpSet.addAvp(Avp.EUTRAN_POSITIONING_DATA, hexStringToByteArray(subscriberElement.eutranPositioningData), 10415, true, false);
            }

            if (subscriberElement.eutranCellGlobalIdentity != null) {
                String[] ecgiArray = subscriberElement.eutranCellGlobalIdentity.split("-");
                Integer[] ecgiParams = setAreaIdParams(ecgiArray, "eUtranCellId");
                EUtranCgiImpl ecgi = new EUtranCgiImpl();
                ecgi.setData(ecgiParams[0], ecgiParams[1], ecgiParams[2]);
                lrrAvpSet.addAvp(Avp.ECGI, ecgi.getData(), 10415, true, false);
            }

            if (subscriberElement.geranPositioningData != null || subscriberElement.geranGanssPositioningData != null) {
                AvpSet geranPositioningInfo = lrrAvpSet.addGroupedAvp(Avp.GERAN_POSITIONING_INFO, 10415, false, false);
                if (subscriberElement.geranPositioningData != null) {
                    PositioningDataInformation geranPositioningDataInfo = new PositioningDataInformationImpl(hexStringToByteArray(subscriberElement.geranPositioningData));
                    geranPositioningInfo.addAvp(Avp.GERAN_POSITIONING_DATA, geranPositioningDataInfo.getData(), 10415, false, false);
                }
                if (subscriberElement.geranGanssPositioningData != null) {
                    GeranGANSSpositioningData geranGANSSPositioningData = new GeranGANSSpositioningDataImpl(hexStringToByteArray(subscriberElement.geranGanssPositioningData));
                    geranPositioningInfo.addAvp(Avp.GERAN_GANSS_POSITIONING_DATA, geranGANSSPositioningData.getData(), 10415, false, false);
                }
            }

            if (subscriberElement.cellGlobalIdentity != null) {
                String[] cgiArray = subscriberElement.cellGlobalIdentity.split("-");
                Integer[] cgiParams = setAreaIdParams(cgiArray, "cellGlobalId");
                CellGlobalIdOrServiceAreaIdFixedLengthImpl cgi = new CellGlobalIdOrServiceAreaIdFixedLengthImpl();
                cgi.setData(cgiParams[0], cgiParams[1], cgiParams[2], cgiParams[3]);
                lrrAvpSet.addAvp(Avp.CELL_GLOBAL_IDENTITY, cgi.getData(), 10415, false, false);
            }

            if (subscriberElement.utranPositioningData != null || subscriberElement.utranGanssPositioningData != null ||
                    subscriberElement.utranAdditionalPositioningData != null) {
                AvpSet utranPositioningInfo = lrrAvpSet.addGroupedAvp(Avp.UTRAN_POSITIONING_INFO,10415, false, false);
                if (subscriberElement.utranPositioningData != null) {
                    UtranPositioningDataInfo utranPositioningDataInfo = new UtranPositioningDataInfoImpl(hexStringToByteArray(subscriberElement.utranPositioningData));
                    utranPositioningInfo.addAvp(Avp.UTRAN_POSITIONING_DATA, utranPositioningDataInfo.getData(), 10415, false, false);

                }
                if (subscriberElement.utranGanssPositioningData != null) {
                    UtranGANSSpositioningData utranGANSSPositioningData = new UtranGANSSpositioningDataImpl(hexStringToByteArray(subscriberElement.utranGanssPositioningData));
                    utranPositioningInfo.addAvp(Avp.UTRAN_GANSS_POSITIONING_DATA, utranGANSSPositioningData.getData(), 10415, false, false);
                }
                if (subscriberElement.utranAdditionalPositioningData != null) {
                    UtranAdditionalPositioningData utranAdditionalPositioningData = new UtranAdditionalPositioningDataImpl(hexStringToByteArray(subscriberElement.utranAdditionalPositioningData));
                    utranPositioningInfo.addAvp(Avp.UTRAN_ADDITIONAL_POSITIONING_DATA, utranAdditionalPositioningData.getData(), 10415, false, false);
                }
            }

            if (subscriberElement.serviceAreaIdentity != null) {
                String[] saiArray = subscriberElement.serviceAreaIdentity.split("-");
                Integer[] saiParams = setAreaIdParams(saiArray, "cellGlobalId");
                CellGlobalIdOrServiceAreaIdFixedLengthImpl sai = new CellGlobalIdOrServiceAreaIdFixedLengthImpl();
                sai.setData(saiParams[0], saiParams[1], saiParams[2], saiParams[3]);
                lrrAvpSet.addAvp(Avp.SERVICE_AREA_IDENTITY, sai.getData(), 10415, false, false);
            }

            if (subscriberElement.lcsServiceTypeId != null)
                lrrAvpSet.addAvp(Avp.LCS_SERVICE_TYPE_ID, subscriberElement.lcsServiceTypeId, 10415, true, false, true);

            if (subscriberElement.targetServingNodeForHandover != null) {
                AvpSet targetServingNodeForHandover = lrrAvpSet.addGroupedAvp(Avp.SERVING_NODE, 10415, false, false);
                if (subscriberElement.targetServingNodeForHandover.sgsnNumber != null)
                    targetServingNodeForHandover.addAvp(Avp.SGSN_NUMBER, parseTBCD(subscriberElement.targetServingNodeForHandover.sgsnNumber), 10415, true, false);
                if (subscriberElement.targetServingNodeForHandover.sgsnName != null)
                    targetServingNodeForHandover.addAvp(Avp.SGSN_NAME, subscriberElement.targetServingNodeForHandover.sgsnName, 10415, false, false,false);
                if (subscriberElement.targetServingNodeForHandover.sgsnRealm != null)
                    targetServingNodeForHandover.addAvp(Avp.SGSN_REALM, subscriberElement.targetServingNodeForHandover.sgsnRealm, 10415, false, false,false);
                if (subscriberElement.targetServingNodeForHandover.mmeName != null)
                    targetServingNodeForHandover.addAvp(Avp.MME_NAME, subscriberElement.targetServingNodeForHandover.mmeName, 10415, true, false,false);
                if (subscriberElement.targetServingNodeForHandover.mmeRealm != null)
                    targetServingNodeForHandover.addAvp(Avp.MME_REALM, subscriberElement.targetServingNodeForHandover.mmeRealm, 10415, false, false,false);
                if (subscriberElement.targetServingNodeForHandover.mscNumber != null)
                    targetServingNodeForHandover.addAvp(Avp.MSC_NUMBER, parseTBCD(subscriberElement.targetServingNodeForHandover.mscNumber), 10415, true, false);
                if (subscriberElement.targetServingNodeForHandover.tgppAAAServerName != null)
                    targetServingNodeForHandover.addAvp(Avp.TGPP_AAA_SERVER_NAME, subscriberElement.targetServingNodeForHandover.tgppAAAServerName, 10415, true, false,false);
                if (subscriberElement.targetServingNodeForHandover.lcsCapabilitySets != null)
                    targetServingNodeForHandover.addAvp(Avp.LCS_CAPABILITIES_SETS, subscriberElement.targetServingNodeForHandover.lcsCapabilitySets, 10415, true, false, true);
                if (subscriberElement.targetServingNodeForHandover.gmlcAddress != null) {
                    // IPv4 or IPv6 address of H-GMLC or the V-GMLC associated with the serving node
                    InetAddress servingNodeGmlcAddress = null;
                    try {
                        servingNodeGmlcAddress = InetAddress.getByName(subscriberElement.targetServingNodeForHandover.gmlcAddress);
                    } catch (UnknownHostException e) {
                        logger.error("Unknown host exception when trying to get gmlc address", e);
                    }
                    targetServingNodeForHandover.addAvp(Avp.GMLC_ADDRESS, servingNodeGmlcAddress, 10415, true, false);
                }
            }

            if (subscriberElement.lrrFlags != null)
                lrrAvpSet.addAvp(Avp.LRR_FLAGS, subscriberElement.lrrFlags, 10415, false, false, true);

            if (lcsReferenceNumber != null) {
                if (lcsReferenceNumber >= 0) {
                    lrrAvpSet.addAvp(Avp.LCS_REFERENCE_NUMBER, lcsReferenceNumber, 10415, false, false, true);
                    if (subscriberElement.deferredMtLrDataServingNode != null) {
                        AvpSet deferredMtLrData = lrrAvpSet.addGroupedAvp(Avp.DEFERRED_MT_LR_DATA, 10415, false, false);
                        if (subscriberElement.deferredMtLrDataLocationType != null) {
                            deferredMtLrData.addAvp(Avp.DEFERRED_LOCATION_TYPE, subscriberElement.deferredMtLrDataLocationType, 10415, false, false, true);
                        }
                        if (subscriberElement.deferredMtLrDataTerminationCause != null) {
                            deferredMtLrData.addAvp(Avp.TERMINATION_CAUSE_3GPP, subscriberElement.deferredMtLrDataTerminationCause, 10415, false, false, true);
                            if (subscriberElement.deferredMtLrDataTerminationCause == 4) {
                                AvpSet deferredMtLrDataServingNode = deferredMtLrData.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
                                if (subscriberElement.deferredMtLrDataServingNode.sgsnNumber != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.SGSN_NUMBER, parseTBCD(subscriberElement.deferredMtLrDataServingNode.sgsnNumber), 10415, false, false);
                                if (subscriberElement.deferredMtLrDataServingNode.sgsnName != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.SGSN_NAME, subscriberElement.deferredMtLrDataServingNode.sgsnName, 10415, false, false, false);
                                if (subscriberElement.deferredMtLrDataServingNode.sgsnRealm != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.SGSN_REALM, subscriberElement.deferredMtLrDataServingNode.sgsnRealm, 10415, false, false, false);
                                if (subscriberElement.deferredMtLrDataServingNode.mmeName != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.MME_NAME, subscriberElement.deferredMtLrDataServingNode.mmeName, 10415, false, false, false);
                                if (subscriberElement.deferredMtLrDataServingNode.mmeRealm != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.MME_REALM, subscriberElement.deferredMtLrDataServingNode.mmeRealm, 10415, false, false, false);
                                if (subscriberElement.deferredMtLrDataServingNode.mscNumber != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.MSC_NUMBER, parseTBCD(subscriberElement.deferredMtLrDataServingNode.mscNumber), 10415, false, false);
                                if (subscriberElement.deferredMtLrDataServingNode.tgppAAAServerName != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, subscriberElement.deferredMtLrDataServingNode.tgppAAAServerName, 10415, false, false, false);
                                if (subscriberElement.deferredMtLrDataServingNode.lcsCapabilitySets != null)
                                    deferredMtLrDataServingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, subscriberElement.deferredMtLrDataServingNode.lcsCapabilitySets, 10415, false, false, true);
                                if (subscriberElement.deferredMtLrDataServingNode.gmlcAddress != null) {
                                    // IPv4 or IPv6 address of H-GMLC or the V-GMLC associated with the deferred MT-LR data serving node
                                    InetAddress defMtLrpDataServingNodeGmlcAddress = null;
                                    try {
                                        defMtLrpDataServingNodeGmlcAddress = InetAddress.getByName(subscriberElement.deferredMtLrDataServingNode.gmlcAddress);
                                    } catch (UnknownHostException e) {
                                        logger.error("Unknown host exception when trying to get gmlc address", e);
                                    }
                                    deferredMtLrDataServingNode.addAvp(Avp.GMLC_ADDRESS, defMtLrpDataServingNodeGmlcAddress, 10415, true, false);
                                }
                            }
                        }
                    }

                    if (subscriberElement.reportingInterval != null && subscriberElement.reportingAmount != null) {
                        AvpSet periodicLdrInformation = lrrAvpSet.addGroupedAvp(Avp.PERIODIC_LDR_INFORMATION, 10415, false, false);
                        periodicLdrInformation.addAvp(Avp.REPORTING_INTERVAL, subscriberElement.reportingInterval, 10415, false, false, true);
                        periodicLdrInformation.addAvp(Avp.REPORTING_AMOUNT, subscriberElement.reportingAmount,10415, false, false, true);
                    }

                    if (subscriberElement.delayedLocationDataServingNode != null) {
                        AvpSet delayedLocationReportedData = lrrAvpSet.addGroupedAvp(DIAMETER_AVP_DELAYED_LOCATION_REPORTING_DATA, 10415, false, false);
                        if (subscriberElement.delayedLocationDataTerminationCause != null) {
                            delayedLocationReportedData.addAvp(Avp.TERMINATION_CAUSE_3GPP, subscriberElement.delayedLocationDataTerminationCause, 10415, false, false, true);
                            if (subscriberElement.delayedLocationDataTerminationCause == 4) {
                                AvpSet delayedLocationReportedDataServingNode = delayedLocationReportedData.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
                                if (subscriberElement.delayedLocationDataServingNode.sgsnNumber != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.SGSN_NUMBER, parseTBCD(subscriberElement.delayedLocationDataServingNode.sgsnNumber), 10415, false, false);
                                if (subscriberElement.delayedLocationDataServingNode.sgsnName != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.SGSN_NAME, subscriberElement.delayedLocationDataServingNode.sgsnName, 10415, false, false, false);
                                if (subscriberElement.delayedLocationDataServingNode.sgsnRealm != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.SGSN_REALM, subscriberElement.delayedLocationDataServingNode.sgsnRealm, 10415, false, false, false);
                                if (subscriberElement.delayedLocationDataServingNode.mmeName != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.MME_NAME, subscriberElement.delayedLocationDataServingNode.mmeName, 10415, false, false, false);
                                if (subscriberElement.delayedLocationDataServingNode.mmeRealm != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.MME_REALM, subscriberElement.delayedLocationDataServingNode.mmeRealm, 10415, false, false, false);
                                if (subscriberElement.delayedLocationDataServingNode.mscNumber != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.MSC_NUMBER, parseTBCD(subscriberElement.delayedLocationDataServingNode.mscNumber), 10415, false, false);
                                if (subscriberElement.delayedLocationDataServingNode.tgppAAAServerName != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, subscriberElement.delayedLocationDataServingNode.tgppAAAServerName, 10415, false, false, false);
                                if (subscriberElement.delayedLocationDataServingNode.lcsCapabilitySets != null)
                                    delayedLocationReportedDataServingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, subscriberElement.delayedLocationDataServingNode.lcsCapabilitySets, 10415, false, false, true);
                                if (subscriberElement.delayedLocationDataServingNode.gmlcAddress != null) {
                                    // IPv4 or IPv6 address of H-GMLC or the V-GMLC associated with the delayed location reported data serving node
                                    InetAddress delLocRepDataServingNodeGmlcAddress = null;
                                    try {
                                        delLocRepDataServingNodeGmlcAddress = InetAddress.getByName(subscriberElement.delayedLocationDataServingNode.gmlcAddress);
                                    } catch (UnknownHostException e) {
                                        logger.error("Unknown host exception when trying to get gmlc address", e);
                                    }
                                    delayedLocationReportedDataServingNode.addAvp(Avp.GMLC_ADDRESS, delLocRepDataServingNodeGmlcAddress, 10415, true, false);
                                }
                            }
                        }
                    }


                    if (subscriberElement.pseudonymIndicator != null)
                        lrrAvpSet.addAvp(Avp.PSEUDONYM_INDICATOR, subscriberElement.pseudonymIndicator, 10415, false, false,true);

                    if (subscriberElement.lcsQosClass != null)
                        lrrAvpSet.addAvp(Avp.LCS_QOS_CLASS, subscriberElement.lcsQosClass, 10415, false, false, true);
                }
            }

            if (subscriberElement.gmlcAddress != null) {
                // IPv4 or IPv6 address of the H-GMLC which should receive location estimates
                InetAddress gmlcAddress = null;
                try {
                    gmlcAddress = InetAddress.getByName(subscriberElement.gmlcAddress);
                } catch (UnknownHostException e) {
                    logger.error("Unknown host exception when trying to get gmlc address", e);
                }
                lrrAvpSet.addAvp(Avp.GMLC_ADDRESS, gmlcAddress, 10415, true, false);
            }

            if (subscriberElement.esmlcCellInfoEcgi != null && subscriberElement.esmlcCellInfoCpi != null) {
                AvpSet esmlcCellInfo = lrrAvpSet.addGroupedAvp(Avp.ESMLC_CELL_INFO, 10415, false, false);
                String[] esmlcEcgiArray = subscriberElement.esmlcCellInfoEcgi.split("-");
                Integer[] ecgiParams = setAreaIdParams(esmlcEcgiArray, "eUtranCellId");
                EUtranCgiImpl ecgi = new EUtranCgiImpl();
                ecgi.setData(ecgiParams[0], ecgiParams[1], ecgiParams[2]);
                esmlcCellInfo.addAvp(Avp.ECGI, ecgi.getData(), 10415, false, false);
                esmlcCellInfo.addAvp(Avp.CELL_PORTION_ID, subscriberElement.esmlcCellInfoCpi, 10415, false, false, true);
            }

            if (subscriberElement.oneXRttRcid != null)
                lrrAvpSet.addAvp(Avp.ONE_X_RTT_RCID, subscriberElement.oneXRttRcid, 10415, false, false, true);

            if (subscriberElement.civicAddress != null)
                lrrAvpSet.addAvp(Avp.CIVIC_ADDRESS, subscriberElement.civicAddress, 10415, false, false,true);

            if (subscriberElement.barometricPressure != null)
                lrrAvpSet.addAvp(Avp.BAROMETRIC_PRESSURE, subscriberElement.barometricPressure, 10415, false, false, true);

            if (subscriberElement.amfInstanceId != null)
                lrrAvpSet.addAvp(Avp.AMF_INSTANCE_ID, subscriberElement.amfInstanceId, 10415, false, false, true);

            if (logger.isInfoEnabled()) {
                if (lcsReferenceNumber != null)
                    logger.info(">> Sending [LRR] Location-Report-Request to GMLC for session-id [{}] and LCS-Reference-Number:{}\n",
                        session.getSessionId(), lcsReferenceNumber);
                else
                    logger.info(">> Sending [LRR] Location-Report-Request to GMLC for session-id [{}]\n", session.getSessionId());
            }

            session.sendLocationReportRequest(lrr);

        } catch (MAPException e) {
            logger.error(">< MAPException while generating SLg Location-Report-Request (LRR)", e);
        } catch (Exception e) {
            logger.info(">< Exception while generating SLg Location-Report-Request (LRR)\n", e);
        }

    }


    @Override
    public void doLocationReportAnswerEvent(ServerSLgSession session, LocationReportRequest lrr, LocationReportAnswer lra) {

        int resultCode = 2000, lraFlags = -1;
        String gmlcAddress = null, lcsReferenceNumber = null;
        Object reportingPlmnList = null;
        try {
            AvpSet lraAvpSet = lra.getMessage().getAvps();

            if (lraAvpSet != null) {

                if (lraAvpSet.getAvp(Avp.RESULT_CODE) != null)
                    resultCode = lraAvpSet.getAvp(Avp.RESULT_CODE).getInteger32();

                if (lraAvpSet.getAvp(Avp.GMLC_ADDRESS) != null)
                    gmlcAddress = lraAvpSet.getAvp(Avp.GMLC_ADDRESS).toString();

                if (lraAvpSet.getAvp(Avp.LRA_FLAGS) != null)
                    lraFlags = lraAvpSet.getAvp(Avp.LRA_FLAGS).getInteger32();

                if (lraAvpSet.getAvp(Avp.REPORTING_PLMN_LIST) != null)
                    reportingPlmnList = lraAvpSet.getAvp(Avp.REPORTING_PLMN_LIST);

                if (lraAvpSet.getAvp(Avp.LCS_REFERENCE_NUMBER) != null)
                    lcsReferenceNumber = lraAvpSet.getAvp(Avp.LCS_REFERENCE_NUMBER).toString();
            }

            if (logger.isInfoEnabled()) {
                logger.info("<< Received [LRA] Location-Report-Answer from {}@{} for request [{}] and session-id [{}; resultCode={}, lcsReferenceNumber={},, gmlcAddress={}, reportingPlmnList={}, LRA-Flags={}]\n",
                    lra.getOriginHost(), lra.getOriginRealm(), lrr, session.getSessionId(), resultCode, lcsReferenceNumber, gmlcAddress, reportingPlmnList, lraFlags);
            }

        } catch (Exception e) {
            logger.error(">< Got exception while processing [LRA] Location-Report-Answer", e);
        }
    }

    private String getSLgExperimentalResultString(int code) {
        String experimentalResultString = "";
        switch (code) {
            case DIAMETER_ERROR_USER_UNKNOWN:
                experimentalResultString = "DIAMETER_ERROR_USER_UNKNOWN";
                break;
            case DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK:
                experimentalResultString = "DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK";
                break;
            case DIAMETER_ERROR_UNREACHABLE_USER:
                experimentalResultString = "DIAMETER_ERROR_UNREACHABLE_USER";
                break;
            case DIAMETER_ERROR_SUSPENDED_USER:
                experimentalResultString = "DIAMETER_ERROR_SUSPENDED_USER";
                break;
            case DIAMETER_ERROR_DETACHED_USER:
                experimentalResultString = "DIAMETER_ERROR_DETACHED_USER";
                break;
            case DIAMETER_ERROR_POSITIONING_DENIED:
                experimentalResultString = "DIAMETER_ERROR_POSITIONING_DENIED";
                break;
            case DIAMETER_ERROR_POSITIONING_FAILED:
                experimentalResultString = "DIAMETER_ERROR_POSITIONING_FAILED";
                break;
            case DIAMETER_ERROR_UNKNOWN_UNREACHABLE:
                experimentalResultString = "DIAMETER_ERROR_UNKNOWN_UNREACHABLE";
                break;
        }
        return experimentalResultString;
    }

}

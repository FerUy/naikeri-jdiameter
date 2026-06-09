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
import org.jdiameter.api.slh.ServerSLhSession;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;
import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;
import org.jdiameter.common.impl.app.slh.LCSRoutingInfoAnswerImpl;
import org.jdiameter.common.impl.app.slh.SLhSessionFactoryImpl;
import org.jdiameter.server.impl.app.slh.SLhServerSessionImpl;
import org.mobicents.servers.diameter.location.data.SubscriberElement;
import org.mobicents.servers.diameter.location.data.SubscriberInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.mobicents.servers.diameter.utils.TBCDUtil.parseTBCD;
import static org.mobicents.servers.diameter.utils.TBCDUtil.toTBCDString;

/**
 * @author <a href="mailto:aferreiraguido@gmail.com"> Alejandro Ferreira Guido </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SLhReferencePoint extends SLhSessionFactoryImpl implements NetworkReqListener, EventListener<Request, Answer> {

    private static final Logger logger = LoggerFactory.getLogger(SLhReferencePoint.class);

    private static final int DIAMETER_ERROR_USER_UNKNOWN = 5001;
    private static final int DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK = 5490;
    private static final int DIAMETER_ERROR_ABSENT_USER = 4201;

    private static final Object[] EMPTY_ARRAY = new Object[]{};

    private final SubscriberInformation subscriberInformation;

    public SLhReferencePoint(SubscriberInformation subscriberInformation) {
        super();

        this.subscriberInformation = subscriberInformation;
    }

    public Answer processRequest(Request request) {
        if (logger.isInfoEnabled()) {
            logger.info("<< Received SLh request [{}]", request);
        }

        try {
            ApplicationId slhAppId = ApplicationId.createByAuthAppId(0, this.getApplicationId());
            SLhServerSessionImpl session = sessionFactory.getNewAppSession(request.getSessionId(), slhAppId, ServerSLhSession.class, EMPTY_ARRAY);
            session.processRequest(request);
        } catch (InternalException e) {
            logger.error(">< Failure handling SLh received request [{}]", request, e);
        }

        return null;
    }

    public void receivedSuccessMessage(Request request, Answer answer) {
        if (logger.isInfoEnabled()) {
            logger.info("<< Received SLh message for request [{}] and Answer [{}]", request, answer);
        }
    }

    public void timeoutExpired(Request request) {
        if (logger.isInfoEnabled()) {
            logger.info("<< Received SLh timeout for request [{}]", request);
        }
    }

    @Override
    public void doLCSRoutingInfoRequestEvent(ServerSLhSession session, LCSRoutingInfoRequest rir)
            throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

        int resultCode = ResultCode.SUCCESS;

        Long gmlcNumber = null;
        String msisdn = "", imsi = "";

        if (logger.isInfoEnabled()) {
            logger.info("<> Processing [RIR] Routing-Info-Request for request [{}] from {}@{} with session-id [{}]", rir, rir.getOriginHost(), rir.getOriginRealm(), session.getSessionId());
        }

        AvpSet rirAvpSet = rir.getMessage().getAvps();

        if (rirAvpSet.getAvp(Avp.USER_NAME) != null) {
            try {
                imsi = rirAvpSet.getAvp(Avp.USER_NAME).getUTF8String();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (rirAvpSet.getAvp(Avp.MSISDN) != null) {
            try {
                byte[] msisdnByteArray = rirAvpSet.getAvp(Avp.MSISDN).getOctetString();
                msisdn = toTBCDString(msisdnByteArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (rirAvpSet.getAvp(Avp.GMLC_NUMBER) != null) {
            try {
                byte[] gmlcNumberOctet = rirAvpSet.getAvp(Avp.GMLC_NUMBER).getOctetString();
                gmlcNumber = Long.valueOf(toTBCDString(gmlcNumberOctet));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (logger.isInfoEnabled()) {
            if (!msisdn.isEmpty()) {
                if (gmlcNumber != null)
                    logger.info("<> Generating [RIA] Routing-Info-Answer response data for MSISDN={}, GMLC-Number={}", msisdn, gmlcNumber);
                else
                    logger.info("<> Generating [RIA] Routing-Info-Answer response data for MSISDN={}", msisdn);
            } else {
                if (gmlcNumber != null)
                    logger.info("<> Generating [RIA] Routing-Info-Answer response data for IMSI={}, GMLC-Number={}", imsi, gmlcNumber);
                else
                    logger.info("<> Generating [RIA] Routing-Info-Answer response data for IMSI={}", imsi);
            }
        }

        SubscriberElement subscriberElement = null;
        try {
            subscriberElement = subscriberInformation.getElementBySubscriber(imsi, msisdn);
            if (subscriberElement == null) {
                logger.info("subscriberElement = subscriberInformation.getElementBySubscriber(imsi, msisdn) is NULL!!!");
                resultCode = DIAMETER_ERROR_USER_UNKNOWN;
            } else if (subscriberElement.locationResult == 5490)
                resultCode = DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK;
            else if (subscriberElement.locationResult == 4201)
                resultCode = DIAMETER_ERROR_ABSENT_USER;
        } catch (Exception e) {
            if (e.getMessage().equals("SubscriberIncoherentData"))
                resultCode = DIAMETER_ERROR_USER_UNKNOWN;
            if (e.getMessage().equals("SubscriberNotFound"))
                resultCode = DIAMETER_ERROR_USER_UNKNOWN;
            if (e.getMessage().equals("ApplicationUnsupported"))
                resultCode = ResultCode.APPLICATION_UNSUPPORTED;
        }

        LCSRoutingInfoAnswer ria = new LCSRoutingInfoAnswerImpl((Request) rir.getMessage(), resultCode);

        AvpSet riaAvpSet = ria.getMessage().getAvps();

        if (resultCode == ResultCode.SUCCESS) {

            if (subscriberElement != null) {

                if (subscriberElement.imsi != null)
                    riaAvpSet.addAvp(Avp.USER_NAME, subscriberElement.imsi, true, false, false);

                if (subscriberElement.msisdn != null)
                    riaAvpSet.addAvp(Avp.MSISDN, parseTBCD(subscriberElement.msisdn), 10415, true, false);

                if (subscriberElement.lmsi != null)
                    riaAvpSet.addAvp(Avp.LMSI, parseTBCD(subscriberElement.lmsi), 10415, true, false);

                if (subscriberElement.servingNode != null) {
                    AvpSet servingNode = riaAvpSet.addGroupedAvp(Avp.SERVING_NODE, 10415, false, false);
                    if (subscriberElement.servingNode.sgsnNumber != null)
                        servingNode.addAvp(Avp.SGSN_NUMBER, parseTBCD(subscriberElement.servingNode.sgsnNumber), 10415, true, false);
                    if (subscriberElement.servingNode.sgsnName != null)
                        servingNode.addAvp(Avp.SGSN_NAME, subscriberElement.servingNode.sgsnName, 10415, false, false,false);
                    if (subscriberElement.servingNode.sgsnRealm != null)
                        servingNode.addAvp(Avp.SGSN_REALM, subscriberElement.servingNode.sgsnRealm, 10415, false, false,false);
                    if (subscriberElement.servingNode.mmeName != null)
                        servingNode.addAvp(Avp.MME_NAME, subscriberElement.servingNode.mmeName, 10415, true, false,false);
                    if (subscriberElement.servingNode.mmeRealm != null)
                        servingNode.addAvp(Avp.MME_REALM, subscriberElement.servingNode.mmeRealm, 10415, false, false,false);
                    if (subscriberElement.servingNode.mscNumber != null)
                        servingNode.addAvp(Avp.MSC_NUMBER, parseTBCD(subscriberElement.servingNode.mscNumber), 10415, true, false);
                    if (subscriberElement.servingNode.tgppAAAServerName != null)
                        servingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, subscriberElement.servingNode.tgppAAAServerName, 10415, true, false,false);
                    if (subscriberElement.servingNode.lcsCapabilitySets != null)
                        servingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, subscriberElement.servingNode.lcsCapabilitySets, 10415, true, false, true);
                    if (subscriberElement.servingNode.gmlcAddress != null) {
                        // IPv4 or IPv6 address of H-GMLC or the V-GMLC associated with the serving node
                        InetAddress servingNodeGmlcAddress = null;
                        try {
                            servingNodeGmlcAddress = InetAddress.getByName(subscriberElement.servingNode.gmlcAddress);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        servingNode.addAvp(Avp.GMLC_ADDRESS, servingNodeGmlcAddress, 10415, true, false);
                    }
                }

                if (subscriberElement.additionalServingNode != null) {
                    AvpSet additionalServingNode = riaAvpSet.addGroupedAvp(Avp.ADDITIONAL_SERVING_NODE, 10415, false, false);
                    if (subscriberElement.additionalServingNode.sgsnNumber != null)
                        additionalServingNode.addAvp(Avp.SGSN_NUMBER, parseTBCD(subscriberElement.additionalServingNode.sgsnNumber), 10415, true, false);
                    if (subscriberElement.additionalServingNode.sgsnName != null)
                        additionalServingNode.addAvp(Avp.SGSN_NAME, subscriberElement.additionalServingNode.sgsnName, 10415, false, false,false);
                    if (subscriberElement.additionalServingNode.sgsnRealm != null)
                        additionalServingNode.addAvp(Avp.SGSN_REALM, subscriberElement.additionalServingNode.sgsnRealm, 10415, false, false,false);
                    if (subscriberElement.additionalServingNode.mmeName != null)
                        additionalServingNode.addAvp(Avp.MME_NAME, subscriberElement.additionalServingNode.mmeName, 10415, true, false,false);
                    if (subscriberElement.additionalServingNode.mmeRealm != null)
                        additionalServingNode.addAvp(Avp.MME_REALM, subscriberElement.additionalServingNode.mmeRealm, 10415, false, false,false);
                    if (subscriberElement.additionalServingNode.mscNumber != null)
                        additionalServingNode.addAvp(Avp.MSC_NUMBER, parseTBCD(subscriberElement.additionalServingNode.mscNumber), 10415, true, false);
                    if (subscriberElement.additionalServingNode.tgppAAAServerName != null)
                        additionalServingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, subscriberElement.additionalServingNode.tgppAAAServerName, 10415, true, false, false);
                    if (subscriberElement.additionalServingNode.lcsCapabilitySets != null)
                        additionalServingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, subscriberElement.additionalServingNode.lcsCapabilitySets, 10415, true, false, true);
                    if (subscriberElement.additionalServingNode.gmlcAddress != null) {
                        // IPv4 or IPv6 address of H-GMLC or the V-GMLC associated with the additional serving node
                        InetAddress additionalServingNodeGmlcAddress = null;
                        try {
                            additionalServingNodeGmlcAddress = InetAddress.getByName(subscriberElement.additionalServingNode.gmlcAddress);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        additionalServingNode.addAvp(Avp.GMLC_ADDRESS, additionalServingNodeGmlcAddress, 10415, true, false);
                    }
                }

                if (subscriberElement.gmlcAddress != null) {
                    InetAddress gmlcAddress = null;
                    try {
                        gmlcAddress = InetAddress.getByName(subscriberElement.gmlcAddress);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    riaAvpSet.addAvp(Avp.GMLC_ADDRESS, gmlcAddress, 10415, true, false);
                }

                if (subscriberElement.pprAddress != null) {
                    InetAddress pprAddress = null;
                    try {
                        pprAddress = InetAddress.getByName(subscriberElement.pprAddress);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    riaAvpSet.addAvp(Avp.PPR_ADDRESS, pprAddress, 10415, true, false);
                }

                riaAvpSet.addAvp(Avp.RIA_FLAGS, subscriberElement.riaFlags, 10415, true, false, true);
                riaAvpSet.addAvp(Avp.AUTH_SESSION_STATE, 0, 0, true, false, true);
            }
        }

        if (resultCode == DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK || resultCode == DIAMETER_ERROR_USER_UNKNOWN ||
            resultCode == DIAMETER_ERROR_ABSENT_USER) {
            // remove Result_Code AVP and add Experimental-Result-Code AVP
            riaAvpSet.removeAvp(Avp.RESULT_CODE);
            riaAvpSet.addAvp(Avp.AUTH_SESSION_STATE, 0, 0, true, false, true);
            AvpSet experimentalResult = riaAvpSet.addGroupedAvp(Avp.EXPERIMENTAL_RESULT, true, false);
            experimentalResult.addAvp(Avp.EXPERIMENTAL_RESULT_CODE, resultCode, true, true);
            experimentalResult.addAvp(Avp.VENDOR_ID, 10415, true, false);
            logger.info(">> Sending [RIA] Routing-Info-Answer to {}@{} with experimental result code:{} ({})\n",
                rir.getOriginHost(), rir.getOriginRealm(), resultCode, getSLhExperimentalResultString(resultCode));

        } else if (resultCode == ResultCode.SUCCESS) {
            logger.info(">> Sending [RIA] Routing-Info-Answer to {}@{} with result code:{} (SUCCESS)\n", rir.getOriginHost(), rir.getOriginRealm(), resultCode);
        } else {
            logger.info(">> Sending Error-Answer to {}@{} with result code:{}\n", rir.getOriginHost(), rir.getOriginRealm(), resultCode);
        }
        session.sendLCSRoutingInfoAnswer(ria);
    }

    private String getSLhExperimentalResultString(int code) {
        String experimentalResultString = "";
        switch (code) {
            case DIAMETER_ERROR_USER_UNKNOWN:
                experimentalResultString = "DIAMETER_ERROR_USER_UNKNOWN";
                break;
            case DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK:
                experimentalResultString = "DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK";
                break;
            case DIAMETER_ERROR_ABSENT_USER:
                experimentalResultString = "DIAMETER_ERROR_ABSENT_USER";
                break;
        }
        return experimentalResultString;
    }
}
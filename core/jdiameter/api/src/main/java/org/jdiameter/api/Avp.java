package org.jdiameter.api;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

/**
 * The Avp class implements a Diameter AVP.
 * This class allows applications to build and read arbitrary Diameter AVP objects.
 * Wrapper interface allows to adapt the message to any driver vendor specific interface.
 * Serializable interface allows use this class in SLEE Event objects.
 *
 * @version 1.5.1 Final
 * @author erick.svenson@yahoo.com
 * @author artem.litvinov@gmail.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface Avp extends Wrapper, Serializable {

  /**
   * The User-Name AVP code
   */
  int USER_NAME = 1;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-IMSI AVP code
   */
  int TGPP_IMSI = 1;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-Charging-Id AVP code
   */
  int TGPP_CHARGING_ID = 2;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-PDP-Type AVP code
   */
  int TGPP_PDP_TYPE = 3;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-IMSI-MCC-MNC AVP code
   */
  int TGPP_IMSI_MCC_MNC = 8;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-GGSN-MCC-MNC AVP code
   */
  int TGPP_GGSN_MCC_MNC = 9;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-NSAPI AVP code
   */
  int TGPP_NSAPI = 10;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-Session-Stop-Indicator AVP code
   */
  int TGPP_SESSION_STOP_INDICATOR = 11;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-Selection-Mode AVP code
   */
  int TGPP_SELECTION_MODE = 12;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-Charging-Characteristics AVP code
   */
  int TGPP_CHARGING_CHARACTERISTICS = 13;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-SGSN-MCC-MNC AVP code
   */
  int GPP_SGSN_MCC_MNC = 18;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-RAT-Type AVP code
   */
  int TGPP_RAT_TYPE = 21;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-User-Location-Info AVP code
   */
  int GPP_USER_LOCATION_INFO = 22;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP-MS-TimeZone AVP code
   */
  int TGPP_MS_TIMEZONE = 23;

  /**
   * The Class AVP code
   */
  int CLASS = 25;

  /**
   * The Session-Timeout AVP code
   */
  int SESSION_TIMEOUT = 27;

  /**
   * The Proxy-State AVP code
   */
  int PROXY_STATE = 33;

  /**
   * The Accounting-Session-Id AVP code
   */
  int ACC_SESSION_ID = 44;

  /**
   * The Accounting-Multi-Session-Id AVP code
   */
  int ACC_MULTI_SESSION_ID = 50;

  /**
   * The Event-Timestamp AVP code
   */
  int EVENT_TIMESTAMP = 55;

  /**
   * The Acct-Interim-Interval AVP code
   */
  int ACCT_INTERIM_INTERVAL = 85;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) MIP6-Feature-Vector AVP code
   */
  int MIP6_FEATURE_VECTOR = 124;

  /**
   * IETF RFC 5447 MIP6-Home-Link-Prefix AVP code
   */
  int MIP6_HOME_LINK_PREFIX = 126;


  /**
   * IETF RFC 5580 Location-Data AVP code
   */
  int LOCATION_DATA = 128;

  /**
   * The Host-IP-Address AVP code
   */
  int HOST_IP_ADDRESS = 257;

  /**
   * The Authentication-Application-Id AVP code
   */
  int AUTH_APPLICATION_ID = 258;
  /**
   * The Accounting-Application-Id AVP code
   */
  int ACCT_APPLICATION_ID = 259;

  /**
   * The Vendor-Specific-Application-Id AVP code
   */
  int VENDOR_SPECIFIC_APPLICATION_ID = 260;

  /**
   * The Redirect-Host-Usage AVP code
   */
  int REDIRECT_HOST_USAGE = 261;

  /**
   * The Redirect-Max-Cache-Time AVP code
   */
  int REDIRECT_MAX_CACHE_TIME = 262;

  /**
   * The Session-Id AVP code
   */
  int SESSION_ID = 263;

  /**
   * The Origin-Host AVP code
   */
  int ORIGIN_HOST = 264;

  /**
   * The Supported-Vendor-Id AVP code
   */
  int SUPPORTED_VENDOR_ID = 265;

  /**
   * The Vendor-Id AVP code
   */
  int VENDOR_ID = 266;

  /**
   * The FirmWare-Revision AVP code
   */
  int FIRMWARE_REVISION = 267;

  /**
   * The Result-Code AVP code
   */
  int RESULT_CODE = 268;

  /**
   * The Product-Name AVP code
   */
  int PRODUCT_NAME = 269;

  /**
   * The Session-Binding AVP code
   */
  int SESSION_BINDING = 270;

  /**
   * The Session-Server-Failover AVP code
   */
  int SESSION_SERVER_FAILOVER = 271;

  /**
   * The Multi-Round-Timeout AVP code
   */
  int MULTI_ROUND_TIMEOUT = 272;

  /**
   * The Disconnect cause AVP code
   */
  int DISCONNECT_CAUSE = 273;

  /**
   * The Auth-Request-Type AVP code
   */
  int AUTH_REQUEST_TYPE = 274;

  /**
   * The Auth-Grace-Period AVP code
   */
  int AUTH_GRACE_PERIOD = 276;

  /**
   * The Auth-Session-State AVP code
   */
  int AUTH_SESSION_STATE = 277;

  /**
   * The Origin-State-Id AVP code
   */
  int ORIGIN_STATE_ID = 278;

  /**
   * The File-Avp AVP code
   */
  int FAILED_AVP = 279;

  /**
   * The Proxy-Host AVP code
   */
  int PROXY_HOST = 280;

  /**
   * The Error-Message AVP code
   */
  int ERROR_MESSAGE = 281;

  /**
   * The Route-Record AVP code
   */
  int ROUTE_RECORD = 282;

  /**
   * The Destination-Realm AVP code
   */
  int DESTINATION_REALM = 283;

  /**
   * The Proxy-Info AVP code
   */
  int PROXY_INFO = 284;

  /**
   * The Re-Authentication-Request-type AVP code
   */
  int RE_AUTH_REQUEST_TYPE = 285;

  /**
   * The Accounting-Sub-Session-Id AVP code
   */
  int ACC_SUB_SESSION_ID = 287;

  /**
   * The Authorization-Lifetime AVP code
   */
  int AUTHORIZATION_LIFETIME = 291;

  /**
   * The Redirect-Host AVP code
   */
  int REDIRECT_HOST = 292;

  /**
   * The Destination-Host AVP code
   */
  int DESTINATION_HOST = 293;

  /**
   * The Error-reporting-host AVP code
   */
  int ERROR_REPORTING_HOST = 294;

  /**
   * The Termination-Cause AVP code
   */
  int TERMINATION_CAUSE = 295;

  /**
   * The Origin-Realm AVP code
   */
  int ORIGIN_REALM = 296;

  /**
   * Experimental-Result AVP code
   */
  int EXPERIMENTAL_RESULT = 297;

  /**
   * The Experimental-Result-Code AVP code
   */
  int EXPERIMENTAL_RESULT_CODE = 298;

  /**
   * The Inband-Security-Id AVP code
   */
  int INBAND_SECURITY_ID = 299;

  /**
   * The E2E-Sequence-Avp AVP code
   */
  int E2E_SEQUENCE_AVP = 300;

  /**
   * IETF RFC 7944 DRMP AVP Code
   */
  int DRMP = 301;

  /**
   * 3GPP TS 29.173 (SLh interface) 3GPP-AAA-Server-Name AVP code (reused from 3GPP TS 29.273)
   */
  int TGPP_AAA_SERVER_NAME = 318;

  /**
   * IETF RFC 4004 MIP-Home-Agent-Address AVP code
   */
  int MIP_HOME_AGENT_ADDRESS = 334;

  /**
   * IETF RFC 4004 MIP-Home-Agent-Host AVP code
   */
  int MIP_HOME_AGENT_HOST = 348;

  // RFC 4006 (Credit-Control-Application) AVPs

  /**
   * CCA (RFC4006) Correlation ID AVP code
   */
  int CC_CORRELATION_ID = 411;

  /**
   * CCA (RFC4006) Credit-Control-Input Octets AVP code
   */
  int CC_INPUT_OCTETS = 412;

  /**
   * CCA (RFC4006) Credit-Control-Money AVP code
   */
  int CC_MONEY = 413;

  /**
   * CCA (RFC4006) Credit-Control-Output Octets AVP code
   */
  int CC_OUTPUT_OCTETS = 414;

  /**
   * CCA (RFC4006) Credit-Control-Request-Number AVP code
   */
  int CC_REQUEST_NUMBER = 415;

  /**
   * CCA (RFC4006) Request-Type AVP code
   */
  int CC_REQUEST_TYPE = 416;

  /**
   * CCA (RFC4006) Credit-Control-Service-Specific Units AVP code
   */
  int CC_SERVICE_SPECIFIC_UNITS = 417;

  /**
   * CCA (RFC4006) Credit-Control-Session-Failover AVP code
   */
  int CC_SESSION_FAILOVER = 418;

  /**
   * CCA (RFC4006) Credit-Control-Sub-Session ID AVP code
   */
  int CC_SUB_SESSION_ID = 419;

  /**
   * CCA (RFC4006) Credit-Control-Time AVP code
   */
  int CC_TIME = 420;

  /**
   * CCA (RFC4006) Credit-Control-Total-Octets AVP code
   */
  int CC_TOTAL_OCTETS = 421;

  /**
   * CCA (RFC4006) Check-Balance-Result AVP code
   */
  int CHECK_BALANCE_RESULT = 422;

  /**
   * CCA (RFC4006) Cost-Information AVP code
   */
  int COST_INFORMATION = 423;

  /**
   * CCA (RFC4006) Cost-Unit AVP code
   */
  int COST_UNIT = 424;

  /**
   * CCA (RFC4006) Currency-Code AVP code
   */
  int CURRENCY_CODE = 425;

  /**
   * CCA (RFC4006) Credit-Control AVP code
   */
  int CREDIT_CONTROL = 426;

  /**
   * CCA (RFC4006) Credit-Control-Failure-Handling AVP code
   */
  int CREDIT_CONTROL_FAILURE_HANDLING = 427;

  /**
   * CCA (RFC4006) Direct-Debiting-Failure-Handling AVP code
   */
  int DIRECT_DEBITING_FAILURE_HANDLING = 428;
  /**
   * CCA (RFC4006) Exponent AVP code
   */
  int EXPONENT = 429;

  /**
   * CCA (RFC4006) Final-Unit-Indication AVP code
   */
  int FINAL_UNIT_INDICATION = 430;

  /**
   * CCA (RFC4006) Granted-Service-Unit AVP code
   */
  int GRANTED_SERVICE_UNIT = 431;

  /**
   * CCA (RFC4006) Rating-Group AVP code
   */
  int RATING_GROUP = 432;
  /**
   * CCA (RFC4006) Redirect-Address-Type AVP code
   */
  int REDIRECT_ADDRESS_TYPE = 433;
  /**
   * CCA (RFC4006) Redirect-Server AVP code
   */
  int REDIRECT_SERVER = 434;
  /**
   * CCA (RFC4006) Redirect-Address AVP code
   */
  int REDIRECT_ADDRESS = 435;

  /**
   * CCA (RFC4006) Requested-Action AVP code
   */
  int REQUESTED_ACTION = 436;
  /**
   * CCA (RFC4006) Requested-Service-Unit AVP code
   */
  int REQUESTED_SERVICE_UNIT = 437;
  /**
   * CCA (RFC4006) Restriction-Filter-Rule AVP code
   */
  int RESTRICTION_FILTER_RULE = 438;

  /**
   * CCA (RFC4006) Service-Id AVP code
   */
  int SERVICE_IDENTIFIER_CCA = 439;

  /**
   * CCA (RFC4006) Service-Parameter-Info AVP code
   */
  int SERVICE_PARAMETER_INFO = 440;

  /**
   * CCA (RFC4006) Service-Parameter-Type AVP code
   */
  int SERVICE_PARAMETER_TYPE = 441;

  /**
   * CCA (RFC4006) Service-Parameter-Value AVP code
   */
  int SERVICE_PARAMETER_VALUE = 442;

  /**
   * CCA (RFC4006) Subscription-Id AVP code
   */
  int SUBSCRIPTION_ID = 443;

  /**
   * CCA (RFC4006) Subscription-Id-Data AVP code
   */
  int SUBSCRIPTION_ID_DATA = 444;

  /**
   * CCA (RFC4006) Unit-Value AVP code
   */
  int UNIT_VALUE = 445;

  /**
   * CCA (RFC4006) Used-Service-Unit AVP code
   */
  int USED_SERVICE_UNIT = 446;

  /**
   * CCA (RFC4006) Value-Digits AVP code
   */
  int VALUE_DIGITS = 447;

  /**
   * CCA (RFC4006) Validity-Time AVP code
   */
  int VALIDITY_TIME = 448;

  /**
   * CCA (RFC4006) Final-Unit-Action AVP code
   */
  int FINAL_UNIT_ACTION = 449;

  /**
   * CCA (RFC4006) Subscription-Id-Type AVP code
   */
  int SUBSCRIPTION_ID_TYPE = 450;

  /**
   * CCA (RFC4006) Tariff-Time-Change AVP code
   */
  int TARIFF_TIME_CHANGE = 451;

  /**
   * CCA (RFC4006) Tariff-Change-Usage AVP code
   */
  int TARIFF_CHANGE_USAGE = 452;

  /**
   * CCA (RFC4006) GSU-Pool-Identifier AVP code
   */
  int GSU_POOL_ID = 453;

  /**
   * CCA (RFC4006) Credit-Control-Unit-Type AVP code
   */
  int CC_UNIT_TYPE = 454;

  /**
   * CCA (RFC4006) Multiple-Services-Indicator AVP code
   */
  int MULTIPLE_SERVICES_INDICATOR = 455;

  /**
   * CCA (RFC4006) Multiple-Services-Credit-Control AVP code
   */
  int MULTIPLE_SERVICES_CREDIT_CONTROL = 456;

  /**
   * CCA (RFC4006) GSU-Pool-Reference AVP code
   */
  int GSU_POOL_REFERENCE = 457;

  /**
   * CCA (RFC4006) User-Equipment-Info AVP code
   */
  int USER_EQUIPMENT_INFO = 458;

  /**
   * CCA (RFC4006) User-Equipment-Info-Type AVP code
   */
  int USER_EQUIPMENT_INFO_TYPE = 459;

  /**
   * CCA (RFC4006) User-Equipment-Info-Value AVP code
   */
  int USER_EQUIPMENT_INFO_VALUE = 460;

  /**
   * CCA (RFC4006) Service-Context-Id AVP code
   */
  int SERVICE_CONTEXT_ID = 461;

  /**
   * The Accounting-Record-Type AVP code
   */
  int ACC_RECORD_TYPE = 480;

  /**
   * The Accounting-Realtime-Required AVP code
   */
  int ACCOUNTING_REALTIME_REQUIRED = 483;

  /**
   * The Accounting-Record-Number AVP code
   */
  int ACC_RECORD_NUMBER = 485;

  /**
   * IETF RFC 5447 MIP6-Agent-Info AVP code
   */
  int MIP6_AGENT_INFO = 486;

  /**
   * SLg (3GPP TS 29.172) Service-Selection AVP Code (reused from 3GPP TS 29.272 & IETF RFC 5778)
   */
  int SERVICE_SELECTION = 493;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) QoS-Capability AVP CODE
   */
  int QOS_CAPABILITY = 578;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Access-Network-Charging-Identifier Value AVP code
   */
  int ACCESS_NETWORK_CHARGING_IDENTIFIER_VALUE = 503;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) AF-Charging-Identifier AVP code
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) Mobile-Node-Identifier AVP code
   */
  int AF_CHARGING_IDENTIFIER = 505;
  int MOBILE_NODE_IDENTIFIER = 506;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Flows AVP code
   */
  int FLOWS = 510;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Max-Requested-Bandwidth-DL AVP code
   */
  int MAX_REQUESTED_BANDWIDTH_DL = 515;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Max-Requested-Bandwidth-UL AVP code
   */
  int MAX_REQUESTED_BANDWIDTH_UL = 516;

  /**
   * 3GPP TS 29.214 (Rx interface) Extended-Max-Requested-BW-DL AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int EXTENDED_MAX_REQUESTED_BW_DL = 554;

  /**
   * 3GPP TS 29.214 (Rx interface) Extended-Max-Requested-BW-UL AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int EXTENDED_MAX_REQUESTED_BW_UL = 555;


  /**
   * IETF RFC 5777 Time-Of-Day-Condition AVP code
   */
  int TIME_OF_DAY_CONDITION = 560;

  /**
   * IETF RFC 5777 Time-Of-Day-Start AVP code
   */
  int TIME_OF_DAY_START = 561;

  /**
   * IETF RFC 5777 Time-Of-Day-End AVP code
   */
  int TIME_OF_DAY_END = 562;

  /**
   * IETF RFC 5777 Day-Of-Month-Mask AVP code
   */
  int DAY_OF_WEEK_MASK = 563;

  /**
   * IETF RFC 5777 Day-Of-Week-Mask AVP code
   */
  int DAY_OF_MONTH_MASK = 564;

  /**
   * IETF RFC 5777 Month-Of-Year-Mask AVP code
   */
  int MONTH_OF_YEAR_MASK = 565;

  /**
   * IETF RFC 5777 Absolute-Start-Time AVP code
   */
  int ABSOLUTE_START_TIME = 566;

  /**
   * IETF RFC 5777 Absolute-Start-Fractional-Seconds AVP code
   */
  int ABSOLUTE_START_FRACTIONAL_SECONDS = 567;

  /**
   * IETF RFC 5777 Absolute-End-Time AVP code
   */
  int ABSOLUTE_END_TIME = 568;

  /**
   * IETF RFC 5777 Absolute-End-Fractional-Seconds AVP code
   */
  int ABSOLUTE_END_FRACTIONAL_SECONDS = 569;

  /**
   * IETF RFC 5777 Timezone-Flag AVP code
   */
  int TIMEZONE_FLAG = 570;

  /**
   * IETF RFC 5777 Timezone-Offset AVP code
   */
  int TIMEZONE_OFFSET = 571;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Visited-Network-Identifier AVP code
   */
  int VISITED_NETWORK_ID = 600;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Public-Identity AVP code
   */
  int PUBLIC_IDENTITY = 601;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Server-Name AVP code
   */
  int SERVER_NAME = 602;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Server-Capabilities AVP code
   */
  int SERVER_CAPABILITIES = 603;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Mandatory-Capability AVP code
   */
  int MANDATORY_CAPABILITY = 604;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Optional-Capability AVP code
   */
  int OPTIONAL_CAPABILITY = 605;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) User-Data AVP code
   * 3GPP TS 32.299 (Ro/Rf interfaces) User-Data-RORF AVP code
   */
  int USER_DATA_CxDx = 606;
  int USER_DATA_RoRf = 606;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Number-Auth-Items AVP code
   */
  int SIP_NUMBER_AUTH_ITEMS = 607;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Authentication-Scheme AVP code
   */
  int SIP_AUTHENTICATION_SCHEME = 608;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Authenticate AVP code
   */
  int SIP_AUTHENTICATE = 609;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Authorization AVP code
   */
  int SIP_AUTHORIZATION = 610;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Authentication-Context AVP code
   */
  int SIP_AUTHENTICATION_CONTEXT = 611;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Auth-Data-Item AVP code
   */
  int SIP_AUTH_DATA_ITEM = 612;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Item-Number AVP code
   */
  int SIP_ITEM_NUMBER = 613;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Server-Assignment Type AVP code
   */
  int SERVER_ASSIGNMENT_TYPE = 614;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Deregistration-Reason AVP code
   */
  int DEREGISTRATION_REASON = 615;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Reason-Code AVP code
   */
  int REASON_CODE = 616;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Reason-Info AVP code
   */
  int REASON_INFO = 617;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Charging-Information AVP code
   */
  int CHARGING_INFORMATION = 618;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Primary-Event-Charging-Function-Name AVP code
   */
  int PRI_EVENT_CHARGING_FUNCTION = 619;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Secondary-Event-Charging-Function-Name AVP code
   */
  int SEC_EVENT_CHARGING_FUNCTION = 620;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Primary-Charging-Collection-Function-Name AVP code
   * IETF RFC 7863 OC-Supported-Features AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int PRI_CHARGING_COLLECTION_FUNCTION = 621;
  int OC_SUPPORTED_FEATURES = 621;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Secondary-Charging-Collection-Function-Name AVP code
   * IETF RFC 7863 OC-Feature-Vector AVP code
   */
  int SEC_CHARGING_COLLECTION_FUNCTION = 622;
  int OC_FEATURE_VECTOR = 622;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) User-Authorization-Type AVP code
   * IETF RFC 7683 OC-OLR AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int USER_AUTHORIZATION_TYPE = 623;
  int OC_OLR = 623;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) User-Data-Already-Available AVP code
   * IETF RFC 7683 OC-Sequence-Number AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int USER_DATA_ALREADY_AVAILABLE = 624;
  int OC_SEQUENCE_NUMBER = 624;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Confidentiality Key AVP code
   * IETF RFC 7683 OC-Validity-Duration AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int CONFIDENTIALITY_KEY = 625;
  int OC_VALIDITY_DURATION = 625;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Integrity-Key AVP code
   * IETF RFC 7683 OC-Report-Type AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int INTEGRITY_KEY = 626;
  int OC_REPORT_TYPE = 626;

  /**
   * IETF RFC 7683 OC-Reduction-Percentage AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int OC_REDUCTION_PERCENTAGE = 627;

  /**
   * Supported Features AVP code
   */
  int SUPPORTED_FEATURES = 628;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Feature-List-ID AVP code
   */
  int FEATURE_LIST_ID = 629;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Feature-List AVP code
   */
  int FEATURE_LIST = 630;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Supported-Applications AVP code
   */
  int SUPPORTED_APPLICATIONS = 631;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Associated-Identities AVP code
   */
  int ASSOCIATED_IDENTITIES = 632;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Originating-Request AVP code
   */
  int ORIGINATING_REQUEST = 633;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Wildcarded-PSI AVP code
   * 3GPP TS 29.329 (Sh interface) Wildcarded-Public-Identity AVP code
   */
  int WILDCARDED_PSI = 634;
  int WILDCARDED_PUBLIC_IDENTITY = 634;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SIP-Digest-Authenticate AVP code
   */
  int SIP_DIGEST_AUTHENTICATE = 635;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Wildcarded-IMPU AVP code
   */
  int WILDCARDED_IMPU = 636;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) UAR-Flags AVP code
   */
  int UAR_FLAGS = 637;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Loose-Route-Indication AVP code
   */
  int LOOSE_ROUTE_INDICATION = 638;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SCSCF-Restoration-Info AVP code
   */
  int SCSCF_RESTORATION_INFO = 639;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Path AVP code
   */
  int PATH = 640;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Contact AVP code
   */
  int CONTACT = 641;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Subscription-Info AVP code
   */
  int SUBSCRIPTION_INFO = 642;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Call-ID-SIP-Header AVP code
   */
  int CALL_ID_SIP_HEADER = 643;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) From-SIP-Header AVP code
   */
  int FROM_SIP_HEADER = 644;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) To SIP-Header AVP code
   */
  int TO_SIP_HEADER = 645;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Record-Route AVP code
   */
  int RECORD_ROUTE = 646;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Associated-Registered-Identities AVP code
   */
  int ASSOCIATED_REGISTERED_IDENTITIES = 647;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Multiple-Registration-Indication AVP code
   * IETF RFC 7683 OC-Peer-Algo AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int MULTIPLE_REGISTRATION_INDICATION = 648;
  int OC_PEER_ALGO = 648;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Restoration-Info AVP code
   * IETF RFC 7683 SourceID AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int RESTORATION_INFO = 649;
  int SOURCE_ID = 649;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Session-Priority AVP code
   * IETF RFC 8583 Load AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int SESSION_PRIORITY = 650;
  int LOAD = 650;


  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Identity-with-Emergency-Registration AVP code
   * IETF RFC 8583 Load-Type AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int IDENTITY_WITH_EMERGENCY_REGISTRATION = 651;
  int LOAD_TYPE = 651;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Priviledged-Sender-Indication AVP code
   * IETF RFC 8583 Load-Value AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int PRIVILEDGED_SENDER_INDICATION = 652;
  int LOAD_VALUE = 652;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) LIA-Flags AVP code
   */
  int LIA_FLAGS = 653;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Initial-CSeq-Sequence-Number AVP code
   */
  int INITIAL_CSEQ_SEQUENCE_NUMBER = 654;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) SAR-Flags AVP code
   */
  int SAR_FLAGS = 655;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Allowed-WAF-WWSF-Identities AVP code
   */
  int ALLOWED_WAF_WWSF_IDENTITIES = 656;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) WebRTC-Authentication-Function-Name AVP code
   */
  int WEBRTC_AUTHENTICATION_FUNCTION_NAME = 657;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) WebRTC-Web-Server-Function-Name AVP code
   */
  int WEBRTC_WEB_SERVER_FUNCTION_NAME = 658;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) RTR-Flags AVP code
   */
  int RTR_FLAGS = 659;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) P-CSCF-Subscription-Info AVP code
   */
  int P_CSCF_SUBSCRIPTION_INFO = 660;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Registration-Time-Out AVP code
   */
  int REGISTRATION_TIME_OUT = 661;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Alternate-Digest-Algorithm AVP code
   */
  int ALTERNATE_DIGEST_ALGORITHM = 662;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Alternate-Digest-HA1 AVP code
   */
  int ALTERNATE_DIGEST_HA1 = 663;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) Failed-PCSCF AVP code
   */
  int FAILED_PCSCF = 664;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) PCSCF-FQDN AVP code
   */
  int PCSCF_FQDN = 665;

  /**
   * 3GPP TS 29.229 (Cx/Dx interfaces) PCSCF-IP-Address AVP code
   */
  int PCSCF_IP_ADDRESS = 666;


  /* Sh Interface AVPs */

  /**
   * 3GPP TS 29.329 (Sh interfaces) User-Identity AVP code
   */
  int USER_IDENTITY = 700;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MSISDN AVP code
   */
  int MSISDN = 701;

  /**
   * 3GPP TS 29.329 (Sh interfaces) User-Data AVP code
   */
  int USER_DATA_SH = 702;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Data-Reference AVP code
   */
  int DATA_REFERENCE = 703;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Service-Indication AVP code
   */
  int SERVICE_INDICATION = 704;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Subs-Req-Type AVP code
   */
  int SUBS_REQ_TYPE = 705;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Requested-Domain AVP code
   */
  int REQUESTED_DOMAIN = 706;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Current-Location AVP code
   */
  int CURRENT_LOCATION = 707;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Identity-Set AVP code
   */
  int IDENTITY_SET = 708;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Expiry-Time AVP code
   */
  int EXPIRY_TIME = 709;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Send-Data-Indication AVP code
   */
  int SEND_DATA_INDICATION = 710;

  /**
   * 3GPP TS 29.329 (Sh interfaces) DSAI-Tag AVP code
   */
  int DSAI_TAG = 711;

  /**
   * 3GPP TS 29.329 (Sh interfaces) One-Time-Notification AVP code
   */
  int ONE_TIME_NOTIFICATION = 712;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Requested-Nodes AVP code
   */
  int REQUESTED_NODES = 713;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Serving-Node-Indication AVP code
   */
  int SERVING_NODE_INDICATION = 714;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Repository-Data-ID AVP code
   */
  int REPOSITORY_DATA_ID = 715;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Sequence-Number AVP code
   */
  int SEQUENCE_NUMBER = 716;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Pre-Paging-Supported AVP code
   */
  int PRE_PAGING_SUPPORTED = 717;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Local-Time-Zone-Indication AVP code
   */
  int LOCAL_TIME_ZONE_INDICATION = 718;

  /**
   * 3GPP TS 29.329 (Sh interfaces) UDR-Flags AVP code
   */
  int UDR_FLAGS = 719;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Call-Reference-Info AVP code
   */
  int CALL_REFERENCE_INFO = 720;

  /**
   * 3GPP TS 29.329 (Sh interfaces) Call-Reference-Number AVP code
   */
  int CALL_REFERENCE_NUMBER = 721;

  /**
   * 3GPP TS 29.329 (Sh interfaces) AS-Number AVP code
   */
  int AS_NUMBER = 722;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Event-Type AVP code
   */
  int EVENT_TYPE = 823;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SIP-Method AVP code
   */
  int SIP_METHOD = 824;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Event AVP code
   */
  int EVENT = 825;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Content-Type AVP code
   */
  int CONTENT_TYPE = 826;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Content-Length AVP code
   */
  int CONTENT_LENGTH = 827;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Content-Disposition AVP code
   */
  int CONTENT_DISPOSITION = 828;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Role-Of-Node AVP code
   */
  int ROLE_OF_NODE = 829;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) User-Session-Id AVP code
   */
  int USER_SESSION_ID = 830;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Calling-Party-Address AVP code
   */
  int CALLING_PARTY_ADDRESS = 831;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Called-Party-Address AVP code
   */
  int CALLED_PARTY_ADDRESS = 832;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Time-Stamps AVP code
   */
  int TIME_STAMPS = 833;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SIP-Request-Timestamp AVP code
   */
  int SIP_REQUEST_TIMESTAMP = 834;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SIP-Response-Timestamp AVP code
   */
  int SIP_RESPONSE_TIMESTAMP = 835;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Application-Server AVP code
   */
  int APPLICATION_SERVER = 836;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Application-Provided-Called-Party-Address AVP code
   */
  int APPLICATION_PROVIDED_CALLED_PARTY_ADDRESS = 837;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Inter-Operator-Identifier AVP code
   */
  int INTER_OPERATOR_IDENTIFIER = 838;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Originating-IOI AVP code
   */
  int ORIGINATING_IOI = 839;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Terminating-IOI AVP code
   */
  int TERMINATING_IOI = 840;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) IMS-Charging-Identifier-AVP code
   */
  int IMS_CHARGING_IDENTIFIER = 841;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-Session-Description AVP code
   */
  int SDP_SESSION_DESCRIPTION = 842;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-Media-Component AVP code
   */
  int SDP_MEDIA_COMPONENT = 843;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-Media-Name AVP code
   */
  int SDP_MEDIA_NAME = 844;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-Media-Description AVP code
   */
  int SDP_MEDIA_DESCRIPTION = 845;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) CG-Address AVP code
   */
  int CG_ADDRESS = 846;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) GGSN-Address AVP code
   */
  int GGSN_ADDRESS = 847;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Served-Party-IP Address AVP code
   */
  int SERVED_PARTY_IP_ADDRESS = 848;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Authorized-QoS AVP code
   */
  int AUTHORIZED_QOS = 849;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Application-Server-Information AVP code
   */
  int APPLICATION_SERVER_INFORMATION = 850;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Trunk-Group-Id AVP code
   */
  int TRUNK_GROUP_ID = 851;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Incoming-Trunk-Group-Id AVP code
   */
  int INCOMING_TRUNK_GROUP_ID = 852;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Outgoing-Trunk-Group-Id AVP code
   */
  int OUTGOING_TRUNK_GROUP_ID = 853;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Bearer-Service AVP code
   */
  int BEARER_SERVICE = 854;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Id AVP code
   */
  int SERVICE_IDENTIFIER = 855;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Associated-URI AVP code
   */
  int ASSOCIATED_URI = 856;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Charged-Party AVP code
   */
  int CHARGED_PARTY = 857;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Controlling-Address AVP code
   */
  int POC_CONTROLLING_ADDRESS = 858;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Group-Name AVP code
   */
  int POC_GROUP_NAME = 859;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Cause-Code AVP code
   */
  int CAUSE_CODE = 861;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Node-Functionality AVP code
   */
  int NODE_FUNCTIONALITY = 862;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Specific-Data AVP code
   */
  int SERVICE_SPECIFIC_DATA = 863;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Originator AVP code
   */
  int ORIGINATOR = 864;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PS-Furnish-Charging-Information AVP code
   */
  int PS_FURNISH_CHARGING_INFORMATION = 865;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PS-Free-Format-Data AVP code
   */
  int PS_FREE_FORMAT_DATA = 866;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PS-Append-Free-Format-Data AVP code
   */
  int PS_APPEND_FREE_FORMAT_DATA = 867;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Time-Quota-Threshold AVP code
   */
  int TIME_QUOTA_THRESHOLD = 868;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Volume-Quota-Threshold AVP code
   */
  int VOLUME_QUOTA_THRESHOLD = 869;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Trigger-Type AVP code
   */
  int TRIGGER_TYPE = 870;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Quota-Holding-Time AVP code
   */
  int QUOTA_HOLDING_TIME = 871;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Reporting-Reason AVP code
   */
  int REPORTING_REASON = 872;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Information AVP code
   */
  int SERVICE_INFORMATION = 873;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PS-Information AVP code
   */
  int PS_INFORMATION = 874;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) WLAN-Information-AVP code
   */
  int WLAN_INFORMATION = 875;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) IMS-Information AVP code
   */
  int IMS_INFORMATION = 876;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MMS-Information AVP code
   */
  int MMS_INFORMATION = 877;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Information AVP code
   */
  int LCS_INFORMATION = 878;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Information AVP code
   */
  int POC_INFORMATION = 879;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MBMS-Information AVP code
   */
  int MBMS_INFORMATION = 880;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Quota-Consumption-Time AVP code
   */
  int QUOTA_CONSUMPTION_TIME = 881;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Media-Initiator-Flag AVP code
   */
  int MEDIA_INITIATOR_FLAG = 882;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Server-Role AVP code
   */
  int POC_SERVER_ROLE = 883;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Session-Type AVP code
   */
  int POC_SESSION_TYPE = 884;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Of-Participants AVP code
   */
  int NUMBER_OF_PARTICIPANTS = 885;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Originator-Address AVP code
   */
  int ORIGINATOR_ADDRESS = 886;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Participants-Involved AVP code
   */
  int PARTICIPANTS_INVOLVED = 887;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Expires-AVP code
   */
  int EXPIRES = 888;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Message-Body AVP code
   */
  int MESSAGE_BODY = 889;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) WAG-Address AVP code
   */
  int WAG_ADDRESS = 890;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) WAG-PLMN-Id AVP code
   */
  int WAG_PLMN_ID = 891;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) WLAN-Radio-Container AVP code
   */
  int WLAN_RADIO_CONTAINER = 892;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) WLAN-Technology AVP code
   */
  int WLAN_TECHNOLOGY = 893;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) WLAN-UE-Local-IPAddress AVP code
   */
  int WLAN_UE_LOCAL_IPADDRESS = 894;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PDG-Address AVP code
   */
  int PDG_ADDRESS = 895;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PDG-Charging-Id AVP code
   */
  int PDG_CHARGING_ID = 896;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Address-Data AVP code
   */
  int ADDRESS_DATA = 897;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Address-Domain-AVP code
   */
  int ADDRESS_DOMAIN = 898;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Address-Type AVP code
   */
  int ADDRESS_TYPE = 899;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) TMGI AVP code
   */
  int TMGI = 900;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Required-MBMS-Bearer-Capabilities AVP code
   */
  int REQUIRED_MBMS_BEARER_CAPABILITIES = 901;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MBMS-Service-Area AVP code
   */
  int MBMS_SERVICE_AREA = 903;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MBMS-Service-Type AVP code
   */
  int MBMS_SERVICE_TYPE = 906;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MBMS-2G-3G-Indicator AVP code
   */
  int MBMS_2G_3G_INDICATOR = 907;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MBMS-Session-Identity AVP code
   */
  int MBMS_SESSION_IDENTITY = 908;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) RAI AVP code
   */
  int RAI = 909;


  // Ro/Rf IMS Interfaces AVPs

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Charging Rule Base Name AVP code
   */
  int CHARGING_RULE_BASE_NAME = 1004;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) QoS Information AVP code
   */
  int QOS_INFORMATION = 1016;

  /**
   * Gx/Gxx (3GPP TS 29.212) Bearer Identifier AVP code
   */
  int BEARER_IDENTIFIER = 1020;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Guaranteed-Bitrate-UL AVP code
   */
  int GUARANTEED_BITRATE_DL = 1025;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Guaranteed-Bitrate-UL AVP code
   */
  int GUARANTEED_BITRATE_UL = 1026;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) QoS Class Identifier AVP code
   */
  int QOS_CLASS_IDENTIFIER = 1028;


  /**
   * 3GPP TS 23.273 (STa interface) RAT-Type AVP code
   */
  int RAT_TYPE = 1032;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Allocation Retention Priority AVP code
   */
  int ALLOCATION_RETENTION_PRIORITY = 1034;

  /**
   * Gx/Gxx (3GPP TS 29.212) APN aggregate max bitrate DL AVP code
   */
  int APN_AGGREGATE_MAX_BITRATE_DL = 1040;

  /**
   * Gx/Gxx (3GPP TS 29.212) APN aggregate max bitrate UL AVP code
   */
  int APN_AGGREGATE_MAX_BITRATE_UL = 1041;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Priority Level AVP code
   */
  int PRIORITY_LEVEL = 1046;

  /**
   * 3GPP TS 29.212 (Policy and Charging Control) Pre-emption-Capability AVP code used by S6a/S6d 3GPP TS 29.272)
   */
  int PREEMPTION_CAPABILITY = 1047;

  /**
   * 3GPP TS 29.212 (Policy and Charging Control) Pre-emption-Vulnerability AVP code used by S6a/S6d 3GPP TS 29.272)
   */
  int PREEMPTION_VULNERABILITY = 1047;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) VASP-Id AVP code
   */
  int VASP_ID = 1101;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) VAS-Id AVP code
   */
  int VAS_ID = 1102;


  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Domain-Name AVP code
   */
  int DOMAIN_NAME = 1200;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Recipient-Address AVP code
   */
  int RECIPIENT_ADDRESS = 1201;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Submission-Time AVP code
   */
  int SUBMISSION_TIME = 1202;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MM-Content-Type AVP code
   */
  int MM_CONTENT_TYPE = 1203;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Type-Number AVP code
   */
  int TYPE_NUMBER = 1204;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Additional-Type-Information AVP code
   */
  int ADDITIONAL_TYPE_INFORMATION = 1205;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Content-Size AVP code
   */
  int CONTENT_SIZE = 1206;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Additional-Content-Information AVP code
   */
  int ADDITIONAL_CONTENT_INFORMATION = 1207;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Addressee-Type AVP code
   */
  int ADDRESSEE_TYPE = 1208;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Priority AVP code
   */
  int PRIORITY = 1209;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Message-ID AVP code
   */
  int MESSAGE_ID = 1210;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Message-Type AVP code
   */
  int MESSAGE_TYPE = 1211;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Message-Size AVP code
   */
  int MESSAGE_SIZE = 1212;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Message-Class AVP code
   */
  int MESSAGE_CLASS = 1213;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Class-Identifier AVP code
   */
  int CLASS_IDENTIFIER = 1214;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Token-Text AVP code
   */
  int TOKEN_TEXT = 1215;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Delivery-Report-Requested AVP code
   */
  int DELIVERY_REPORT_REQUESTED = 1216;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Adaptations AVP code
   */
  int ADAPTATIONS = 1217;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Applic-ID AVP code
   */
  int APPLIC_ID = 1218;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Aux-Applic-Info AVP code
   */
  int AUX_APPLIC_INFO = 1219;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Content-Class AVP code
   */
  int CONTENT_CLASS = 1220;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) DRM-Content AVP code
   */
  int DRM_CONTENT = 1221;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Read-Reply-Report-Requested AVP code
   */
  int READ_REPLY_REPORT_REQUESTED = 1222;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Reply-Applic ID AVP code
   */
  int REPLY_APPLIC_ID = 1223;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) File-Repair-Supported AVP code
   */
  int FILE_REPAIR_SUPPORTED = 1224;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MBMS-User-Service-Type AVP code
   */
  int MBMS_USER_SERVICE_TYPE = 1225;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Unit-Quota-Threshold AVP code
   */
  int UNIT_QUOTA_THRESHOLD = 1226;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PDP-Address AVP code
   */
  int PDP_ADDRESS = 1227;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SGSN-Address AVP code
   */
  int SGSN_ADDRESS = 1228;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Session-Id AVP code
   */
  int POC_SESSION_ID = 1229;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Deferred-Location-Event-Type AVP code
   */
  int DEFERRED_LOCATION_EVENT_TYPE = 1230;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Client-Id AVP code
   */
  int LCS_CLIENT_ID = 1232;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Client-Dialed-By-MS AVP code
   */
  int LCS_CLIENT_DIALED_BY_MS = 1233;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Client-External-ID AVP code
   */
  int LCS_CLIENT_EXTERNAL_ID = 1234;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Data-Coding-Scheme AVP code
   */
  int LCS_DATA_CODING_SCHEME = 1236;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Format-Indicator AVP code
   */
  int LCS_FORMAT_INDICATOR = 1237;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Name-String AVP code
   */
  int LCS_NAME_STRING = 1238;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Requestor-Id AVP code
   */
  int LCS_REQUESTOR_ID = 1239;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Requestor-Id-String AVP code
   */
  int LCS_REQUESTOR_ID_STRING = 1240;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) LCS-Client-Type AVP code
   */
  int LCS_CLIENT_TYPE = 1241;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Location-Estimate AVP code
   */
  int LOCATION_ESTIMATE = 1242;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Location-Estimate-Type AVP code
   */
  int LOCATION_ESTIMATE_TYPE = 1243;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Location-Type AVP code
   */
  int LOCATION_TYPE = 1244;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Positioning-Data AVP code
   */
  int POSITIONING_DATA = 1245;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) WLAN-Session-Id AVP code
   */
  int WLAN_SESSION_ID = 1246;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PDP-Context-Type AVP code
   */
  int PDP_CONTEXT_TYPE = 1247;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MMBox-Storage-Requested AVP code
   */
  int MMBOX_STORAGE_REQUESTED = 1248;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Specific-Info AVP code
   */
  int SERVICE_SPECIFIC_INFO = 1249;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Called-Asserted-Identity AVP code
   */
  int CALLED_ASSERTED_IDENTITY = 1250;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Requested-Party-Address AVP code
   */
  int REQUESTED_PARTY_ADDRESS = 1251;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-User-Role AVP code
   */
  int POC_USER_ROLE = 1252;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-User-Role-IDs AVP code
   */
  int POC_USER_ROLE_IDS = 1253;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-User-Role-Info-Units AVP code
   */
  int POC_USER_ROLE_INFO_UNITS = 1254;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Talk-Burst-Exchange AVP code
   */
  int TALK_BURST_EXCHANGE = 1255;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Generic-Information AVP code
   */
  int SERVICE_GENERIC_INFORMATION = 1256;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Specific-Type AVP code
   */
  int SERVICE_SPECIFIC_TYPE = 1257;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Event-Charging-TimeStamp AVP code
   */
  int EVENT_CHARGING_TIMESTAMP = 1258;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Participant-Access-Priority AVP code
   */
  int PARTICIPANT_ACCESS_PRIORITY = 1259;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Participant-Group AVP code
   */
  int PARTICIPANT_GROUP = 1260;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Change-Condition AVP code
   */
  int POC_CHANGE_CONDITION = 1261;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Change-Time AVP code
   */
  int POC_CHANGE_TIME = 1262;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Access-Network-Information AVP code
   */
  int ACCESS_NETWORK_INFORMATION = 1263;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Trigger AVP code
   */
  int TRIGGER = 1264;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Base-Time-Interval AVP code
   */
  int BASE_TIME_INTERVAL = 1265;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Envelope AVP code
   */
  int ENVELOPE = 1266;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Envelope-End-Time AVP code
   */
  int ENVELOPE_END_TIME = 1267;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Envelope-Reporting AVP code
   */
  int ENVELOPE_REPORTING = 1268;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Envelope-Start-Time AVP code
   */
  int ENVELOPE_START_TIME = 1269;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Time-Quota-Mechanism AVP code
   */
  int TIME_QUOTA_MECHANISM = 1270;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Time-Quota-Type AVP code
   */
  int TIME_QUOTA_TYPE = 1271;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Early-Media-Description AVP code
   */
  int EARLY_MEDIA_DESCRIPTION = 1272;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-TimeStamps AVP code
   */
  int SDP_TIMESTAMPS = 1273;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-Offer-Timestamp AVP code
   */
  int SDP_OFFER_TIMESTAMP = 1274;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-Answer-Timestamp AVP code
   */
  int SDP_ANSWER_TIMESTAMP = 1275;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) AF-Correlation-Information AVP code
   */
  int AF_CORRELATION_INFORMATION = 1276;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Session-Initiation-Type AVP code
   */
  int POC_SESSION_INITIATION_TYPE = 1277;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Offline-Charging AVP code
   */
  int OFFLINE_CHARGING = 1278;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) User-Participating-Type AVP code
   */
  int USER_PARTICIPATING_TYPE = 1279;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Alternate-Charged-Party-Address AVP code
   */
  int ALTERNATE_CHARGED_PARTY_ADDRESS = 1280;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) IMS-Communication-Service-Identifier AVP code
   */
  int IMS_COMMUNICATION_SERVICE_IDENTIFIER = 1281;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Of-Received-Talk-Bursts AVP code
   */
  int NUMBER_OF_RECEIVED_TALK_BURSTS = 1282;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Of-Talk-Bursts AVP code
   */
  int NUMBER_OF_TALK_BURSTS = 1283;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Received-Talk-Burst-Time AVP code
   */
  int RECEIVED_TALK_BURST_TIME = 1284;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Received-Talk-Burst-Volume AVP code
   */
  int RECEIVED_TALK_BURST_VOLUME = 1285;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Talk-Burst-Time AVP code
   */
  int TALK_BURST_TIME = 1286;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Talk-Burst-Volume AVP code
   */
  int TALK_BURST_VOLUME = 1287;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Media-Initiator-Party AVP code
   */
  int MEDIA_INITIATOR_PARTY = 1288;


  /* S6a/S6d, S7a/S7d and S13/S13' AVPs*/

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') EPS-Location-Information AVP CODE
   */
  int SUBSCRIPTION_DATA = 1400;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Terminal-Information AVP CODE
   */
  int TERMINAL_INFORMATION = 1401;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') IMEI AVP CODE
   */
  int TGPP_IMEI = 1402;
  int IMEI = 1402;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Software-Version AVP CODE
   */
  int SOFTWARE_VERSION = 1403;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') QoS-Subscribed AVP CODE
   */
  int QOS_SUBSCRIBED = 1404;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') ULR-Flags AVP CODE
   */
  int ULR_FLAGS = 1405;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') ULA-Flags AVP CODE
   */
  int ULA_FLAGS = 1406;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Visited-PLMN-Id AVP CODE
   */
  int VISITED_PLMN_ID = 1407;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Requested-EUTRAN-Authentication-Info AVP CODE
   */
  int REQUESTED_EUTRAN_AUTHENTICATION_INFO = 1408;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Requested-UTRAN-GERAN-Authentication-Info AVP CODE
   */
  int REQUESTED_UTRAN_GERAN_AUTHENTICATION_INFO = 1409;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Number-Of-Requested-Vectors AVP CODE
   */
  int NUMBER_OF_REQUESTED_VECTORS = 1410;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Re-Synchronization-Info AVP CODE
   */
  int RE_SYNCHRONIZATION_INFO = 1411;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Immediate-Response-Preferred AVP CODE
   */
  int IMMEDIATE_RESPONSE_PREFERRED = 1412;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Authentication-Info AVP CODE
   */
  int AUTHENTICATION_INFO = 1413;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') E-UTRAN-Vector AVP CODE
   */
  int E_UTRAN_VECTOR = 1414;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') UTRAN-Vector AVP CODE
   */
  int UTRAN_VECTOR = 1415;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') GERAN-Vector AVP CODE
   */
  int GERAN_VECTOR = 1416;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Network-Access-Mode AVP CODE
   */
  int NETWORK_ACCESS_MODE = 1417;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') HPLMN-ODB AVP CODE
   */
  int HPLMN_ODB = 1418;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Item-Number AVP CODE
   */
  int ITEM_NUMBER = 1419;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Cancellation-Type AVP CODE
   */
  int CANCELLATION_TYPE = 1420;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') DSR-Flags AVP CODE
   */
  int DSR_FLAGS = 1421;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') DSA-Flags AVP CODE
   */
  int DSA_FLAGS = 1422;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Context-Identifier AVP CODE
   */
  int CONTEXT_IDENTIFIER = 1423;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Subscriber-Status AVP CODE
   */
  int SUBSCRIBER_STATUS = 1424;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Operator-Determined-Barring AVP CODE
   */
  int OPERATOR_DETERMINED_BARRING = 1425;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Access-Restriction-Data AVP CODE
   */
  int ACCESS_RESTRICTION_DATA = 1426;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') APN-OI-Replacement AVP CODE
   */
  int APN_OI_REPLACEMENT = 1427;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') All-APN-Configurations-Included-Indicator AVP CODE
   */
  int ALL_APN_CONFIGURATIONS_INCLUDED_INDICATOR = 1428;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') APN-Configuration-Profile AVP CODE
   */
  int APN_CONFIGURATION_PROFILE = 1429;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') APN-Configuration AVP CODE
   */
  int APN_CONFIGURATION = 1430;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') EPS-Subscribed-QoS-Profile AVP CODE
   */
  int EPS_SUBSCRIBED_QOS_PROFILE = 1431;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') VPLMN-Dynamic-Address-Allowed AVP CODE
   */
  int VPLMN_DYNAMIC_ADDRESS_ALLOWED = 1432;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') STN-SR AVP CODE
   */
  int STN_SR = 1433;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Alert-Reason AVP CODE
   */
  int ALERT_REASON = 1434;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') AMBR AVP CODE
   */
  int AMBR = 1435;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') CSG-Subscription-Data AVP CODE
   */
  int CSG_SUBSCRIPTION_DATA = 1436;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') CSG-Id AVP CODE
   */
  int CSG_ID = 1437;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PDN-GW_Allocation-Type AVP CODE
   */
  int PDN_GW_ALLOCATION_TYPE = 1438;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Expiration-Date AVP CODE
   */
  int EXPIRATION_DATE = 1439;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') RAT-Frequency-Selection-Priority-ID AVP CODE
   */
  int RAT_FREQUENCY_SELECTION_PRIORITY_ID = 1440;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') IDA-Flags AVP CODE
   */
  int IDA_FLAGS = 1441;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PUA-Flags AVP CODE
   */
  int PUA_FLAGS = 1442;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') NOR-Flags AVP CODE
   */
  int NOR_FLAGS = 1443;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') User-Id AVP CODE
   */
  int USER_ID = 1444;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Equipment-Status AVP CODE
   */
  int EQUIPMENT_STATUS = 1445;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Regional-Subscription-Zone-Code AVP CODE
   */
  int REGIONAL_SUBSCRIPTION_ZONE_CODE = 1446;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') RAND AVP CODE
   */
  int RAND = 1447;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') XRES AVP CODE
   */
  int XRES = 1448;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') AUTN AVP CODE
   */
  int AUTN = 1449;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') KASME AVP CODE
   */
  int KASME = 1450;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-Collection-Entity AVP CODE
   */
  int TRACE_COLLECTION_ENTITY = 1452;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Kc AVP CODE
   */
  int Kc = 1453;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SRES AVP CODE
   */
  int SRES = 1454;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PDN-Type AVP CODE
   */
  int PDN_TYPE = 1456;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Roaming-Restricted-Due-To-Unsupported-Feature AVP CODE
   */
  int ROAMING_RESTRICTED_DUE_TO_UNSUPPORTED_FEATURE = 1457;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-Data AVP CODE
   */
  int TRACE_DATA = 1458;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-Reference AVP CODE
   */
  int TRACE_REFERENCE = 1459;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-Depth AVP CODE
   */
  int TRACE_DEPTH = 1462;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-NE-Type-List AVP CODE
   */
  int TRACE_NE_TYPE_LIST = 1463;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-Interface-List AVP CODE
   */
  int TRACE_INTERFACE_LIST = 1464;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-Event-List AVP CODE
   */
  int TRACE_EVENT_LIST = 1465;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') OMC-Id AVP CODE
   */
  int OMC_ID = 1466;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') GPRS-Subscription-Data AVP CODE
   */
  int GPRS_SUBSCRIPTION_DATA = 1467;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Complete-Data-List-Included-Indicator AVP CODE
   */
  int COMPLETE_DATA_LIST_INCLUDED_INDICATOR = 1468;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PDP-Context AVP CODE
   */
  int PDP_CONTEXT = 1469;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PDP-Type AVP CODE
   */
  int PDP_TYPE = 1470;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') 3GPP2-MEID AVP CODE
   */
  int TGPP2_MEID = 1471;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Specific-APN-Info AVP CODE
   */
  int SPECIFIC_APN_INFO = 1472;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') LCS-Info AVP CODE
   */
  int LCS_INFO = 1473;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') GMLC-Number AVP CODE
   */
  int GMLC_NUMBER = 1474;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') LCS-PrivacyException AVP CODE
   */
  int LCS_PRIVACY_EXCEPTION = 1475;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SS-Code AVP CODE
   */
  int SS_CODE = 1476;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SS-Status AVP CODE
   */
  int SS_STATUS = 1477;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Notification-To-UE-User AVP CODE
   */
  int NOTIFICATION_TO_UE_USER = 1478;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') External-Client AVP CODE
   */
  int EXTERNAL_CLIENT = 1479;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Client-Identity AVP CODE
   */
  int CLIENT_IDENTITY = 1480;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') GMLC-Restriction AVP CODE
   */
  int GMLC_RESTRICTION = 1481;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PLMN-Client AVP CODE
   */
  int PLMN_CLIENT = 1482;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Service-Type AVP CODE
   */
  int TGPP_SERVICE_TYPE = 1483;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Service-Type-Identity AVP CODE
   */
  int SERVICE_TYPE_IDENTITY = 1484;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MO-LR AVP CODE
   */
  int MO_LR = 1485;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Teleservice-List AVP CODE
   */
  int TELESERVICE_LIST = 1486;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') TS-Code AVP CODE
   */
  int TS_CODE = 1487;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Call-Barring-Info AVP CODE
   */
  int CALL_BARRING_INFO = 1488;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SGSN-Number AVP CODE
   */
  int SGSN_NUMBER = 1489;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') IDR-Flags AVP CODE
   */
  int IDR_FLAGS = 1490;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') ICS-Indicator AVP CODE
   */
  int ICS_INDICATOR = 1491;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') IMS-Voice-Over-PS-Sessions-Supported AVP CODE
   */
  int IMS_VOICE_OVER_PS_SESSIONS_SUPPORTED = 1492;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Homogeneous-Support-of-IMS-Voice-Over-PS-Sessions AVP CODE
   */
  int HOMOGENEOUS_SUPPORT_OF_IMS_VOICE_OVER_PS_SESSIONS = 1493;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Last-UE-Activity-Time AVP CODE
   */
  int LAST_UE_ACTIVITY_TIME = 1494;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') EPS-User-State AVP CODE
   */
  int EPS_USER_STATE = 1495;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') EPS-Location-Information AVP CODE
   */
  int EPS_LOCATION_INFORMATION = 1496;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MME-User-State AVP CODE
   */
  int MME_USER_STATE = 1497;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SGSN-User-State AVP CODE
   */
  int SGSN_USER_STATE = 1498;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') User-State AVP CODE
   */
  int USER_STATE = 1499;

  /* 3GPP EPS AAA (SWa, SWa', Sta, SWd and SWd' interfaces) **/

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) ANID AVP CODE
   */
  int ANID = 1504;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) AN-Trusted AVP CODE
   */
  int AN_TRUSTED = 1503;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) MIP-FA-RK AVP CODE
   */
  int MIP_FA_RK = 1506;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) MIP-FA-RK-SPI AVP CODE
   */
  int MIP_FA_RK_SPI = 1507;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) WLAN-Identifier AVP CODE
   */
  int WLAN_IDENTIFIER = 1509;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) Full-Network-Name AVP CODE
   */
  int FULL_NETWORK_NAME = 1516;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) Short-Network-Name AVP CODE
   */
  int SHORT_NETWORK_NAME = 1517;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) AAA-Failure-Indication AVP CODE
   */
  int AAA_FAILURE_INDICATION = 1518;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) Transport-Access-Type AVP CODE
   */
  int TRANSPORT_ACCESS_TYPE = 1519;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) DER-Flags AVP CODE
   */
  int DER_FLAGS = 1520;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces)  AVP CODE
   */
  int DEA_FLAGS = 1521;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) SSID AVP CODE
   */
  int SSID = 1524;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) HESSID AVP CODE
   */
  int HESSID = 1525;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) Access-Network-Info AVP CODE
   */
  int ACCESS_NETWORK_INFO = 1526;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) TWAN-Connection-Mode AVP CODE
   */
  int TWAN_CONNECTION_MODE = 1527;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) TWAN-Connectivity-Parameters AVP CODE
   */
  int TWAN_CONNECTIVITY_PARAMETERS = 1528;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) Connectivity-Flags AVP CODE
   */
  int CONNECTIVITY_FLAGS = 1529;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) TWAN-PCO AVP CODE
   */
  int TWAN_PCO = 1530;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) TWAG-CP-Address AVP CODE
   */
  int TWAG_CP_ADDRESS = 1531;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) TWAG-UP-Address AVP CODE
   */
  int TWAG_UP_ADDRESS = 1532;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) TWAN-S2a-Failure-Cause AVP CODE
   */
  int TWAN_S2A_FAILURE_CAUSE = 1533;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) SM-Back-Off-Timer AVP CODE
   */
  int SM_BACK_OFF_TIMER = 1534;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) WLCP-Key AVP CODE
   */
  int WLCP_KEY = 1535;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) Emergency-Services AVP CODE
   */
  int EMERGENCY_SERVICES = 1538;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) IMEI-Check-In-VPLMN-Result AVP CODE
   */
  int IMEI_CHECK_IN_VPLMN_RESULT = 1540;

  /**
   * 3GPP TS 29.273 (SWa, SWa', Sta, SWd and SWd' interfaces) High-Priority-Access-Info AVP CODE
   */
  int HIGH_PRIORITY_ACCESS_INFO = 1542;

  /* S6a/S6d, S7a/S7d and S13/S13' AVPs (continuation) */

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MME-Location-Information AVP CODE
   */
  int MME_LOCATION_INFORMATION = 1600;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SGSN-Location-Information AVP CODE
   */
  int SGSN_LOCATION_INFORMATION = 1601;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') E-UTRAN-Cell-Global-Identity AVP CODE
   */
  int E_UTRAN_CELL_GLOBAL_IDENTITY = 1602;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Tracking-Area-Identity AVP CODE
   */
  int TRACKING_AREA_IDENTITY = 1603;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Cell-Global-Identity AVP CODE
   */
  int CELL_GLOBAL_IDENTITY = 1604;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Routing-Area-Identity AVP CODE
   */
  int ROUTING_AREA_IDENTITY = 1605;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Location-Area-Identity AVP CODE
   */
  int LOCATION_AREA_IDENTITY = 1606;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Service-Area-Identity AVP CODE
   */
  int SERVICE_AREA_IDENTITY = 1607;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Geographical-Information AVP CODE
   */
  int GEOGRAPHICAL_INFORMATION = 1608;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Geodetic-Information AVP CODE
   */
  int GEODETIC_INFORMATION = 1609;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Current-Location-Retrieved AVP CODE
   */
  int CURRENT_LOCATION_RETRIEVED = 1610;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Age-Of-Location-Information AVP CODE
   */
  int AGE_OF_LOCATION_INFORMATION = 1611;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Active-APN AVP CODE
   */
  int ACTIVE_APN = 1612;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SIPTO-Permission AVP CODE
   */
  int SIPTO_PERMISSION = 1613;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Error-Diagnostic AVP CODE
   */
  int ERROR_DIAGNOSTIC = 1614;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') UE-SRVCC-Capability AVP CODE
   */
  int UE_SRVCC_CAPABILITY = 1615;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MPS-Priority AVP CODE
   */
  int MPS_PRIORITY = 1616;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') VPLMN-LIPA-Allowed AVP CODE
   */
  int VPLMN_LIPA_ALLOWED = 1617;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') LIPA-Permission AVP CODE
   */
  int LIPA_PERMISSION = 1618;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Subscribed-Periodic-RAU-TAU-Timer AVP CODE
   */
  int SUBSCRIBED_PERIODIC_RAU_TAU_TIMER = 1619;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Ext-PDP-Type AVP CODE
   */
  int EXT_PDP_TYPE = 1620;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Ext-PDP-Address AVP CODE
   */
  int EXT_PDP_ADDRESS = 1621;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MDT-Configuration AVP CODE
   */
  int MDT_CONFIGURATION = 1622;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Job-Type AVP CODE
   */
  int JOB_TYPE = 1623;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Area-Scope AVP CODE
   */
  int AREA_SCOPE = 1624;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') List-Of-Measurements AVP CODE
   */
  int LIST_OF_MEASUREMENTS = 1625;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Reporting-Trigger AVP CODE
   */
  int REPORTING_TRIGGER = 1626;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Report-Interval AVP CODE
   */
  int REPORT_INTERVAL = 1627;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Report-Amount AVP CODE
   */
  int REPORT_AMOUNT = 1628;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Event-Threshold-RSRP AVP CODE
   */
  int EVENT_THRESHOLD_RSRP = 1629;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Event-Threshold-RSRQ AVP CODE
   */
  int EVENT_THRESHOLD_RSRQ = 1630;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Logging-Interval AVP CODE
   */
  int LOGGING_INTERVAL = 1631;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Logging-Duration AVP CODE
   */
  int LOGGING_DURATION = 1632;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Relay-Node-Indicator AVP CODE
   */
  int RELAY_NODE_INDICATOR = 1633;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MDT-User-Consent AVP CODE
   */
  int MDT_USER_CONSENT = 1634;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PUR-Flags AVP CODE
   */
  int PUR_FLAGS = 1635;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Subscribed-VSRVCC AVP CODE
   */
  int SUBSCRIBED_VSRVCC = 1636;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Equivalent-PLMN-List AVP CODE
   */
  int EQUIVALENT_PLMN_LIST = 1637;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') CLR-Flags AVP CODE
   */
  int CLR_FLAGS = 1638;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') UVR-Flags AVP CODE
   */
  int UVR_FLAGS = 1639;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') UVA-Flags AVP CODE
   */
  int UVA_FLAGS = 1640;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') VPLMN-CSG-Subscription-Data AVP CODE
   */
  int VPLMN_CSG_SUBSCRIPTION_DATA = 1641;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Time-Zone AVP CODE
   */
  int TIME_ZONE = 1642;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') A-MSISDN AVP CODE
   */
  int A_MSISDN = 1643;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MME-Number-for-MT-SMS AVP CODE
   */
  int MME_NUMBER_FOR_MT_SMS = 1645;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SMS-Register-Request AVP CODE
   */
  int SMS_REGISTER_REQUEST = 1648;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Local-Time-Zone AVP CODE
   */
  int LOCAL_TIME_ZONE = 1649;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Daylight-Saving-Time AVP CODE
   */
  int DAYLIGHT_SAVING_TIME = 1650;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Subscription-Data-Flags AVP CODE
   */
  int SUBSCRIPTION_DATA_FLAGS = 1654;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Measurement-Period-LTE AVP CODE
   */
  int MEASUREMENT_PERIOD_LTE = 1655;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Measurement-Period-UMTS AVP CODE
   */
  int MEASUREMENT_PERIOD_UMTS = 1656;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Collection-Period-RRM-LTE AVP CODE
   */
  int COLLECTION_PERIOD_RRM_LTE = 1657;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Collection-Period-RRM-UMTS AVP CODE
   */
  int COLLECTION_PERIOD_RRM_UMTS = 1658;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Positioning-Method AVP CODE
   */
  int POSITIONING_METHOD = 1659;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Measurement-Quantity AVP CODE
   */
  int MEASUREMENT_QUANTITY = 1660;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Event-Threshold-Event-1F AVP CODE
   */
  int EVENT_THRESHOLD_EVENT_1F = 1661;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Event-Threshold-Event-1I AVP CODE
   */
  int EVENT_THRESHOLD_EVENT_1I = 1662;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Restoration-Priority AVP CODE
   */
  int RESTORATION_PRIORITY = 1663;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SGs-MME-Identity AVP CODE
   */
  int SGS_MME_IDENTITY = 1664;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SIPTO-Local-Network-Permission AVP CODE
   */
  int SIPTO_LOCAL_NETWORK_PERMISSION = 1665;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Coupled-Node-Diameter-I AVP CODE
   */
  int COUPLED_NODE_DIAMETER_ID = 1666;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') WLAN-offloadability AVP CODE
   */
  int WLAN_OFFLOADABILITY = 1667;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') WLAN-offloadability-EUTRAN AVP CODE
   */
  int WLAN_OFFLOADABILITY_EUTRAN = 1668;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') WLAN-offloadability-UTRAN AVP CODE
   */
  int WLAN_OFFLOADABILITY_UTRAN = 1669;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Reset-ID AVP CODE
   */
  int RESET_ID = 1670;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MDT-Allowed-PLMN-Id AVP CODE
   */
  int MDT_ALLOWED_PLMN_ID = 1671;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Adjacent-PLMNs AVP CODE
   */
  int ADJACENT_PLMNS = 1672;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Adjacent-Access-Restriction-Data AVP CODE
   */
  int ADJACENT_ACCESS_RESTRICTION_DATA = 1673;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') DL-Buffering-Suggested-Packet-Count AVP CODE
   */
  int DL_BUFFERING_SUGGESTED_PACKET_COUNT = 1674;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') IMSI-Group-Id AVP CODE
   */
  int IMSI_GROUP_ID = 1675;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Group-Service-Id AVP CODE
   */
  int GROUP_SERVICE_ID = 1676;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Group-PLMN-Id AVP CODE
   */
  int GROUP_PLMN_ID = 1677;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Local-Group-Id AVP CODE
   */
  int LOCAL_GROUP_ID = 1678;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') AIR-Flags AVP CODE
   */
  int AIR_FLAGS = 1679;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') UE-Usage-Type AVP CODE
   */
  int UE_USAGE_TYPE = 1680;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Non-IP-PDN-Type-Indicato AVP CODE
   */
  int NON_IP_PDN_TYPE_INDICATOR = 1681;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Non-IP-Data-Delivery-Mechanism AVP CODE
   */
  int NON_IP_DATA_DELIVERY_MECHANISM = 1682;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Additional-Context-Identifier AVP CODE
   */
  int ADDITIONAL_CONTEXT_IDENTIFIER = 1683;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SCEF-Realm AVP CODE
   */
  int SCEF_REALM = 1684;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Subscription-Data-Deletion AVP CODE
   */
  int SUBSCRIPTION_DATA_DELETION = 1685;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Preferred-Data-Mode AVP CODE
   */
  int PREFERRED_DATA_MODE = 1686;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Emergency-Info AVP CODE
   */
  int EMERGENCY_INFO = 1687;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') V2X-Subscription-Data AVP CODE
   */
  int V2X_SUBSCRIPTION_DATA = 1688;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') V2X-Permission AVP CODE
   */
  int V2X_PERMISSION = 1689;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PDN-Connection-Continuity AVP CODE
   */
  int PDN_CONNECTION_CONTINUITY = 1690;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') eDRX-Cycle-Length AVP CODE
   */
  int EDRX_CYCLE_LENGTH = 1691;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') eDRX-Cycle-Length-Value AVP CODE
   */
  int EDRX_CYCLE_LENGTH_VALUE = 1692;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') UE-PC5-AMBR AVP CODE
   */
  int UE_PC5_AMBR = 1693;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MBSFN-Area AVP CODE
   */
  int MBSFN_AREA = 1694;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MBSFN-Area-ID AVP CODE
   */
  int MBSFN_AREA_ID = 1695;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Carrier-Frequency AVP CODE
   */
  int CARRIER_FREQUENCY = 1696;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') RDS-Indicator AVP CODE
   */
  int RDS_INDICATOR = 1697;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Service-Gap-Time AVP CODE
   */
  int SERVICE_GAP_TIME = 1698;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Aerial-UE-Subscription-Information AVP CODE
   */
  int AERIAL_UE_SUBSCRIPTION_INFORMATION = 1699;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Broadcast-Location-Assistance-Data-Types AVP CODE
   */
  int BROADCAST_LOCATION_ASSISTANCE_DATA_TYPES = 1700;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Paging-Time-Window AVP CODE
   */
  int PAGING_TIME_WINDOW = 1701;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Operation-Mode AVP CODE
   */
  int OPERATION_MODE = 1702;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Paging-Time-Window-Length AVP CODE
   */
  int PAGING_TIME_WINDOW_LENGTH = 1703;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Core-Network-Restrictions AVP CODE
   */
  int CORE_NETWORK_RESTRICTIONS = 1704;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') eDRX-Related-RAT AVP CODE
   */
  int EDRX_RELATED_RAT = 1705;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Interworking-5GS-Indicator AVP CODE
   */
  int INTERWORKING_5GS_INDICATOR = 1706;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Ethernet-PDN-Type-Indicator AVP CODE
   */
  int ETHERNET_PDN_TYPE_INDICATOR = 1707;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Subscribed-ARPI AVP CODE
   */
  int SUBSCRIBED_ARPI = 1708;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') IAB-Operation-Permission AVP CODE
   */
  int IAB_OPERATION_PERMISSION = 1709;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') V2X-Subscription-Data-Nr AVP CODE
   */
  int V2X_SUBSCRIPTION_DATA_NR = 1710;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') UE-PC5-QoS AVP CODE
   */
  int UE_PC5_QOS = 1711;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PC5-QoS-Flow AVP CODE
   */
  int PC5_QOS_FLOW = 1712;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') 5QI AVP CODE
   */
  int _5QI = 1713;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PC5-Flow-Bitrates AVP CODE
   */
  int PC5_FLOW_BITRATES = 1714;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Guaranteed-Flow-Bitrates AVP CODE
   */
  int GUARANTEED_FLOW_BITRATES = 1715;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Maximum-Flow-Bitrates AVP CODE
   */
  int MAXIMUM_FLOW_BITRATES = 1716;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PC5-Range AVP CODE
   */
  int PC5_RANGE = 1717;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PC5-Link-AMBR AVP CODE
   */
  int PC5_LINK_AMBR = 1718;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Third-Context-Identifier AVP CODE
   */
  int THIRD_CONTEXT_IDENTIFIER = 1719;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') MDT-Configuration-NR AVP CODE
   */
  int MDT_CONFIGURATION_NR = 1720;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Event-Threshold-SINR AVP CODE
   */
  int EVENT_THRESHOLD_SINR = 1721;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Collection-Period-RRM-NR AVP CODE
   */
  int COLLECTION_PERIOD_RRM_NR = 1722;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Collection-Period-M6-NR AVP CODE
   */
  int COLLECTION_PERIOD_M6_NR = 1723;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Collection-Period-M7-NR AVP CODE
   */
  int COLLECTION_PERIOD_M7_NR = 1724;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Sensor-Measurement AVP CODE
   */
  int SENSOR_MEASUREMENT = 1725;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') NR-Cell-Global-Identity AVP CODE
   */
  int NR_CELL_GLOBAL_IDENTITY = 1726;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') Trace-Reporting-Consumer-Uri AVP CODE
   */
  int TRACE_REPORTING_CONSUMER_URI = 1727;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') PLMN-RAT-Usage-Control AVP CODE
   */
  int PLMN_RAT_USAGE_CONTROL = 1728;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SF-ULR-Timestamp AVP CODE
   */
  int SF_ULR_TIMESTAMP = 1729;

  /**
   * 3GPP TS 29.272 (S6a/S6d, S7a/S7d and S13/S13') SF-Provisional-Indication AVP CODE
   */
  int SF_PROVISIONAL_INDICATION = 1730;





  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SMS-Information AVP code
   */
  int SMS_INFORMATION = 2000;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Data-Coding-Scheme AVP code
   */
  int DATA_CODING_SCHEME = 2001;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Destination-Interface AVP code
   */
  int DESTINATION_INTERFACE = 2002;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Interface-Id AVP code
   */
  int INTERFACE_ID = 2003;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Interface-Port AVP code
   */
  int INTERFACE_PORT = 2004;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Interface-Text AVP code
   */
  int INTERFACE_TEXT = 2005;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Interface-Type AVP code
   */
  int INTERFACE_TYPE = 2006;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SM-Message-Type AVP code
   */
  int SM_MESSAGE_TYPE = 2007;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Originator-SCCP-Address AVP code
   */
  int ORIGINATOR_SCCP_ADDRESS = 2008;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Originator-Interface AVP code
   */
  int ORIGINATOR_INTERFACE = 2009;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Recipient-SCCP-Address AVP code
   */
  int RECIPIENT_SCCP_ADDRESS = 2010;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Reply-Path-Requested AVP code
   */
  int REPLY_PATH_REQUESTED = 2011;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SM-Discharge-Time AVP code
   */
  int SM_DISCHARGE_TIME = 2012;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SM-Protocol-ID AVP code
   */
  int SM_PROTOCOL_ID = 2013;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SM-Status AVP code
   */
  int SM_STATUS = 2014;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SM-User-Data-Header AVP code
   */
  int SM_USER_DATA_HEADER = 2015;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SMS-Node AVP code
   */
  int SMS_NODE = 2016;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SMSC-Address AVP code
   */
  int SMSC_ADDRESS = 2017;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Client-Address AVP code
   */
  int CLIENT_ADDRESS = 2018;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Of-Messages-Sent AVP code
   */
  int NUMBER_OF_MESSAGES_SENT = 2019;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Low-Balance-Indication AVP code
   */
  int LOW_BALANCE_INDICATION = 2020;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Remaining-Balance AVP code
   */
  int REMAINING_BALANCE = 2021;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Refund-Information AVP code
   */
  int REFUND_INFORMATION = 2022;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Carrier-Select-Routing-Information AVP code
   */
  int CARRIER_SELECT_ROUTING_INFORMATION = 2023;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Portability-Routing-Information AVP code
   */
  int NUMBER_PORTABILITY_ROUTING_INFORMATION = 2024;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PoC-Event-Type AVP code
   */
  int POC_EVENT_TYPE = 2025;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Recipient-Info AVP code
   */
  int RECIPIENT_INFO = 2026;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Originator-Received-Address AVP code
   */
  int ORIGINATOR_RECEIVED_ADDRESS = 2027;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Recipient-Received-Address AVP code
   */
  int RECIPIENT_RECEIVED_ADDRESS = 2028;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SM-Service-Type AVP code
   */
  int SM_SERVICE_TYPE = 2029;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MMTel-Information AVP code
   */
  int MMTEL_INFORMATION = 2030;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) MMTel-SService-Type AVP code
   */
  int MMTEL_SSERVICE_TYPE = 2031;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Mode AVP code
   */
  int SERVICE_MODE = 2032;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Subscriber-Role AVP code
   */
  int SUBSCRIBER_ROLE = 2033;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Of-Diversions AVP code
   */
  int NUMBER_OF_DIVERSIONS = 2034;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Associated-Party-Address AVP code
   */
  int ASSOCIATED_PARTY_ADDRESS = 2035;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SDP-Type AVP code
   */
  int SDP_TYPE = 2036;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Change-Condition AVP code
   */
  int CHANGE_CONDITION = 2037;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Change-Time AVP code
   */
  int CHANGE_TIME = 2038;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Diagnostics AVP code
   */
  int DIAGNOSTICS = 2039;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Service-Data-Container AVP code
   */
  int SERVICE_DATA_CONTAINER = 2040;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Start-Time AVP code
   */
  int START_TIME = 2041;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Stop-Time AVP code
   */
  int STOP_TIME = 2042;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Time-First-Usage AVP code
   */
  int TIME_FIRST_USAGE = 2043;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Time-Last-Usage AVP code
   */
  int TIME_LAST_USAGE = 2044;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Time-Usage AVP code
   */
  int TIME_USAGE = 2045;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Traffic-Data-Volumes AVP code
   */
  int TRAFFIC_DATA_VOLUMES = 2046;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Serving-Node-Type AVP code
   */
  int SERVING_NODE_TYPE = 2047;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Supplementary-Service AVP code
   */
  int SUPPLEMENTARY_SERVICE = 2048;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Participant-Action-Type AVP code
   */
  int PARTICIPANT_ACTION_TYPE = 2049;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) PDN-Connection-ID AVP code
   */
  int PDN_CONNECTION_ID = 2050;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Dynamic-Address-Flag AVP code
   */
  int DYNAMIC_ADDRESS_FLAG = 2051;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Accumulated-Cost AVP code
   */
  int ACCUMULATED_COST = 2052;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) AoC-Cost-Information AVP code
   */
  int AOC_COST_INFORMATION = 2053;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) AoC-Information AVP code
   */
  int AOC_INFORMATION = 2054;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) AoC-Request-Type AVP code
   */
  int AOC_REQUEST_TYPE = 2055;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Current-Tariff AVP code
   */
  int CURRENT_TARIFF = 2056;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Next-Tariff AVP code
   */
  int NEXT_TARIFF = 2057;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Rate-Element AVP code
   */
  int RATE_ELEMENT = 2058;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Scale-Factor AVP code
   */
  int SCALE_FACTOR = 2059;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Tariff-Information AVP code
   */
  int TARIFF_INFORMATION = 2060;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Unit-Cost AVP code
   */
  int UNIT_COST = 2061;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Incremental-Cost AVP code
   */
  int INCREMENTAL_COST = 2062;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Local-Sequence-Number AVP code
   */
  int LOCAL_SEQUENCE_NUMBER = 2063;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Node-Id AVP code
   */
  int NODE_ID = 2064;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SGW-Change AVP code
   */
  int SGW_CHANGE = 2065;


  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Application-Server-ID AVP code
   */
  int APPLICATION_SERVER_ID = 2101;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Application-Service-Type AVP code
   */
  int APPLICATION_SERVICE_TYPE = 2102;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Application-Session-ID AVP code
   */
  int APPLICATION_SESSION_ID = 2103;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Delivery-Status AVP code
   */
  int DELIVERY_STATUS = 2104;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) IM-Information-AVP code
   */
  int IM_INFORMATION = 2110;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Of-Messages-Successfully-Exploded AVP code
   */
  int NUMBER_OF_MESSAGES_SUCCESSFULLY_EXPLODED = 2111;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Number-Of-Messages-Successfully-Sent AVP code
   */
  int NUMBER_OF_MESSAGES_SUCCESSFULLY_SENT = 2112;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Total-Number-Of-Messages-Exploded AVP code
   */
  int TOTAL_NUMBER_OF_MESSAGES_EXPLODED = 2113;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Total-Number-Of-Messages-Sent AVP code
   */
  int TOTAL_NUMBER_OF_MESSAGES_SENT = 2114;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) DCD-Information AVP code
   */
  int DCD_INFORMATION = 2115;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Content-ID AVP code
   */
  int CONTENT_ID = 2116;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Content-Provider ID AVP code
   */
  int CONTENT_PROVIDER_ID = 2117;


  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SIP-Request-Timestamp Fraction AVP code
   */
  int SIP_REQUEST_TIMESTAMP_FRACTION = 2301;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) SIP-Response-Timestamp-Fraction AVP code
   */
  int SIP_RESPONSE_TIMESTAMP_FRACTION = 2302;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) Online-Charging Flag AVP code
   */
  int ONLINE_CHARGING_FLAG = 2303;

  /**
   * CSG-Access-Mode AVP Code
   */
  int CSG_ACCESS_MODE = 2317;

  /**
   * CSG-Membership-Indication AVP Code
   */
  int CSG_MEMBERSHIP_INDICATION = 2318;

  /**
   * 3GPP TS 32.299 User-CSG-Information AVP code
   */
  int USER_CSG_INFORMATION = 2319;


  /*+ SLh interface AVPs */

  /**
   * 3GPP TS 29.173 (SLh interface) LMSI AVP code
   */
  int LMSI = 2400;

  /**
   * 3GPP TS 29.173 (SLh interface) Serving-Node AVP code
   */
  int SERVING_NODE = 2401;

  /**
   * 3GPP TS 29.173 (SLh interface) MME-Name AVP code
   */
  int MME_NAME = 2402;

  /**
   * 3GPP TS 29.173 (SLh interface) MSC-Number AVP code
   */
  int MSC_NUMBER = 2403;

  /**
   * 3GPP TS 29.173 (SLh interface) LCS-Capabilities-Sets AVP code
   */
  int LCS_CAPABILITIES_SETS = 2404;

  /**
   * 3GPP TS 29.173 (SLh interface) GMLC-Address AVP code
   */
  int GMLC_ADDRESS = 2405;

  /**
   * 3GPP TS 29.173 (SLh interface) Additional-Serving-Node AVP code
   */
  int ADDITIONAL_SERVING_NODE = 2406;

  /**
   * 3GPP TS 29.173 (SLh interface) PPR-Address AVP code
   */
  int PPR_ADDRESS = 2407;

  /**
   * 3GPP TS 29.173 (SLh interface) MME-Realm AVP code
   */
  int MME_REALM = 2408;

  /**
   * 3GPP TS 29.173 (SLh interface) SGSN-Name AVP code
   */
  int SGSN_NAME = 2409;

  /**
   * 3GPP TS 29.173 (SLh interface) SGSN-Realm AVP code
   */
  int SGSN_REALM = 2410;

  /**
   * 3GPP TS 29.173 (SLh interface) RIA-Flags AVP code
   */
  int RIA_FLAGS = 2411;


  /**
   * SLg (3GPP TS 29.172) SLg-Location-Type AVP code
   */
  int SLG_LOCATION_TYPE = 2500;

  /**
   * SLg (3GPP TS 29.172) LCS-EPS-Client-Name AVP code
   */
  int LCS_EPS_CLIENT_NAME = 2501;

  /**
   * SLg (3GPP TS 29.172) LCS_Requestor_Name AVP code
   */
  int LCS_REQUESTOR_NAME = 2502;

  /**
   * SLg (3GPP TS 29.172) LCS-Priority AVP code
   */
  int LCS_PRIORITY = 2503;

  /**
   * SLg (3GPP TS 29.172) LCS-QoS AVP code
   */
  int LCS_QOS = 2504;

  /**
   * SLg (3GPP TS 29.172) Horizontal-Accuracy AVP code
   */
  int HORIZONTAL_ACCURACY = 2505;

  /**
   * SLg (3GPP TS 29.172) Vertical-Accuracy AVP code
   */
  int VERTICAL_ACCURACY = 2506;

  /**
   * SLg (3GPP TS 29.172) Vertical-Requested AVP code
   */
  int VERTICAL_REQUESTED = 2507;

  /**
   * SLg (3GPP TS 29.172) Velocity-Requested AVP code
   */
  int VELOCITY_REQUESTED = 2508;

  /**
   * SLg (3GPP TS 29.172) Response-Time AVP code
   */
  int RESPONSE_TIME = 2509;

  /**
   * SLg (3GPP TS 29.172) LCS-Supported-GAD-Shapes AVP code
   */
  int LCS_SUPPORTED_GAD_SHAPES = 2510;

  /**
   * SLg (3GPP TS 29.172) LCS-Codeword AVP code
   */
  int LCS_CODEWORD = 2511;

  /**
   * SLg (3GPP TS 29.172) LCS-Privacy-Check AVP code
   */
  int LCS_PRIVACY_CHECK = 2512;

  /**
   * SLg (3GPP TS 29.172) Accuracy-Fulfilment-Indicator AVP code
   */
  int ACCURACY_FULFILMENT_INDICATOR = 2513;

  /**
   * SLg (3GPP TS 29.172) Age-Of-Location-Estimate AVP code
   */
  int AGE_OF_LOCATION_ESTIMATE = 2514;

  /**
   * SLg (3GPP TS 29.172) Velocity-Estimate 2515 AVP code
   */
  int VELOCITY_ESTIMATE = 2515;

  /**
   * SLg (3GPP TS 29.172) EUTRAN-Positioning-Data AVP code
   */
  int EUTRAN_POSITIONING_DATA = 2516;

  /**
   * SLg (3GPP TS 29.172) ECGI AVP code
   */
  int ECGI = 2517;

  /**
   * SLg (3GPP TS 29.172) Location_Event AVP code
   */
  int LOCATION_EVENT = 2518;

  /**
   * SLg (3GPP TS 29.172) Pseudonym-Indicator
   */
  int PSEUDONYM_INDICATOR = 2519;

  /**
   * SLg (3GPP TS 29.172) LCS-Service-Type-ID AVP Code
   */
  int LCS_SERVICE_TYPE_ID = 2520;

  /**
   * SLg (3GPP TS 29.172) LCS-Privacy-Check-Non-Session AVP Code
   */
  int LCS_PRIVACY_CHECK_NON_SESSION = 2521;

  /**
   * SLg (3GPP TS 29.172) LCS-Privacy-Check-Session AVP Code
   */
  int LCS_PRIVACY_CHECK_SESSION = 2522;

  /**
   * SLg (3GPP TS 29.172) LCS-QoS-Class AVP Code
   */
  int LCS_QOS_CLASS = 2523;

  /**
   * SLg (3GPP TS 29.172) GERAN-Positioning-Info AVP Code
   */
  int GERAN_POSITIONING_INFO = 2524;

  /**
   * SLg (3GPP TS 29.172) GERAN-Positioning-Data AVP Code
   */
  int GERAN_POSITIONING_DATA = 2525;

  /**
   * SLg (3GPP TS 29.172) GERAN-GANSS-Positioning-Data AVP Code
   */
  int GERAN_GANSS_POSITIONING_DATA = 2526;

  /**
   * SLg (3GPP TS 29.172) UTRAN-Positioning-Info AVP Code
   */
  int UTRAN_POSITIONING_INFO = 2527;

  /**
   * SLg (3GPP TS 29.172) UTRAN-Positioning-Data AVP Code
   */
  int UTRAN_POSITIONING_DATA = 2528;

  /**
   * SLg (3GPP TS 29.172) UTRAN-GANSS-Positioning-Data AVP Code
   */
  int UTRAN_GANSS_POSITIONING_DATA = 2529;

  /**
   * SLg (3GPP TS 29.172) LRR-Flags AVP Code
   */
  int LRR_FLAGS = 2530;

  /**
   * SLg (3GPP TS 29.172) LCS-Reference-Number AVP Code
   */
  int LCS_REFERENCE_NUMBER = 2531;

  /**
   * SLg (3GPP TS 29.172) Deferred-Location-Type AVP Code
   */
  int DEFERRED_LOCATION_TYPE = 2532;

  /**
   * 3GPP TS 29.172 (SLg interface) Area-Event-Info AVP Code
   */
  int AREA_EVENT_INFO = 2533;

  /**
   * 3GPP TS 29.172 (SLg interface) Area-Definition AVP Code
   */
  int AREA_DEFINITION = 2534;

  /**
   * 3GPP TS 29.172 (SLg interface) Area AVP Code
   */
  int AREA = 2535;

  /**
   * 3GPP TS 29.172 (SLg interface) Area-Type AVP Code
   */
  int AREA_TYPE = 2536;

  /**
   * 3GPP TS 29.172 (SLg interface) Area-Identification AVP Code
   */
  int AREA_IDENTIFICATION = 2537;

  /**
   * 3GPP TS 29.172 (SLg interface) Occurrence-Info AVP Code
   */
  int OCCURRENCE_INFO = 2538;

  /**
   * 3GPP TS 29.172 (SLg interface) Interval-Time AVP Code
   */
  int INTERVAL_TIME = 2539;

  /**
   * 3GPP TS 29.172 (SLg interface) Periodic-LDR-Information AVP Code
   */
  int PERIODIC_LDR_INFORMATION = 2540;

  /**
   * 3GPP TS 29.172 (SLg interface) Reporting-Amount AVP Code
   */
  int REPORTING_AMOUNT = 2541;

  /**
   * 3GPP TS 29.172 (SLg interface) Reporting-Interval AVP Code
   */
  int REPORTING_INTERVAL = 2542;

  /**
   * 3GPP TS 29.172 (SLg interface) Reporting-PLMN-List AVP Code
   */
  int REPORTING_PLMN_LIST = 2543;

  /**
   * 3GPP TS 29.172 (SLg interface) PLMN-ID-List AVP Code
   */
  int PLMN_ID_LIST = 2544;

  /**
   * 3GPP TS 29.172 (SLg interface) PLR-Flags AVP Code
   */
  int PLR_FLAGS = 2545;

  /**
   * 3GPP TS 29.172 (SLg interface) PLA-Flags AVP Code
   */
  int PLA_FLAGS = 2546;

  /**
   * 3GPP TS 29.172 (SLg interface) Deferred-MT-LR-Data AVP Code
   */
  int DEFERRED_MT_LR_DATA = 2547;

  /**
   * 3GPP TS 29.172 (SLg interface) Termination-Cause AVP Code;
   */
  int TERMINATION_CAUSE_3GPP = 2548;

  /**
   * 3GPP TS 29.172 (SLg interface) LRA-Flags AVP Code
   */
  int LRA_FLAGS = 2549;

  /**
   * 3GPP TS 29.172 (SLg interface) Periodic-Location-Support-Indicator AVP Code
   */
  int PERIODIC_LOCATION_SUPPORT_INDICATOR = 2550;

  /**
   * 3GPP TS 29.172 (SLg interface)
   */
  int PRIORITIZED_LIST_INDICATOR = 2551;

  /**
   * 3GPP TS 29.172 (SLg interface) ESMLC-Cell-Info AVP Code
   */
  int ESMLC_CELL_INFO = 2552;

  /**
   * 3GPP TS 29.172 (SLg interface) Cell-Portion-ID AVP Code
   */
  int CELL_PORTION_ID = 2553;

  /**
   * 3GPP TS 29.172 (SLg interface) 1xRTT-RCID AVP Code
   */
  int ONE_X_RTT_RCID = 2554;

  /**
   * 3GPP TS 29.172 (SLg interface) Delayed-Location-Reporting-Data AVP Code
   */
  int DELAYED_LOCATION_REPORTING_DATA = 2555;

  /**
   * 3GPP TS 29.172 (SLg interface) Civic-Address AVP Code
   */
  int CIVIC_ADDRESS = 2556;

  /**
   * 3GPP TS 29.172 (SLg interface) Barometric-Pressure AVP Code
   */
  int BAROMETRIC_PRESSURE = 2557;

  /**
   * 3GPP TS 29.172 (SLg interface) UTRAN-Additional-Positioning-Data AVP Code
   */
  int UTRAN_ADDITIONAL_POSITIONING_DATA = 2558;

  /**
   * 3GPP TS 29.172 (SLg interface) Motion-Event-Info AVP Code
   */
  int MOTION_EVENT_INFO = 2559;

  /**
   * 3GPP TS 29.172 (SLg interface) Linear-Distance AVP Code
   */
  int LINEAR_DISTANCE = 2560;

  /**
   * 3GPP TS 29.172 (SLg interface) Maximum-Interval AVP Code
   */
  int MAXIMUM_INTERVAL = 2561;


  /**
   * 3GPP TS 29.172 (SLg interface) Sampling-Interval AVP Code
   */
  int SAMPLING_INTERVAL = 2562;


  /**
   * 3GPP TS 29.172 (SLg interface) Reporting-Duration AVP Code
   */
  int REPORTING_DURATION = 2563;


  /**
   * 3GPP TS 29.172 (SLg interface) Reporting-Location-Requirements AVP Code
   */
  int REPORTING_LOCATION_REQUIREMENTS = 2564;

  /**
   * 3GPP TS 29.172 (SLg interface) Additional-Area AVP Code
   */
  int ADDITIONAL_AREA = 2565;

  /**
   * 3GPP TS 29.172 (SLg interface) AMF-Instance-Id AVP Code
   */
  int AMF_INSTANCE_ID = 2566;


  /**
   * 3GPP TS 32.299 BSSID AVP Code
   */
  int BSSID = 2716;


  /**
   * 3GPP TS 29.336 (S6t interface) IP-SM-GW-Number AVP Code
   */
  int IP_SM_GW_NUMBER = 3100;

  /**
   * 3GPP TS 29.336 (S6t interface) IP-SM-GW-Name AVP Code
   */
  int IP_SM_GW_NAME = 3101;

  /**
   * 3GPP TS 29.336 (S6t interface) User-Identifier AVP Code
   */
  int USER_IDENTIFIER = 3102;

  /**
   * 3GPP TS 29.336 (S6t interface) Service-ID AVP Code
   */
  int SERVICE_ID = 3103;

  /**
   * 3GPP TS 29.336 (S6t interface) SCS-Identity AVP Code
   */
  int SCS_IDENTITY = 3104;

  /**
   * 3GPP TS 29.336 (S6t interface) Service-Parameters AVP Code
   */
  int SERVICE_PARAMETERS = 3105;

  /**
   * 3GPP TS 29.336 (S6t interface) T4-Parameters AVP Code
   */
  int T4_PARAMETERS = 3106;

  /**
   * 3GPP TS 29.336 (S6t interface) Service-Data AVP Code
   */
  int SERVICE_DATA = 3107;

  /**
   * 3GPP TS 29.336 (S6t interface) T4-Data AVP Code
   */
  int T4_DATA = 3108;

  /**
   * 3GPP TS 29.336 (S6t interface) HSS-Cause AVP Code
   */
  int HSS_CAUSE = 3109;

  /**
   * 3GPP TS 29.336 (S6t interface) SIR-Flags AVP Code
   */
  int SIR_FLAGS = 3110;

  /**
   * 3GPP TS 29.336 (S6t interface) External-Identifier AVP Code
   */
  int EXTERNAL_IDENTIFIER = 3111;

  /**
   * 3GPP TS 29.336 (S6t interface) IP-SM-GW-Realm AVP Code
   */
  int IP_SM_GW_REALM = 3112;

  /**
   * 3GPP TS 29.336 (S6t interface) AESE-Communication-Pattern AVP Code
   */
  int AESE_COMMUNICATION_PATTERN = 3113;

  /**
   * 3GPP TS 29.336 (S6t interface) Communication-Pattern-Set AVP Code
   */
  int COMMUNICATION_PATTERN_SET = 3114;

  /**
   * 3GPP TS 29.336 (S6t interface) Periodic-Communication-Indicator AVP Code
   */
  int PERIODIC_COMMUNICATION_INDICATOR = 3115;

  /**
   * 3GPP TS 29.336 (S6t interface) Communication-Duration-Time AVP Code
   */
  int COMMUNICATION_DURATION_TIME = 3116;

  /**
   * 3GPP TS 29.336 (S6t interface) Periodic-Time AVP Code
   */
  int PERIODIC_TIME = 3117;

  /**
   * 3GPP TS 29.336 (S6t interface) Scheduled-Communication-Time AVP Code
   */
  int SCHEDULED_COMMUNICATION_TIME = 3118;

  /**
   * 3GPP TS 29.336 (S6t interface) Stationary-Indication AVP Code
   */
  int STATIONARY_INDICATION = 3119;

  /**
   * 3GPP TS 29.336 (S6t interface) AESE-Communication-Pattern-Config-Status AVP Code
   */
  int AESE_COMMUNICATION_PATTERN_CONFIG_STATUS = 3120;


  /**
   * 3GPP TS 29.336 (S6t interface) AESE-Error-Report AVP Code
   */
  int AESE_ERROR_REPORT = 3121;

  /**
   * 3GPP TS 29.336 (S6t interface) Monitoring-Event-Configuration AVP Code
   */
  int MONITORING_EVENT_CONFIGURATION = 3122;

  /**
   * 3GPP TS 29.336 (S6t interface) Monitoring-Event-Report AVP Code
   */
  int MONITORING_EVENT_REPORT = 3123;

  /**
   * 3GPP TS 29.336 (S6t interface) SCEF-Reference-ID AVP Code
   */
  int SCEF_REFERENCE_ID = 3124;

  /**
   * 3GPP TS 29.336 (S6t interface) SCEF-ID AVP Code
   */
  int SCEF_ID = 3125;

  /**
   * 3GPP TS 29.336 (S6t interface) SCEF-Reference-ID-for-Deletion AVP Code
   */
  int SCEF_REFERENCE_ID_FOR_DELETION = 3126;

  /**
   * 3GPP TS 29.336 (S6t interface) Monitoring-Type AVP Code
   */
  int MONITORING_TYPE = 3127;

  /**
   * 3GPP TS 29.336 (S6t interface) Maximum-Number-of-Reports AVP Code
   */
  int MAXIMUM_NUMBER_OF_REPORTS = 3128;

  /**
   * 3GPP TS 29.336 (S6t interface) UE-Reachability-Configuration AVP Code
   */
  int UE_REACHABILITY_CONFIGURATION = 3129;

  /**
   * 3GPP TS 29.336 (S6t interface) Monitoring-Duration AVP Code
   */
  int MONITORING_DURATION = 3130;


  /**
   * 3GPP TS 29.336 (S6t interface) Maximum-Detection-Time AVP Code
   */
  int MAXIMUM_DETECTION_TIME = 3131;

  /**
   * 3GPP TS 29.336 (S6t interface) Reachability-Type AVP Code
   */
  int REACHABILITY_TYPE = 3132;

  /**
   * 3GPP TS 29.336 (S6t interface) Maximum-Latency AVP Code
   */
  int MAXIMUM_LATENCY = 3133;

  /**
   * 3GPP TS 29.336 (S6t interface) Maximum-Response-Time AVP Code
   */
  int MAXIMUM_RESPONSE_TIME = 3134;

  /**
   * 3GPP TS 29.336 (S6t interface) Location-Information-Configuration AVP Code
   */
  int LOCATION_INFORMATION_CONFIGURATION = 3135;

  /**
   * 3GPP TS 29.336 (S6t interface) MONTE-Location-Type AVP Code
   */
  int MONTE_LOCATION_TYPE = 3136;

  /**
   * 3GPP TS 29.336 (S6t interface) Accuracy AVP Code
   */
  int ACCURACY = 3137;

  /**
   * 3GPP TS 29.336 (S6t interface) Association-Typ AVP Code
   */
  int ASSOCIATION_TYPE = 3138;

  /**
   * 3GPP TS 29.336 (S6t interface) Roaming-Information AVP Code
   */
  int ROAMING_INFORMATION = 3139;

  /**
   * 3GPP TS 29.336 (S6t interface) Reachability-Information AVP Code
   */
  int REACHABILITY_INFORMATION = 3140;

  /**
   * 3GPP TS 29.336 (S6t interface) IMEI-Change AVP Code
   */
  int IMEI_CHANGE = 3141;

  /**
   * 3GPP TS 29.336 (S6t interface) Monitoring-Event-Config-Status AVP Code
   */
  int MONITORING_EVENT_CONFIG_STATUS = 3142;

  /**
   * 3GPP TS 29.336 (S6t interface) Supported-Services AVP code
   */
  int SUPPORTED_SERVICES = 3143;

  /**
   * 3GPP TS 29.336 (S6t interface) Supported-Monitoring-Events AVP code
   */
  int SUPPORTED_MONITORING_EVENTS = 3144;

  /**
   * 3GPP TS 29.336 (S6t interface) CIR-Flags AVP Code
   */
  int CIR_FLAGS = 3145;

  /**
   * 3GPP TS 29.336 (S6t interface) Service-Result AVP Code
   */
  int SERVICE_RESULT = 3146;

  /**
   * 3GPP TS 29.336 (S6t interface) Service-Result-Code AVP Code
   */
  int SERVICE_RESULT_CODE = 3147;

  /**
   * 3GPP TS 29.336 (S6t interface) Reference-ID-Validity-Time AVP Code
   */
  int REFERENCE_ID_VALIDITY_TIME = 3148;

  /**
   * 3GPP TS 29.336 (S6t interface) Event-Handling AVP Code
   */
  int EVENT_HANDLING = 3149;

  /**
   * 3GPP TS 29.336 (S6t interface) NIDD-Authorization-Request AVP Code
   */
  int NIDD_AUTHORIZATION_REQUEST = 3150;

  /**
   * 3GPP TS 29.336 (S6t interface) NIDD-Authorization-Response AVP Code
   */
  int NIDD_AUTHORIZATION_RESPONSE = 3151;

  /**
   * 3GPP TS 29.336 (S6t interface) Service-Report AVP Code
   */
  int SERVICE_REPORT = 3152;

  /**
   * 3GPP TS 29.336 (S6t interface) Node-Type AVP code
   */
  int NODE_TYPE = 3153;

  /**
   * 3GPP TS 29.336 (S6t interface) S6t-HSS-Cause AVP Code
   */
  int S6T_HSS_CAUSE = 3154;

  /**
   * 3GPP TS 29.336 (S6t interface) Enhanced-Coverage-Restriction AVP Code
   */
  int ENHANCED_COVERAGE_RESTRICTION = 3155;

  /**
   * 3GPP TS 29.336 (S6t interface) Enhanced-Coverage-Restriction-Data AVP Code
   */
  int ENHANCED_COVERAGE_RESTRICTION_DATA = 3156;

  /**
   * 3GPP TS 29.336 (S6t interface) Restricted-PLMN-List AVP Code
   */
  int RESTRICTED_PLMN_LIST = 3157;

  /**
   * 3GPP TS 29.336 (S6t interface)  AVP Code
   */
  int ALLOWED_PLMN_LIST = 3158;

  /**
   * 3GPP TS 29.336 (S6t interface) Requested-Validity-Time AVP Code
   */
  int REQUESTED_VALIDITY_TIME = 3159;

  /**
   * 3GPP TS 29.336 (S6t interface) Granted-Validity-Time AVP Code
   */
  int GRANTED_VALIDITY_TIME = 3160;

  /**
   * 3GPP TS 29.336 (S6t interface) NIDD-Authorization-Update AVP Code
   */
  int NIDD_AUTHORIZATION_UPDATE = 3161;

  /**
   * 3GPP TS 29.336 (S6t interface) Loss-Of-Connectivity-Reason AVP Code
   */
  int LOSS_OF_CONNECTIVITY_REASON = 3162;

  /**
   * 3GPP TS 29.336 (S6t interface) Group-Reporting-Guard-Timer AVP Code
   */
  int GROUP_REPORTING_GUARD_TIMER = 3163;

  /**
   * 3GPP TS 29.336 (S6t interface) CIA-Flags AVP Code
   */
  int CIA_FLAGS = 3164;

  /**
   * 3GPP TS 29.336 (S6t interface) Group-Report AVP Code
   */
  int GROUP_REPORT = 3165;

  /**
   * 3GPP TS 29.336 (S6t interface) Group-Report-Item AVP Code
   */
  int GROUP_REPORT_ITEM = 3166;

  /**
   * 3GPP TS 29.336 (S6t interface) RIR-Flags AVP Code
   */
  int RIR_FLAGS = 3167;

  /**
   * 3GPP TS 29.336 (S6t interface) Type-Of-External-Identifier AVP Code
   */
  int TYPE_OF_EXTERNAL_IDENTIFIER = 3168;

  /**
   * 3GPP TS 29.336 (S6t interface) APN-Validity-Time AVP Code
   */
  int APN_VALIDITY_TIME = 3169;

  /**
   * 3GPP TS 29.336 (S6t interface) Suggested-Network-Configuration AVP Code
   */
  int SUGGESTED_NETWORK_CONFIGURATION = 3170;

  /**
   * 3GPP TS 29.336 (S6t interface) Monitoring-Event-Report-Status AVP Code
   */
  int MONITORING_EVENT_REPORT_STATUS = 3171;

  /**
   * 3GPP TS 29.336 (S6t interface) PLMN-ID-Requested AVP Code
   */
  int PLMN_ID_REQUESTED = 3172;

  /**
   * 3GPP TS 29.336 (S6t interface) Additional-Identifiers AVP Code
   */
  int ADDITIONAL_IDENTIFIERS = 3173;

  /**
   * 3GPP TS 29.336 (S6t interface) NIR-Flags AVP Code
   */
  int NIR_FLAGS = 3174;

  /**
   * 3GPP TS 29.336 (S6t interface) Reporting-Time-Stamp AVP Code
   */
  int REPORTING_TIME_STAMP = 3175;

  /**
   * 3GPP TS 29.336 (S6t interface) NIA-Flags AVP Code
   */
  int NIA_FLAGS = 3176;

  /**
   * 3GPP TS 29.336 (S6t interface) Group-User-Identifier AVP Code
   */
  int GROUP_USER_IDENTIFIER = 3177;

  /**
   * 3GPP TS 29.336 (S6t interface) MTC-Provider-Info AVP Code
   */
  int MTC_PROVIDER_INFO = 3178;

  /**
   * 3GPP TS 29.336 (S6t interface) MTC-Provider-ID AVP Code
   */
  int MTC_PROVIDER_ID = 3179;

  /**
   * 3GPP TS 29.336 (S6t interface) PDN-Connectivity-Status-Configuration AVP Code
   */
  int PDN_CONNECTIVITY_STATUS_CONFIGURATION = 3180;

  /**
   * 3GPP TS 29.336 (S6t interface) PDN-Connectivity-Status-Report AVP Code
   */
  int PDN_CONNECTIVITY_STATUS_REPORT = 3181;

  /**
   * 3GPP TS 29.336 (S6t interface) PDN-Connectivity-Status-Type AVP Code
   */
  int PDN_CONNECTIVITY_STATUS_TYPE = 3182;

  /**
   * 3GPP TS 29.336 (S6t interface) Traffic-Profile AVP Code
   */
  int TRAFFIC_PROFILE = 3183;

  /**
   * 3GPP TS 29.336 (S6t interface) Updated-Network-Configuration AVP Code
   */
  int UPDATED_NETWORK_CONFIGURATION = 3184;

  /**
   * 3GPP TS 29.336 (S6t interface) Battery-Indicator AVP Code
   */
  int BATTERY_INDICATOR = 3185;

  /**
   * 3GPP TS 29.336 (S6t interface) SCEF-Reference-ID-Ext AVP Code
   */
  int SCEF_REFERENCE_ID_EXT = 3186;

  /**
   * 3GPP TS 29.336 (S6t interface) SCEF-Reference-ID-for-Deletion-Ext AVP Code
   */
  int SCEF_REFERENCE_ID_FOR_DELETION_EXT = 3187;

  /**
   * 3GPP TS 29.336 (S6t interface) Exclude-Identifiers AVP Code
   */
  int EXCLUDE_IDENTIFIERS = 3188;

  /**
   * 3GPP TS 29.336 (S6t interface) Include-Identifiers AVP Code
   */
  int INCLUDE_IDENTIFIERS = 3189;


  /**
   * 3GPP TS 29.338 (SGd interface) SC-Address AVP Code
   */
  int SC_ADDRESS = 3300;

  /**
   * 3GPP TS 29.338 (SGd interface) SM-RP-UI AVP Code
   */
  int SM_RP_UI = 3301;

  /**
   * 3GPP TS 29.338 (SGd interface) TFR-Flags AVP Code
   */
  int TFR_FLAGS = 3302;

  /**
   * 3GPP TS 29.338 (SGd interface) SM-Delivery-Failure-Cause AVP Code
   */
  int SM_DELIVERY_FAILURE_CAUSE = 3303;

  /**
   * 3GPP TS 29.338 (SGd interface) SM-Enumerated-Delivery-Failure-Cause AVP Code
   */
  int SM_ENUMERATED_DELIVERY_FAILURE_CAUSE = 3304;

  /**
   * 3GPP TS 29.338 (SGd interface) SM-Diagnostic-Info AVP Code
   */
  int SM_DIAGNOSTIC_INFO = 3305;

  /**
   * 3GPP TS 29.338 (SGd interface) SM-Delivery-Timer AVP Code
   */
  int SM_DELIVERY_TIMER = 3306;

  /**
   * 3GPP TS 29.338 (SGd interface) SM-Delivery-Start-Time 3307 AVP Code
   */
  int SM_DELIVERY_START_TIME = 3307;

  /**
   * 3GPP TS 29.338 (S6c interface) SM-RP-MTI AVP code
   */
  int SM_RP_MTI = 3308;

  /**
   * 3GPP TS 29.338 (S6c interface) SM-RP-SMEA AVP Code
   */
  int SM_RP_SMEA = 3309;

  /**
   * 3GPP TS 29.338 (S6c interface) SRR-Flags AVP Code
   */
  int SRR_FLAGS = 3310;

  /**
   * 3GPP TS 29.338 (S6c interface) SM-Delivery-Not-Intended AVP Code
   */
  int SM_DELIVERY_NOT_INTENDED = 3311;

  /**
   * 3GPP TS 29.338 (S6c interface) MWD-Status AVP Code
   */
  int MWD_STATUS = 3312;

  /**
   * 3GPP TS 29.338 (S6c interface) MME-Absent-User-Diagnostic-SM AVP Code
   */
  int MME_ABSENT_USER_DIAGNOSTIC_SM = 3313;

  /**
   * 3GPP TS 29.338 (S6c interface) MSC-Absent-User-Diagnostic-SM AVP Code
   */
  int MSC_ABSENT_USER_DIAGNOSTIC_SM = 3314;

  /**
   * 3GPP TS 29.338 (S6c interface) SGSN-Absent-User-Diagnostic AVP Code
   */
  int SGSN_ABSENT_USER_DIAGNOSTIC = 3315;

  /**
   * 3GPP TS 29.338 (S6c interface) SM-Delivery-Outcome AVP Code
   */
  int SM_DELIVERY_OUTCOME = 3316;

  /**
   * 3GPP TS 29.338 (S6c interface)  AVP Code
   */
  int MME_SM_DELIVERY_OUTCOME = 3317;

  /**
   * 3GPP TS 29.338 (S6c interface) MSC-SM-Delivery-Outcome AVP Code
   */
  int MSC_SM_DELIVERY_OUTCOME = 3318;

  /**
   * 3GPP TS 29.338 (S6c interface) SGSN-SM-Delivery-Outcome AVP Code
   */
  int SGSN_SM_DELIVERY_OUTCOME = 3319;

  /**
   * 3GPP TS 29.338 (S6c interface) IP-SM-GW-SM-Delivery-Outcome AVP Code
   */
  int IP_SM_GW_SM_DELIVERY_OUTCOME = 3320;

  /**
   * 3GPP TS 29.338 (S6c interface) SM-Delivery-Cause AVP Code
   */
  int SM_DELIVERY_CAUSE = 3321;

  /**
   * 3GPP TS 29.338 (S6c interface) Absent-User-Diagnostic-SM AVP Code
   */
  int ABSENT_USER_DIAGNOSTIC_SM = 3322;

  /**
   * 3GPP TS 29.338 (S6c interface) RDR-Flags AVP Code
   */
  int RDR_FLAGS = 3323;

  /**
   * 3GPP TS 29.338 (SGd interface) SMSMI-Correlation-ID AVP Code
   */
  int SMSMI_CORRELATION_ID = 3324;

  /**
   * 3GPP TS 29.338 (SGd interface) HSS-ID AVP Code
   */
  int HSS_ID = 3325;

  /**
   * 3GPP TS 29.338 (SGd interface) Originating-SIP-URI AVP Code
   */
  int ORIGINATING_SIP_URI = 3326;

  /**
   * 3GPP TS 29.338 (SGd interface) Destination-SIP-URI AVP Code
   */
  int DESTINATION_SIP_URI = 3327;

  /**
   * 3GPP TS 29.338 (SGd interface) OFR-Flags AVP Code
   */
  int OFR_FLAGS = 3328;

  /**
   * 3GPP TS 29.338 (S6c interface) Maximum-UE-Availability-Time AVP Code
   */
  int MAXIMUM_UE_AVAILABILITY_TIME = 3329;

  /**
   * 3GPP TS 29.338 (SGd interface) Maximum-Retransmission-Time AVP Code
   */
  int MAXIMUM_RETRANSMISSION_TIME = 3330;

  /**
   * 3GPP TS 29.338 (SGd interface) Requested-Retransmission-Time AVP Code
   */
  int REQUESTED_RETRANSMISSION_TIME = 3331;

  /**
   * 3GPP TS 29.338 (SGd interface) SMS-GMSC-Address AVP Code
   */
  int SMS_GMSC_ADDRESS = 3332;

  /**
   * 3GPP TS 29.338 (S6c interface) SMS-GMSC-Alert-Event AVP Code
   */
  int SMS_GMSC_ALERT_EVENT = 3333;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-3GPP-Absent-User-Diagnostic-SM AVP Code
   */
  int SMSF_3GPP_ABSENT_USER_DIAGNOSTIC_SM = 3334;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-Non-3GPP-Absent-User-Diagnostic-SM AVP Code
   */
  int SMSF_NON_3GPP_ABSENT_USER_DIAGNOSTIC_SM = 3335;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-3GPP-SM-Delivery-Outcome AVP Code
   */
  int SMSF_3GPP_SM_DELIVERY_OUTCOME = 3336;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-Non-3GPP-SM-Delivery-Outcome AVP Code
   */
  int SMSF_NON_3GPP_SM_DELIVERY_OUTCOME = 3337;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-3GPP-Number AVP Code
   */
  int SMSF_3GPP_NUMBER = 3338;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-Non-3GPP-Number AVP Code
   */
  int SMSF_NON_3GPP_NUMBER = 3339;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-3GPP-Name AVP Code
   */
  int SMSF_3GPP_NAME = 3340;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-Non-3GPP-Name AVP Code
   */
  int SMSF_NON_3GPP_NAME = 3341;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-3GPP-Realm  AVP Code
   */
  int SMSF_3GPP_REALM = 3342;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-Non-3GPP-Realm AVP Code
   */
  int SMSF_NON_3GPP_REALM = 3343;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-3GPP-Address AVP Code
   */
  int SMSF_3GPP_ADDRESS = 3344;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-Non-3GPP-Address AVP Code
   */
  int SMSF_NON_3GPP_ADDRESS = 3345;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-3GPP-SBI-Support-Indicator AVP Code
   */
  int SMSF_3GPP_SBI_SUPPORT_INDICATOR = 3346;

  /**
   * 3GPP TS 29.338 (S6c interface) SMSF-Non-3GPP-SBI-Support-Indicator AVP Code
   */
  int SMSF_NON_3GPP_SBI_SUPPORT_INDICATOR = 3347;

  /**
   * 3GPP TS 29.338 (S6c interface) IP-SM-GW-SBI-Support-Indicator AVP Code
   */
  int IP_SM_GW_SBI_SUPPORT_INDICATOR = 3348;


  /**
   * 3GPP TS 29.344 ProSe-Subscription-Data AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int PROSE_SUBSCRIPTION_DATA = 3701;

  /**
   * 3GPP TS 29.344 ProSe-Permission AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int PROSE_PERMISSION = 3702;

  /**
   * 3GPP TS 29.344 ProSe-Allowed-PLMN AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int PROSE_ALLOWED_PLMN = 3703;

  /**
   * 3GPP TS 29.344 ProSe-Direct-Allowed AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int PROSE_DIRECT_ALLOWED = 3704;

  /**
   * 3GPP TS 29.344 UPR-Flags AVP code
   */
  int UPR_FLAGS = 3705;

  /**
   * 3GPP TS 29.344 PNR-Flags AVP code
   */
  int PNR_FLAGS = 3706;

  /**
   * 3GPP TS 29.344 ProSe-Initial-Location-Information AVP code
   */
  int PROSE_INITIAL_LOCATION_INFORMATION = 3707;

  /**
   * 3GPP TS 29.344 Authorized-Discovery-Range AVP code (used by S6a/S6d 3GPP TS 29.272)
   */
  int AUTHORIZED_DISCOVERY_RANGE = 3708;

  /**
   * Np (3GPP TS 29.217) eNodeB-Id AVP Code
   */
  int E_NODE_B_ID = 4008;

  /**
   * Np (3GPP TS 29.217) Extended-eNodeB-Id AVP Code
   */
  int EXTENDED_E_NODE_B_ID = 4013;

  /**
   * 3GPP TS 29.128 (T6a/T6b) Active-Time AVP code
   */
  int ACTIVE_TIME = 4324;

  /**
   * 3GPP TS 29.128 (T6a/T6b) Reachability-Cause AVP code
   */
  int REACHABILITY_CAUSE = 4325;

  /**
   * 3GPP TS 32.299 (Ro/Rf interfaces) 3GPP2 BSID AVP code
   */
  int TGPP2_BSID = 5535;

  /**
   * @return the AVP code.
   */
  int getCode();

  /**
   * @return true if Vendor-id is present in Avp header
   */
  boolean isVendorId();

  /**
   *
   * @return true if flag M is set 1
   */
  boolean isMandatory();

  /**
   * @return true if flag E is set 1
   */
  boolean isEncrypted();

  /**
   * @return Vendor-Id if present (-1 if it not available)
   */
  long getVendorId();

  /**
   * @return data as a byte array (Raw format)
   * @throws AvpDataException
   *           if data has incorrect format
   */
  byte[] getRaw() throws AvpDataException;

  /**
   * @return data as a String (Use AS-ASCI code page)
   * @throws AvpDataException
   *           if data has incorrect format
   */
  byte[] getOctetString() throws AvpDataException;

  /**
   * @return data as an integer
   * @throws AvpDataException
   *           if data has incorrect format
   */
  int getInteger32() throws AvpDataException;

  /**
   * @return data as an unsigned long
   * @throws AvpDataException
   *           if data has incorrect format
   */
  long getInteger64() throws AvpDataException;

  /**
   * @return data as an unsigned integer
   * @throws AvpDataException
   *           if data has incorrect format
   */
  long getUnsigned32() throws AvpDataException;

  /**
   * @return data as long
   * @throws AvpDataException
   *           if data has incorrect format
   */
  long getUnsigned64() throws AvpDataException;

  /**
   * @return data as a float
   * @throws AvpDataException
   *           if data has incorrect format
   */
  float getFloat32() throws AvpDataException;

  /**
   *
   * @return data as a double
   * @throws AvpDataException
   *           if data has incorrect format
   */
  double getFloat64() throws AvpDataException;

  /**
   * @return data as a Diameter Address (Inet4Address or Inet6Address)
   * @throws AvpDataException
   *           if data has incorrect format
   */
  InetAddress getAddress() throws AvpDataException;

  /**
   * @return data as an Diameter Time (millisecond is truncated)
   * @throws AvpDataException
   *           if data has incorrect format
   */
  Date getTime() throws AvpDataException;

  /**
   * @return data as a String (Use UTF-8 code page)
   * @throws AvpDataException
   *           if data has incorrect format
   */
  String getUTF8String() throws AvpDataException;

  /**
   * @return data as a String (Use AS-ASCI code page)
   * @throws AvpDataException
   *           if data has incorrect format
   */
  String getDiameterIdentity() throws AvpDataException;

  /**
   * @return data as a Diameter URI
   * @throws AvpDataException
   *           if data has incorrect format
   */
  URI getDiameterURI() throws AvpDataException;

  /**
   * @return data as an AVP group.
   * @throws AvpDataException
   *           if data has incorrect format
   */
  AvpSet getGrouped() throws AvpDataException;

  byte[] getRawData();
}

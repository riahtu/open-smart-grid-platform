package com.alliander.osgp.adapter.protocol.oslp.infra.messaging.processors;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alliander.osgp.adapter.protocol.oslp.device.DeviceResponse;
import com.alliander.osgp.adapter.protocol.oslp.device.DeviceResponseHandler;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.SetConfigurationDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.infra.messaging.DeviceRequestMessageProcessor;
import com.alliander.osgp.adapter.protocol.oslp.infra.messaging.DeviceRequestMessageType;
import com.alliander.osgp.shared.infra.jms.Constants;

/**
 * Class for processing common set configuration request messages
 * 
 * @author CGI
 * 
 */
@Component("oslpCommonSetConfigurationRequestMessageProcessor")
public class CommonSetConfigurationRequestMessageProcessor extends DeviceRequestMessageProcessor {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonSetConfigurationRequestMessageProcessor.class);

    public CommonSetConfigurationRequestMessageProcessor() {
        super(DeviceRequestMessageType.SET_CONFIGURATION);
    }

    @Override
    public void processMessage(final ObjectMessage message) {
        LOGGER.debug("Processing common set configuration message");

        String correlationUid = null;
        String domain = null;
        String domainVersion = null;
        String messageType = null;
        String organisationIdentification = null;
        String deviceIdentification = null;
        String ipAddress = null;
        Boolean isScheduled = null;
        int retryCount = 0;

        try {
            correlationUid = message.getJMSCorrelationID();
            domain = message.getStringProperty(Constants.DOMAIN);
            domainVersion = message.getStringProperty(Constants.DOMAIN_VERSION);
            messageType = message.getJMSType();
            organisationIdentification = message.getStringProperty(Constants.ORGANISATION_IDENTIFICATION);
            deviceIdentification = message.getStringProperty(Constants.DEVICE_IDENTIFICATION);
            ipAddress = message.getStringProperty(Constants.IP_ADDRESS);
            isScheduled = message.getBooleanProperty(Constants.IS_SCHEDULED);
            retryCount = message.getIntProperty(Constants.RETRY_COUNT);

        } catch (final JMSException e) {
            LOGGER.error("UNRECOVERABLE ERROR, unable to read ObjectMessage instance, giving up.", e);
            LOGGER.debug("correlationUid: {}", correlationUid);
            LOGGER.debug("domain: {}", domain);
            LOGGER.debug("domainVersion: {}", domainVersion);
            LOGGER.debug("messageType: {}", messageType);
            LOGGER.debug("organisationIdentification: {}", organisationIdentification);
            LOGGER.debug("deviceIdentification: {}", deviceIdentification);
            LOGGER.debug("ipAddress: {}", ipAddress);
            LOGGER.debug("scheduled: {}", isScheduled);
            return;
        }

        try {
            final com.alliander.osgp.dto.valueobjects.Configuration configuration = (com.alliander.osgp.dto.valueobjects.Configuration) message.getObject();

            LOGGER.info("Calling DeviceService function: {} for domain: {} {}", messageType, domain, domainVersion);

            final DeviceResponseHandler deviceResponseHandler = new DeviceResponseHandler() {

                @Override
                public void handleResponse(final DeviceResponse deviceResponse) {
                    try {
                        CommonSetConfigurationRequestMessageProcessor.this.handleScheduledEmptyDeviceResponse(deviceResponse,
                                CommonSetConfigurationRequestMessageProcessor.this.responseMessageSender, message.getStringProperty(Constants.DOMAIN), message
                                        .getStringProperty(Constants.DOMAIN_VERSION), message.getJMSType(),
                                message.propertyExists(Constants.IS_SCHEDULED) ? message.getBooleanProperty(Constants.IS_SCHEDULED) : false, message
                                        .getIntProperty(Constants.RETRY_COUNT));
                    } catch (final JMSException e) {
                        LOGGER.error("JMSException", e);
                    }

                }

                @Override
                public void handleException(final Throwable t, final DeviceResponse deviceResponse) {
                    try {
                        CommonSetConfigurationRequestMessageProcessor.this.handleUnableToConnectDeviceResponse(deviceResponse, t, configuration,
                                CommonSetConfigurationRequestMessageProcessor.this.responseMessageSender, deviceResponse,
                                message.getStringProperty(Constants.DOMAIN), message.getStringProperty(Constants.DOMAIN_VERSION), message.getJMSType(),
                                message.propertyExists(Constants.IS_SCHEDULED) ? message.getBooleanProperty(Constants.IS_SCHEDULED) : false,
                                message.getIntProperty(Constants.RETRY_COUNT));
                    } catch (final JMSException e) {
                        LOGGER.error("JMSException", e);
                    }
                }
            };

            final SetConfigurationDeviceRequest deviceRequest = new SetConfigurationDeviceRequest(organisationIdentification, deviceIdentification,
                    correlationUid, configuration);

            this.deviceService.setConfiguration(deviceRequest, deviceResponseHandler, ipAddress);

        } catch (final Exception e) {
            this.handleError(e, correlationUid, organisationIdentification, deviceIdentification, domain, domainVersion, messageType, retryCount);
        }
    }
}

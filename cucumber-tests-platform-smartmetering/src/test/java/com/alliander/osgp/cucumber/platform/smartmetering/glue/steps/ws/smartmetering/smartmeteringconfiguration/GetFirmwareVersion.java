/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.cucumber.platform.smartmetering.glue.steps.ws.smartmetering.smartmeteringconfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alliander.osgp.adapter.ws.schema.smartmetering.configuration.FirmwareVersion;
import com.alliander.osgp.adapter.ws.schema.smartmetering.configuration.GetFirmwareVersionAsyncRequest;
import com.alliander.osgp.adapter.ws.schema.smartmetering.configuration.GetFirmwareVersionAsyncResponse;
import com.alliander.osgp.adapter.ws.schema.smartmetering.configuration.GetFirmwareVersionRequest;
import com.alliander.osgp.adapter.ws.schema.smartmetering.configuration.GetFirmwareVersionResponse;
import com.alliander.osgp.cucumber.core.ScenarioContext;
import com.alliander.osgp.cucumber.platform.smartmetering.PlatformSmartmeteringKeys;
import com.alliander.osgp.cucumber.platform.smartmetering.glue.steps.ws.smartmetering.SmartMeteringStepsBase;
import com.alliander.osgp.cucumber.platform.smartmetering.support.ws.smartmetering.configuration.FirmwareVersionRequestFactory;
import com.alliander.osgp.cucumber.platform.smartmetering.support.ws.smartmetering.configuration.GetFirmwareVersionRequestFactory;
import com.alliander.osgp.cucumber.platform.smartmetering.support.ws.smartmetering.configuration.SmartMeteringConfigurationClient;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GetFirmwareVersion extends SmartMeteringStepsBase {
    protected static final Logger LOGGER = LoggerFactory.getLogger(GetFirmwareVersion.class);

    @Autowired
    private SmartMeteringConfigurationClient smartMeteringConfigurationClient;

    @When("^the get firmware version request is received$")
    public void theGetFirmwareVersionRequestIsReceived(final Map<String, String> requestData) throws Throwable {
        final Map<String, String> settings = new HashMap<>();

        settings.put(PlatformSmartmeteringKeys.KEY_DEVICE_IDENTIFICATION,
                requestData.get(PlatformSmartmeteringKeys.KEY_DEVICE_IDENTIFICATION));

        final GetFirmwareVersionRequest getFirmwareVersionRequest = GetFirmwareVersionRequestFactory
                .fromParameterMap(settings);

        final GetFirmwareVersionAsyncResponse getFirmwareVersionAsyncResponse = this.smartMeteringConfigurationClient
                .getFirmwareVersion(getFirmwareVersionRequest);

        LOGGER.info("Get firmware version asyncResponse is received {}", getFirmwareVersionAsyncResponse);

        assertNotNull("Get firmware version asyncResponse should not be null", getFirmwareVersionAsyncResponse);

        ScenarioContext.current().put(PlatformSmartmeteringKeys.KEY_CORRELATION_UID,
                getFirmwareVersionAsyncResponse.getCorrelationUid());
    }

    @Then("^the firmware version result should be returned$")
    public void theFirmwareVersionResultShouldBeReturned(final Map<String, String> settings) throws Throwable {
        final GetFirmwareVersionAsyncRequest getFirmwareVersionAsyncRequest = FirmwareVersionRequestFactory
                .fromScenarioContext();

        final GetFirmwareVersionResponse getFirmwareVersionResponse = this.smartMeteringConfigurationClient
                .retrieveGetFirmwareVersionResponse(getFirmwareVersionAsyncRequest);

        assertNotNull("Get firmware version response has result null", getFirmwareVersionResponse.getResult());

        final List<FirmwareVersion> firmwareVersions = getFirmwareVersionResponse.getFirmwareVersion();

        assertEquals(firmwareVersions.size(), 3);

        for (final FirmwareVersion receivedFirmwareVersion : firmwareVersions) {
            assertNotNull("The received firmware module type is null", receivedFirmwareVersion.getFirmwareModuleType());
            LOGGER.info("The received firmware module type: {}", receivedFirmwareVersion.getFirmwareModuleType());

            assertNotNull("The received firmware version is null", receivedFirmwareVersion.getVersion());
            LOGGER.info("The received firmware version: {}", receivedFirmwareVersion.getVersion());
        }
    }
}

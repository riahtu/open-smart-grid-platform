/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.domain.core.application.mapping;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import com.alliander.osgp.domain.core.valueobjects.Configuration;
import com.alliander.osgp.domain.core.valueobjects.DaliConfiguration;
import com.alliander.osgp.domain.core.valueobjects.LightType;
import com.alliander.osgp.domain.core.valueobjects.LinkType;
import com.alliander.osgp.domain.core.valueobjects.LongTermIntervalType;
import com.alliander.osgp.domain.core.valueobjects.MeterType;
import com.alliander.osgp.domain.core.valueobjects.RelayConfiguration;

public class ConfigurationConverter extends BidirectionalConverter<com.alliander.osgp.dto.valueobjects.Configuration, Configuration> {

    @Override
    public Configuration convertTo(final com.alliander.osgp.dto.valueobjects.Configuration source,
            final Type<Configuration> destinationType) {

        final LightType lightType = this.mapperFacade.map(source.getLightType(), LightType.class);

        final DaliConfiguration daliConfiguration = this.mapperFacade.map(source.getDaliConfiguration(),
                DaliConfiguration.class);

        final RelayConfiguration relayConfiguration = this.mapperFacade.map(source.getRelayConfiguration(),
                RelayConfiguration.class);

        final Integer shortTermHistoryIntervalMinutes = this.mapperFacade.map(
                source.getShortTermHistoryIntervalMinutes(), Integer.class);

        final LinkType preferredLinkType = this.mapperFacade.map(source.getPreferredLinkType(), LinkType.class);

        final MeterType meterType = this.mapperFacade.map(source.getMeterType(), MeterType.class);

        final Integer longTermHistoryInterval = this.mapperFacade.map(source.getLongTermHistoryInterval(),
                Integer.class);

        final LongTermIntervalType longTermHistoryIntervalType = this.mapperFacade.map(
                source.getLongTermHistoryIntervalType(), LongTermIntervalType.class);

        final Configuration configuration = new Configuration(lightType, daliConfiguration, relayConfiguration, shortTermHistoryIntervalMinutes,
                preferredLinkType, meterType, longTermHistoryInterval, longTermHistoryIntervalType);

        configuration.setAstroGateSunRiseOffset(source.getAstroGateSunRiseOffset());
        configuration.setAstroGateSunSetOffset(source.getAstroGateSunSetOffset());
        configuration.setAutomaticSummerTimingEnabled(source.isAutomaticSummerTimingEnabled());
        configuration.setCommunicationNumberOfRetries(source.getCommunicationNumberOfRetries());
        configuration.setCommunicationPauseTimeBetweenConnectionTrials(source.getCommunicationPauseTimeBetweenConnectionTrials());
        configuration.setCommunicationTimeout(source.getCommunicationTimeout());
        configuration.setDeviceFixIpValue(source.getDeviceFixIpValue());
        configuration.setDhcpEnabled(source.isDhcpEnabled());
        configuration.setOsgpPortNumber(source.getOsgpPortNumber());
        configuration.setOspgIpAddress(source.getOspgIpAddress());
        configuration.setRelayRefreshing(source.isRelayRefreshing());
        configuration.setSummerTimeDetails(source.getSummerTimeDetails());
        configuration.setSwitchingDelay(source.getSwitchingDelay());
        configuration.setTestButtonEnabled(source.isTestButtonEnabled());
        configuration.setTimeSyncFrequency(source.getTimeSyncFrequency());
        configuration.setWinterTimeDetails(source.getWinterTimeDetails());
        configuration.setRelayLinking(this.mapperFacade.mapAsList(source.getRelayLinking(), com.alliander.osgp.domain.core.valueobjects.RelayMatrix.class));

        return configuration;
    }

    @Override
    public com.alliander.osgp.dto.valueobjects.Configuration convertFrom(final Configuration source,
            final Type<com.alliander.osgp.dto.valueobjects.Configuration> destinationType) {

        final com.alliander.osgp.dto.valueobjects.LightType lightType = this.mapperFacade.map(source.getLightType(), com.alliander.osgp.dto.valueobjects.LightType.class);

        final com.alliander.osgp.dto.valueobjects.DaliConfiguration daliConfiguration = this.mapperFacade.map(source.getDaliConfiguration(),
                com.alliander.osgp.dto.valueobjects.DaliConfiguration.class);

        final com.alliander.osgp.dto.valueobjects.RelayConfiguration relayConfiguration = this.mapperFacade.map(source.getRelayConfiguration(),
                com.alliander.osgp.dto.valueobjects.RelayConfiguration.class);

        final Integer shortTermHistoryIntervalMinutes = this.mapperFacade.map(
                source.getShortTermHistoryIntervalMinutes(), Integer.class);

        final com.alliander.osgp.dto.valueobjects.LinkType preferredLinkType = this.mapperFacade.map(source.getPreferredLinkType(), com.alliander.osgp.dto.valueobjects.LinkType.class);

        final com.alliander.osgp.dto.valueobjects.MeterType meterType = this.mapperFacade.map(source.getMeterType(), com.alliander.osgp.dto.valueobjects.MeterType.class);

        final Integer longTermHistoryInterval = this.mapperFacade.map(source.getLongTermHistoryInterval(),
                Integer.class);

        final com.alliander.osgp.dto.valueobjects.LongTermIntervalType longTermHistoryIntervalType = this.mapperFacade.map(
                source.getLongTermHistoryIntervalType(), com.alliander.osgp.dto.valueobjects.LongTermIntervalType.class);


        final com.alliander.osgp.dto.valueobjects.Configuration configuration = new com.alliander.osgp.dto.valueobjects.Configuration(lightType, daliConfiguration,
                relayConfiguration, shortTermHistoryIntervalMinutes, preferredLinkType, meterType, longTermHistoryInterval, longTermHistoryIntervalType);

        configuration.setAstroGateSunRiseOffset(source.getAstroGateSunRiseOffset());
        configuration.setAstroGateSunSetOffset(source.getAstroGateSunSetOffset());
        configuration.setAutomaticSummerTimingEnabled(source.isAutomaticSummerTimingEnabled());
        configuration.setCommunicationNumberOfRetries(source.getCommunicationNumberOfRetries());
        configuration.setCommunicationPauseTimeBetweenConnectionTrials(source.getCommunicationPauseTimeBetweenConnectionTrials());
        configuration.setCommunicationTimeout(source.getCommunicationTimeout());
        configuration.setDeviceFixIpValue(source.getDeviceFixIpValue());
        configuration.setDhcpEnabled(source.isDhcpEnabled());
        configuration.setOsgpPortNumber(source.getOsgpPortNumber());
        configuration.setOspgIpAddress(source.getOspgIpAddress());
        configuration.setRelayRefreshing(source.isRelayRefreshing());
        configuration.setSummerTimeDetails(source.getSummerTimeDetails());
        configuration.setSwitchingDelay(source.getSwitchingDelay());
        configuration.setTestButtonEnabled(source.isTestButtonEnabled());
        configuration.setTimeSyncFrequency(source.getTimeSyncFrequency());
        configuration.setWinterTimeDetails(source.getWinterTimeDetails());
        configuration.setRelayLinking(this.mapperFacade.mapAsList(source.getRelayLinking(), com.alliander.osgp.dto.valueobjects.RelayMatrix.class));

        return configuration;
    }

}

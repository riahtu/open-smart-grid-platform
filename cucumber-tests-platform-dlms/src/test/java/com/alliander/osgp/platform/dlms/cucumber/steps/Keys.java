/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.platform.dlms.cucumber.steps;

import org.osgp.adapter.protocol.dlms.domain.entities.SecurityKeyType;

public class Keys {

    // Defaults keys dlms_device
    public static final String KEY_DEVICE_IDENTIFICATION = "DeviceIdentification";
    public static final String KEY_VERSION = "Version";
    public static final String KEY_ICC_ID = "IccId";
    public static final String KEY_COMMUNICATION_PROVIDER = "CommunicationProvider";
    public static final String KEY_COMMUNICATION_METHOD = "CommunicationMethod";
    public static final String KEY_HLS3ACTIVE = "Hls3active";
    public static final String KEY_HLS4ACTIVE = "Hls4active";
    public static final String KEY_HLS5ACTIVE = "Hls5active";
    public static final String KEY_CHALLENGE_LENGTH = "ChallengeLength";
    public static final String KEY_WITH_LIST_SUPPORTED = "WithListSupported";
    public static final String KEY_SELECTIVE_ACCESS_SUPPORTED = "SelectiveAccessSupported";
    public static final String KEY_IP_ADDRESS_IS_STATIC = "IpAddressIsStatic";
    public static final String KEY_PORT = "Port";
    public static final String KEY_CLIENT_ID = "ClientId";
    public static final String KEY_LOGICAL_ID = "LogicalId";
    public static final String KEY_IN_DEBUG_MODE = "InDebugMode";

    // Default keys security_key
    public static final String KEY_DLMS_DEVICE_ID = "DlmsDeviceId";
    public static final String KEY_SECURITY_KEY_TYPE[] = new String[] { "EMeterEncryption", "EMeterMaster,",
    "EMeterAuthentication" };
    public static final SecurityKeyType E_METER_SECURITY_KEYTYPES[] = new SecurityKeyType[] {
        SecurityKeyType.E_METER_ENCRYPTION, SecurityKeyType.E_METER_MASTER, SecurityKeyType.E_METER_AUTHENTICATION };
    public static final String KEY_SECURITY_KEY_TYPE_M = "SecurityKeyType";
    public static final String KEY_SECURITY_KEY_TYPE_A = "SecurityKeyType";
    public static final String KEY_SECURITY_KEY_TYPE_E = "SecurityKeyType";
    public static final String KEY_VALID_FROM = "ValidFrom";
    public static final String KEY_VALID_TO = "ValidTo";
    public static final String KEY_SECURITY_KEY[] = new String[] { "SecurityEncryption", "SecurityKeyAuthentication",
    "SecurityKeyEncryption" };
    public static final String KEY_SECURITY_KEY_M = "SecurityKey";
    public static final String KEY_SECURITY_KEY_A = "SecurityKey";
    public static final String KEY_SECURITY_KEY_E = "SecurityKey";

    // other
    public static final String KEY_DEVICE_TYPE = "";
    public static final String KEY_PROTOCOL = "";
    public static final String KEY_PROTOCOL_VERSION = "";
}

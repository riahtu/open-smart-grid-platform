/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.oslp.elster.device.responses;

import com.alliander.osgp.adapter.protocol.oslp.elster.device.DeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.elster.device.DeviceResponse;
import com.alliander.osgp.dto.valueobjects.DeviceStatusDto;

public class GetStatusDeviceResponse extends DeviceResponse {

    private final DeviceStatusDto deviceStatus;

    public GetStatusDeviceResponse(final DeviceRequest deviceRequest, final DeviceStatusDto deviceStatus) {
        super(deviceRequest.getOrganisationIdentification(), deviceRequest.getDeviceIdentification(),
                deviceRequest.getCorrelationUid(), deviceRequest.getMessagePriority());
        this.deviceStatus = deviceStatus;
    }

    public DeviceStatusDto getDeviceStatus() {
        return this.deviceStatus;
    }
}

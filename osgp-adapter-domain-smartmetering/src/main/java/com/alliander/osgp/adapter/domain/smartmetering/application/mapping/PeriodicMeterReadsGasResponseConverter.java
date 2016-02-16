/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.domain.smartmetering.application.mapping;

import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

import com.alliander.osgp.domain.core.valueobjects.smartmetering.AmrProfileStatusCode;
import com.alliander.osgp.domain.core.valueobjects.smartmetering.PeriodicMeterReadsContainerGas;
import com.alliander.osgp.dto.valueobjects.smartmetering.PeriodicMeterReadsGas;

public class PeriodicMeterReadsGasResponseConverter
        extends
        CustomConverter<com.alliander.osgp.dto.valueobjects.smartmetering.PeriodicMeterReadsContainerGas, PeriodicMeterReadsContainerGas> {
    private final StandardUnitConverter standardUnitConverter;

    public PeriodicMeterReadsGasResponseConverter(final StandardUnitConverter standardUnitConverter) {
        super();
        this.standardUnitConverter = standardUnitConverter;
    }

    @Override
    public PeriodicMeterReadsContainerGas convert(
            final com.alliander.osgp.dto.valueobjects.smartmetering.PeriodicMeterReadsContainerGas source,
            final Type<? extends PeriodicMeterReadsContainerGas> destinationType) {
        final List<com.alliander.osgp.domain.core.valueobjects.smartmetering.PeriodicMeterReadsGas> meterReadsGas = new ArrayList<>(
                source.getMeterReadsGas().size());
        for (final PeriodicMeterReadsGas pmr : source.getMeterReadsGas()) {

            final AmrProfileStatusCode amrProfileStatusCode = this.mapperFacade.map(pmr.getAmrProfileStatusCode(),
                    AmrProfileStatusCode.class);
            meterReadsGas.add(new com.alliander.osgp.domain.core.valueobjects.smartmetering.PeriodicMeterReadsGas(pmr
                    .getLogTime(),
                    this.standardUnitConverter.calculateStandardizedValue(pmr.getConsumption(), source), pmr
                            .getCaptureTime(), amrProfileStatusCode));
        }

        return new PeriodicMeterReadsContainerGas(
                com.alliander.osgp.domain.core.valueobjects.smartmetering.PeriodType.valueOf(source.getPeriodType()
                        .name()), meterReadsGas, this.standardUnitConverter.toStandardUnit(source));
    }

}

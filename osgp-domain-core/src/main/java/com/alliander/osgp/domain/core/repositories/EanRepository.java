/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alliander.osgp.domain.core.entities.Device;
import com.alliander.osgp.domain.core.entities.Ean;

@Repository
public interface EanRepository extends JpaRepository<Ean, Long>, JpaSpecificationExecutor<Ean> {

    List<Ean> findByDevice(Device device);

    Ean findByCode(Long code);

    /*
     * We need these native queries below because these entities don't have an
     * Id
     */
    @Modifying
    @Query(value = "delete from ean", nativeQuery = true)
    void deleteAllEans();
}

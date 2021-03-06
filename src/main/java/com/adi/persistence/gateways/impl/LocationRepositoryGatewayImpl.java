package com.adi.persistence.gateways.impl;

import com.adi.configuration.errorHandling.DBException;
import com.adi.persistence.gateways.interfaces.LocationRepositoryGateway;
import com.adi.persistence.model.AirlineLocations;
import com.adi.persistence.repo.AirlineLocationsRepository;
import com.adi.services.impls.SellAircraftServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;

import static com.adi.configuration.errorHandling.ErrorConstants.ERROR_RETRIEVING_FROM_DB;
import static com.adi.configuration.errorHandling.ErrorConstants.ERROR_SAVING_TO_DB;

@Service
public class LocationRepositoryGatewayImpl implements LocationRepositoryGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellAircraftServiceImpl.class);

    @Autowired
    private AirlineLocationsRepository airlineLocationsRepository;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public AirlineLocations save(AirlineLocations airlineLocations) {
        AirlineLocations airlineLocationsResult;
        try {
            airlineLocationsResult = airlineLocationsRepository.save(airlineLocations);
        } catch (Exception e) {
            throw new DBException(ERROR_SAVING_TO_DB + " " + e.getLocalizedMessage());
        }
        LOGGER.info("updated AirlineLocation: {}", airlineLocationsResult);
        return airlineLocationsResult;
    }

    @Override
    public List<AirlineLocations> getDistinctByAirlineId(Long airlineId) {
        List<AirlineLocations> airlineLocationsRes;
        try {
            airlineLocationsRes = airlineLocationsRepository.getDistinctByAirlineIdIs(airlineId);
        } catch (Exception e) {
            throw new DBException(ERROR_RETRIEVING_FROM_DB + " " + e.getLocalizedMessage());
        }
        LOGGER.info("updated AirlineLocation: {}", airlineLocationsRes);
        return airlineLocationsRes;
    }


    @Override
    public AirlineLocations getByAirlineIdAndLocationName(Long airlineId, String locationName) {
        AirlineLocations airlineLocationRes;
        try {
            airlineLocationRes = airlineLocationsRepository.getByAirlineIdAndLocationName(airlineId,locationName);
        } catch (Exception e) {
            throw new DBException(ERROR_RETRIEVING_FROM_DB + " " + e.getLocalizedMessage());
        }
        LOGGER.info("get AirlineLocation: {}", airlineLocationRes);
        return airlineLocationRes;
    }

}

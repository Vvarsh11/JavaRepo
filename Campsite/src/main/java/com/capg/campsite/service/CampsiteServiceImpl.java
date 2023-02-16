package com.capg.campsite.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.campsite.entity.Campsite;
import com.capg.campsite.exception.ResourceNotFoundException;
import com.capg.campsite.repository.CampsiteRepository;

@Service
public class CampsiteServiceImpl implements CampsiteService {

	@Autowired
	private CampsiteRepository campsiteRepository;
	private static final Logger logger =  LoggerFactory.getLogger(CampsiteServiceImpl.class);

	@Override
	public Campsite addCampsite(Campsite campsite) throws Exception {
		if (campsiteRepository.findById(campsite.getSiteId()).isPresent()) {
			throw new Exception("CampSite already exists in db  with id :" + campsite.getSiteId());
		}
		logger.info("add campsite");
		return campsiteRepository.save(campsite);
	}

	@Override
	public Campsite updateCampsite(Campsite campsite) throws ResourceNotFoundException {
		Campsite c = campsiteRepository.findById(campsite.getSiteId()).orElseThrow(
				() -> new ResourceNotFoundException("Campsite is not exists with id :" + campsite.getSiteId()));
		campsiteRepository.delete(c);
		logger.info("update campsite");
		return campsiteRepository.save(campsite);
	}

	@Override
	public Campsite getCampsite(int siteId) throws ResourceNotFoundException {
		logger.info("get campsite");
		return campsiteRepository.findById(siteId)
				.orElseThrow(() -> new ResourceNotFoundException("Campsite is not exists with id :" + siteId));

	}

	@Override
	public List<Campsite> getCampsiteDetails() throws ResourceNotFoundException {
		if (campsiteRepository.findAll().isEmpty()) {
			throw new ResourceNotFoundException("No Campsite is present in db ");
		}
		logger.info("get campsite details");
		return campsiteRepository.findAll();
	}

	@Override
	public void deleteCampsite(int siteId) throws ResourceNotFoundException {
		Campsite c = campsiteRepository.findById(siteId)
				.orElseThrow(() -> new ResourceNotFoundException("Campsite is Present with id " + siteId));
		logger.info("delete campsite");
		campsiteRepository.delete(c);
	}

}

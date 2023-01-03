package com.coderslab.crm.service;

import com.coderslab.crm.filter.CustomerFilter;
import com.coderslab.crm.filter.ManufacturerFilter;
import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Manufacturer;
import com.coderslab.crm.repository.ManufacturerRepository;
import com.coderslab.crm.specification.CustomerSpecification;
import com.coderslab.crm.specification.ManufacturerSpecification;
import com.coderslab.crm.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Manufacturer> getAllManufacturers(){
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(Long id){
        return manufacturerRepository.getById(id);
    }

    public Page<Manufacturer> findManufacturersBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, ManufacturerFilter manufacturerFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        ManufacturerSpecification spec1 = new ManufacturerSpecification(new SearchCriteria("name",":",manufacturerFilter.getName()));
        ManufacturerSpecification spec2 = new ManufacturerSpecification(new SearchCriteria("city",":",manufacturerFilter.getCity()));
        ManufacturerSpecification spec3 = new ManufacturerSpecification(new SearchCriteria("street",":",manufacturerFilter.getStreet()));
        ManufacturerSpecification spec4 = new ManufacturerSpecification(new SearchCriteria("streetNumber",":",manufacturerFilter.getStreetNumber()));
        ManufacturerSpecification spec5 = new ManufacturerSpecification(new SearchCriteria("zipCode",":",manufacturerFilter.getZipCode()));
        ManufacturerSpecification spec6 = new ManufacturerSpecification(new SearchCriteria("taxCode",":",manufacturerFilter.getTaxCode()));

        return this.manufacturerRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6), pageable);
    }

    public Manufacturer addNewManufacturer(Manufacturer manufacturer){
        return manufacturerRepository.save(manufacturer);
    }
}

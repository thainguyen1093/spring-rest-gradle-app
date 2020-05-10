package com.spring.app.services.core;

import com.spring.app.dto.core.PageContent;
import com.spring.app.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseCoreServiceImpl<REPOSITORY extends JpaRepository<ENTITY, ID> & JpaSpecificationExecutor<ENTITY>, CREATE, UPDATE, CRITERIA, DTO, ENTITY, ID> implements BaseCoreService<CREATE, UPDATE, CRITERIA, DTO, ENTITY, ID> {

  // This autowired may complaint compile error in some ide. it is ok now.
  // Just make sure your repo extends JpaRepository
  @Autowired
  private REPOSITORY repository;

  @Override
  public PageContent<Page, DTO> search(CRITERIA criteria, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);

    return Optional.ofNullable(criteria)
        .map(this::newSpecification) // create Specification object
        .map(specification -> repository.findAll(specification, pageable)) // fill all Resource that match with criteria
        .filter(entityPage -> entityPage.getTotalElements() > 0) // filter out the total element > 0
        .map(this::fromPageEntityToPageContent) // convert to our object PageContent
        .orElseThrow(() -> new ResourceNotFoundException(String.format("Can not find the the Resource with criteria '%s'", criteria))); // return the value or throw exp if not present
  }

  @Override
  public DTO get(ID id) {
    return repository.findById(id)
        .map(this::fromEntityToDto)
        .orElseThrow(() -> new ResourceNotFoundException(String.format("The resource not found with Id: '%s'", id)));
  }

  @Override
  public DTO create(CREATE createRequest) {
    ENTITY entity = fromCreateToEntity(createRequest);
    ENTITY savedEntity = repository.save(entity);
    return fromEntityToDto(savedEntity);
  }

  @Override
  public void update(ID id, UPDATE update) {
    repository.findById(id)
        .map(current -> fromOldToNew(current, update))
        .map(repository::save)
        .orElseThrow(() -> new ResourceNotFoundException(String.format("The resource not found with Id: '%s'", id)));
  }

  @Override
  public void delete(ID id) {
    if (repository.existsById(id)) {
      throw new ResourceNotFoundException(String.format("The resource not found with Id: '%s'", id));
    }

    repository.deleteById(id);
  }

  @Override
  public long count() {
    return repository.count();
  }

  @Override
  public List<DTO> fromEntityToDto(List<ENTITY> entityList) {
    return entityList.stream()
        .map(this::fromEntityToDto)
        .collect(Collectors.toList());
  }

  @Override
  public DTO fromEntityToDto(ENTITY entity) {
    DTO dto = newDto();
    BeanUtils.copyProperties(entity, dto);
    return dto;
  }

  @Override
  public ENTITY fromDtoToEntity(DTO dto) {
    ENTITY entity = newEntity();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }

  public ENTITY fromCreateToEntity(CREATE createRequest) {
    ENTITY entity = newEntity();
    BeanUtils.copyProperties(createRequest, entity);
    return entity;
  }

  public ENTITY fromOldToNew(ENTITY current, UPDATE update) {
    BeanUtils.copyProperties(update, current);
    return repository.save(current);
  }

  public PageContent<Page, DTO> fromPageEntityToPageContent(Page<ENTITY> entityPage) {
    List<DTO> dtoList = fromEntityToDto(entityPage.getContent());
    PageContent<Page, DTO> pageContent = new PageContent(entityPage);

    pageContent.setElement(dtoList);
    return pageContent;
  }

  public abstract ENTITY newEntity();

  public abstract DTO newDto();

  public abstract Specification<ENTITY> newSpecification(CRITERIA criteria);

}

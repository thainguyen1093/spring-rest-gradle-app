package com.spring.app.services.core;

import com.spring.app.dto.core.PageContent;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Using this interface when we want to implement all core method
 */
public interface BaseCoreService<CREATE, UPDATE, CRITERIA, DTO, ENTITY, ID> {

  PageContent<Page, DTO> search(CRITERIA criteria, int page, int size);

  DTO get(ID id);

  DTO create(CREATE create);

  void update(ID id, UPDATE update);

  void delete(ID id);

  long count();

  List<DTO> fromEntityToDto(List<ENTITY> entity);

  DTO fromEntityToDto(ENTITY entity);

  ENTITY fromDtoToEntity(DTO dto);

}

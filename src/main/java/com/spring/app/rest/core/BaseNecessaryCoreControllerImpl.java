package com.spring.app.rest.core;

import com.spring.app.constants.PathConstant;
import com.spring.app.dto.core.PageContent;
import com.spring.app.services.core.BaseCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Provide default implementation of all core method that need to implement
 *
 * <p>
 * Note: param SERVICE that the service extends BaseCoreService
 */
public abstract class BaseNecessaryCoreControllerImpl<SERVICE extends BaseCoreService<CREATE, UPDATE, CRITERIA, DTO, ENTITY, ID>,
    CREATE, UPDATE, CRITERIA, DTO, ENTITY, ID>
    implements BaseNecessaryCoreController<CREATE, UPDATE, CRITERIA, DTO, ID> {

  // This autowired may complaint compile error in some ide. it is ok now.
  // Just make sure your service extends BaseCoreService and mark with @Service
  @Autowired
  private SERVICE service;

  @GetMapping
  @Override
  public ResponseEntity<PageContent<Page, DTO>> search(@Valid CRITERIA criteria,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "100") int size) {
    PageContent<Page, DTO> dtoResult = service.search(criteria, page, size);
    return ResponseEntity.ok(dtoResult);
  }

  @GetMapping(path = PathConstant.ID_PATH)
  @Override
  public ResponseEntity<DTO> get(@NotBlank @Min(1) @PathVariable ID id) {
    DTO dtoResult = service.get(id);
    return ResponseEntity.ok(dtoResult);
  }

  @PostMapping
  @Override
  public ResponseEntity<DTO> create(@Valid @RequestBody CREATE dtoCreate) {
    DTO dtoResult = service.create(dtoCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(dtoResult);
  }

  @PutMapping(path = PathConstant.ID_PATH)
  @Override
  public ResponseEntity update(@NotBlank @Min(1) @PathVariable ID id,
                               @Valid @RequestBody UPDATE dtoUpdate) {
    service.update(id, dtoUpdate);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(path = PathConstant.ID_PATH)
  @Override
  public ResponseEntity delete(@NotBlank @Min(1) @PathVariable ID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(path = PathConstant.COUNT_PATH)
  @Override
  public ResponseEntity<Long> count() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(service.count());
  }
}

package com.spring.app.rest.core;

import com.spring.app.constants.PathConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * this controller using to override the method that we dont want to implement in BaseCoreController
 */
public interface BaseNecessaryCoreController<CREATE, UPDATE, CRITERIA, DTO, ID> extends BaseCoreController<CREATE, UPDATE, CRITERIA, DTO, ID> {

  /**
   * Not using post to create/update resource.
   * The method always not allow in the real world project. using PUT instead
   *
   * @return Http status code 405(Method Not Allowed) status code always.
   */
  @PostMapping(path = PathConstant.ID_PATH)
  default ResponseEntity<DTO> create(@PathVariable ID id,
                                     @Valid @RequestBody UPDATE dtoUpdate) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
  }

  /**
   * We do not implement update list resources in this project
   *
   * @return Http status code 405(Method Not Allowed) status code always.
   */
  @PutMapping
  default ResponseEntity update(@RequestBody List<UPDATE> dtoUpdateList) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
  }

  /**
   * We do not implement delete all resources in this project
   *
   * @return Http status code 405(Method Not Allowed) status code always.
   */
  @DeleteMapping
  default ResponseEntity delete() {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
  }
}

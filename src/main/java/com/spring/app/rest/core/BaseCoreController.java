package com.spring.app.rest.core;

import com.spring.app.dto.core.PageContent;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Provide all core method that controller need to implement
 * */
public interface BaseCoreController<CREATE, UPDATE, CRITERIA, DTO, ID> {

  /**
   * Using method GET.
   * Get all resource by criteria
   *
   * @return Http status code 200(Ok) and list data when success query. Http status code 404(Not Found) when not found
   */
  ResponseEntity<PageContent<Page, DTO>> search(CRITERIA criteria, int page, int size);

  /**
   * Using method GET.
   * Get specific resource by id
   *
   * @return Http status code 200(Ok) and list data when success query. Http status code 404(Not Found) when not found
   */
  ResponseEntity<DTO> get(ID id);

  /**
   * Using method POST.
   * Create a new resource
   *
   * @return Http status code 201(Created) and empty/created resource data when created success. Http status code 400(Bad Request) when resource is invalid
   */
  ResponseEntity<DTO> create(CREATE dtoCreate);

  /**
   * Using method POST.
   * Not using post to create/update resource.
   * The method always not allow. using PUT instead
   *
   * @return Http status code 405 status code always.
   */
  ResponseEntity<DTO> create(ID id, UPDATE dtoUpdate);

  /**
   * Using method PUT.
   * Update the current list of resource.
   * (rare used to update all resource)
   *
   * @return ...
   */
  ResponseEntity update(List<UPDATE> dtoUpdateList);

  /**
   * Using method PUT.
   * Update the current single resource.
   *
   * <p>
   * NOTE: we dont "create when not exist" in this project. if apply,
   * then return Http status code 201(Created) and empty/created resource data when created success. Http status code 404(Not Found) when not allowed
   *
   * @return Http status code 200(Ok) or 204(No Content) when exist resource updated. Http status code 400(Bad Request) when resource is invalid
   */
  ResponseEntity update(ID id, UPDATE dtoUpdate);

  /**
   * Using method DELETE.
   * Delete all of the resource
   *
   * @return Http status code 200(Ok) or 204(No Content) when exist resource deleted. Http status code 404(Not Found) when resource is not exist to delete
   */
  ResponseEntity delete();

  /**
   * Using method DELETE.
   * Delete the resource
   *
   * @return Http status code 200(Ok) or 204(No Content) when exist resource deleted. Http status code 404(Not Found) when resource is not exist to delete
   */
  ResponseEntity delete(ID id);

  /**
   * Using method GET.
   * count the resource
   *
   * @return Http status code 200(Ok) with number of the source when exist resource deleted(0 when not found).
   */
  ResponseEntity<Long> count();

}

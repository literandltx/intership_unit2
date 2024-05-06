package com.literandltx.intership_unit2.controller;

import com.literandltx.intership_unit2.dto.cartitem.CartItemRequest;
import com.literandltx.intership_unit2.dto.cartitem.SearchCartItemRequest;
import com.literandltx.intership_unit2.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/items")
@RestController
public class CartItemControllerV1 {
    private final CartItemService cartItemServiceV1;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> save(
            @RequestBody @Valid final CartItemRequest request
    ) {
        log.info("CartItemControllerV1.save() method was called with param: {}", request);

        return cartItemServiceV1.save(request);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> updateById(
            @RequestBody @Valid CartItemRequest request,
            @RequestParam(name = "id") final Long id
    ) {
        log.info("CartItemControllerV1.updateById method was called with params: cartItem id: {}, {}", id, request);

        return cartItemServiceV1.updateById(request, id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(
            @RequestParam(name = "id") final Long id
    ) {
        log.info("CartItemControllerV1.deleteById method was called with param: cartItemId: {}", id);

        return cartItemServiceV1.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> findById(
            @RequestParam(name = "id") final Long id
    ) {
        log.info("CartItemControllerV1.findById method was called with param: cartItemId: {}", id);

        return cartItemServiceV1.findById(id);
    }

    @GetMapping("_list")
    public ResponseEntity<?> findAll(
            final Pageable pageable
    ) {
        log.info("CartItemControllerV1.findAll method was called with param: page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize()
        );

        return cartItemServiceV1.findAll(pageable);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestPart("file") final MultipartFile multipartFile
    ) {
        log.info("CartItemControllerV1.upload method was called with param: file name: {}", multipartFile.getName());

        return cartItemServiceV1.upload(multipartFile);
    }

    @PostMapping(path = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> download(
            @RequestBody @Valid final SearchCartItemRequest searchRequest,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        log.info("CartItemControllerV1.upload method was called with param: param: {}", searchRequest);

        return cartItemServiceV1.report(searchRequest, request, response);
    }
}

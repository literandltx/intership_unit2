package com.literandltx.intership_unit2.service;

import com.literandltx.intership_unit2.dto.cartitem.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CartItemService {
    ResponseEntity<CartItemResponse> save(
            final CartItemRequest request
    );

    ResponseEntity<CartItemResponse> findById(
            final Long id
    );

    ResponseEntity<CartItemResponse> updateById(
            final CartItemRequest request,
            final Long id
    );

    ResponseEntity<Void> deleteById(
            final Long id
    );

    ResponseEntity<ListCartItemResponse> findAll(
            final Pageable pageable
    );

    ResponseEntity<Void> report(
            final SearchCartItemRequest searchRequest,
            final HttpServletRequest request,
            final HttpServletResponse response
    );

    ResponseEntity<UploadResponse> upload(
            final MultipartFile multipartFile
    );
}

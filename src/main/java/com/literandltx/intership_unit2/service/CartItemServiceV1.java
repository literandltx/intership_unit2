package com.literandltx.intership_unit2.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literandltx.intership_unit2.dto.cartitem.*;
import com.literandltx.intership_unit2.dto.cartitem.search.NumberRangeRequest;
import com.literandltx.intership_unit2.exception.UnsupportedFileExtensionException;
import com.literandltx.intership_unit2.mapper.CartItemMapper;
import com.literandltx.intership_unit2.mapper.LabelMapper;
import com.literandltx.intership_unit2.model.CartItem;
import com.literandltx.intership_unit2.model.Group;
import com.literandltx.intership_unit2.model.Label;
import com.literandltx.intership_unit2.repository.CartItemRepository;
import com.literandltx.intership_unit2.repository.GroupRepository;
import com.literandltx.intership_unit2.repository.LabelRepository;
import com.literandltx.intership_unit2.repository.specification.CartItemSpecificationBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartItemServiceV1 implements CartItemService {
    private static final String tempFileLocation = "src/main/resources/targetFile.tmp";

    private final CartItemRepository cartItemRepository;
    private final GroupRepository groupRepository;
    private final LabelRepository labelRepository;
    private final CartItemSpecificationBuilder itemSpecificationBuilder;
    private final CartItemMapper cartItemMapper;
    private final ObjectMapper objectMapper;
    private final JsonFactory factory;

    @Override
    public ResponseEntity<CartItemResponse> save(
            final CartItemRequest request
    ) {
        final Group group = groupRepository.findById(request.getGroupId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find group by id: " + request.getGroupId()));
        final CartItem model = cartItemMapper.toModel(request);
        model.getGroup().setTitle(group.getTitle());
        model.getGroup().setId(group.getId());

        final CartItem saved = cartItemRepository.save(model);
        final CartItemResponse response = cartItemMapper.toDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<CartItemResponse> findById(
            final Long id
    ) {
        final CartItem model = cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find cartItem by id: " + id));
        final CartItemResponse response = cartItemMapper.toDto(model);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CartItemResponse> updateById(
            final CartItemRequest request,
            final Long id
    ) {
        final Group group = groupRepository.findById(request.getGroupId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find group by id: " + request.getGroupId()));

        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot find cartItem by id: " + id);
        }

        final CartItem model = cartItemMapper.toModel(request);
        model.setId(id);
        model.getGroup().setTitle(group.getTitle());
        model.getGroup().setId(group.getId());

        final CartItem saved = cartItemRepository.save(model);
        final CartItemResponse response = cartItemMapper.toDto(saved);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteById(
            final Long id
    ) {
        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot find cartItem by id: " + id);
        }

        cartItemRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ListCartItemResponse> findAll(final Pageable pageable) {
        final Page<CartItem> itemsPage = cartItemRepository.findAll(pageable);
        final List<CartItemResponse> list = itemsPage.stream()
                .map(cartItemMapper::toDto)
                .toList();

        final ListCartItemResponse response = new ListCartItemResponse();
        response.setList(list);
        response.setTotalPages(itemsPage.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> report(
            final SearchCartItemRequest searchRequest,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final List<String> titles = searchRequest.getTitles();
        final List<String> description = searchRequest.getDescription();
        final NumberRangeRequest rank = searchRequest.getRank();
        final List<Long> groupIds = searchRequest.getGroupIds();

        final Specification<CartItem> build = itemSpecificationBuilder.build(titles, description, rank, groupIds);
        final List<CartItem> list = cartItemRepository.findAll(build);

        File csvFile = null;
        try {
            csvFile = toCsvFile(list);
            downloadFileResource(csvFile, response);
        } catch (final IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        } finally {
            deleteFile(csvFile);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UploadResponse> upload(
            final MultipartFile multipartFile
    ) {
        validateFile(multipartFile);

        final File file = toFile(multipartFile);
        int failed = 0;
        int success = 0;

        try (final JsonParser parser = factory.createParser(file)) {
            if (parser.nextToken() == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
                        final UploadCartItemRequest uploadRequest = objectMapper.readValue(parser, UploadCartItemRequest.class);
                        final CartItemRequest request = cartItemMapper.toDto(uploadRequest);

                        if (validateCartItem(uploadRequest)) {
                            final CartItem saved = _save(request);
                            Arrays.stream(uploadRequest.getLabels().split(",")).forEach(i -> {
                                final Label label = new Label();
                                label.setName(i);
                                label.setCartItem(saved);
                                labelRepository.save(label);
                            });

                            success++;
                        } else {
                            failed++;
                        }
                    }
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            deleteFile(file);
        }

        return ResponseEntity.ok().body(new UploadResponse(success, failed));
    }

    private CartItem _save(
            final CartItemRequest request
    ) {
        final Group group = groupRepository.findById(request.getGroupId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find group by id: " + request.getGroupId()));
        final CartItem model = cartItemMapper.toModel(request);
        model.getGroup().setTitle(group.getTitle());
        model.getGroup().setId(group.getId());

        return cartItemRepository.save(model);
    }

    private File toCsvFile(
            final List<CartItem> list
    ) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        final String fileName = "report.csv";

        try (final Writer writer = new FileWriter(fileName)) {
            final CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(new String[]{"id", "title", "description", "rank", "group_id", "group_title", "labels"});

            for (final CartItem item : list) {
                csvWriter.writeNext(new String[]{
                        item.getId().toString(),
                        item.getTitle(),
                        item.getDescription(),
                        item.getRank().toString(),
                        item.getGroup().getId().toString(),
                        item.getGroup().getTitle(),
                        item.getLabels().stream()
                                .map(Label::getName)
                                .collect(Collectors.joining(","))
                });
            }
        }

        return new File(fileName);
    }

    private boolean validateCartItem(
            final UploadCartItemRequest request
    ) {
        return request.getTitle() != null && request.getGroupId() != null;
    }

    private static File toFile(
            final MultipartFile multipartFile
    ) {
        File file;

        try {
            file = new File(tempFileLocation);
            writeToFile(file, multipartFile.getBytes());
        } catch (final IOException e) {
            throw new RuntimeException("Error creating or writing to file: " + tempFileLocation, e);
        }

        return file;
    }

    private void downloadFileResource(
            final File file,
            final HttpServletResponse response
    ) throws IOException {
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());

            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());

            final InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    private static void writeToFile(
            final File file,
            final byte[] content
    ) throws IOException {
        try (final OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content);
        }
    }

    private void validateFile(
            final MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        final String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".json"))) {
            throw new UnsupportedFileExtensionException("Invalid file format. Only .json files are supported");
        }
    }

    private void deleteFile(
            final File file
    ) {
        if (file != null && file.exists()) {
            if (!file.delete()) {
                log.info("Cannot delete file {}", file.getAbsolutePath());
            }
        }
    }

}

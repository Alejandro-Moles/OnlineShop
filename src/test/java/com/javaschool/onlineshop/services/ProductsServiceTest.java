package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.dto.TotalSaleProductDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ProductsMapper;
import com.javaschool.onlineshop.models.CategoryModel;
import com.javaschool.onlineshop.models.GenreModel;
import com.javaschool.onlineshop.models.PlatformsModel;
import com.javaschool.onlineshop.models.ProductsModel;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import com.javaschool.onlineshop.repositories.GenreRepository;
import com.javaschool.onlineshop.repositories.PlatformsRepository;
import com.javaschool.onlineshop.repositories.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductsServiceTest {
    private final ProductsRepository productsRepositoryMock = Mockito.mock(ProductsRepository.class);
    private final ProductsMapper productsMapperMock = Mockito.mock(ProductsMapper.class);
    private final CategoryRepository categoryRepositoryMock = Mockito.mock(CategoryRepository.class);
    private final PlatformsRepository platformsRepositoryMock = Mockito.mock(PlatformsRepository.class);

    private final GenreRepository genreRepositoryMock = Mockito.mock(GenreRepository.class);

    private ProductsService productsService = new ProductsService(
            productsRepositoryMock, productsMapperMock, categoryRepositoryMock, platformsRepositoryMock, genreRepositoryMock
    );

    /**
     * Test for retrieving a ProductDTO by UUID, checking if it returns a ProductDTO.
     */
    @Test
    void getProductsbyUuid_ShouldReturnProductDTO() {
        UUID productUUID = UUID.randomUUID();
        when(productsRepositoryMock.findById(productUUID)).thenReturn(Optional.of(new ProductsModel()));
        when(productsMapperMock.createProductDTO(any())).thenReturn(new ProductRequestDTO());

        ProductRequestDTO productDTO = productsService.getProductsbyUuid(productUUID);

        assertNotNull(productDTO);
        verify(productsRepositoryMock).findById(productUUID);
    }

    /**
     * Test for retrieving a ProductDTO by UUID, checking if it throws NoExistData when the product is not found.
     */
    @Test
    void getProductsbyUuid_ShouldThrowException_ProductNotFound() {
        UUID nonExistingProductUUID = UUID.randomUUID();
        when(productsRepositoryMock.findById(nonExistingProductUUID)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () ->
                productsService.getProductsbyUuid(nonExistingProductUUID));

        assertEquals("Product doesn't exist", exception.getMessage());

        verify(productsRepositoryMock).findById(nonExistingProductUUID);
    }

    /**
     * Test for updating a product, checking if it successfully updates the product.
     */
    @Test
    void updateProduct_ShouldUpdateProduct() {
        UUID productUUID = UUID.randomUUID();
        when(productsRepositoryMock.findById(productUUID)).thenReturn(Optional.of(new ProductsModel()));

        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setTitle("Updated Title");
        productDTO.setPrice(29.99);
        productDTO.setPegi(18);
        productDTO.setStock(100);
        productDTO.setIsDigital(true);
        productDTO.setDescription("Updated Description");
        productDTO.setImage("updated_image.jpg".getBytes());
        productDTO.setIsDeleted(true);

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setType("ExistingCategory");
        when(categoryRepositoryMock.findByType(any())).thenReturn(Optional.of(categoryModel));

        PlatformsModel platformsModel = new PlatformsModel();
        platformsModel.setType("ExistingPlatform");
        when(platformsRepositoryMock.findByType(any())).thenReturn(Optional.of(platformsModel));

        List<String> genreTypes = Arrays.asList("Genre1", "Genre2", "Genre3");
        when(genreRepositoryMock.findByTypeIn(genreTypes)).thenReturn(Arrays.asList(new GenreModel(), new GenreModel(), new GenreModel()));

        productDTO.setCategory(categoryModel.getType());
        productDTO.setPlatform(platformsModel.getType());
        productDTO.setGenres(genreTypes);

        assertDoesNotThrow(() -> productsService.updateProduct(productUUID, productDTO));
    }

    /**
     * Test for updating a product, checking if it throws NoExistData when the product is not found.
     */
    @Test
    void updateProduct_ShouldThrowException_ProductNotFound() {
        UUID nonExistingProductUUID = UUID.randomUUID();
        when(productsRepositoryMock.findById(nonExistingProductUUID)).thenReturn(Optional.empty());

        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setTitle("Updated Title");
        productDTO.setIsDeleted(true);

        NoExistData exception = assertThrows(NoExistData.class, () ->
                productsService.updateProduct(nonExistingProductUUID, productDTO));

        assertEquals("Product doesn't exist", exception.getMessage());

        verify(productsRepositoryMock, never()).save(any(ProductsModel.class));
    }

    /**
     * Test for saving a new product, checking if it successfully creates the product.
     */
    @Test
    void saveProduct_ShouldCreateProduct() {
        ProductRequestDTO productDTO = new ProductRequestDTO();
        UUID productUUID = UUID.randomUUID();
        productDTO.setUuid(productUUID);
        productDTO.setTitle("New Title");
        productDTO.setPrice(19.99);
        productDTO.setPegi(16);
        productDTO.setStock(50);
        productDTO.setIsDigital(true);
        productDTO.setDescription("New Description");
        productDTO.setImage("new_image.jpg".getBytes());
        productDTO.setIsDeleted(false);

        when(productsRepositoryMock.existsByTitleAndPlatformAndCategoryAndIsDigital(any(), any(), any(), any())).thenReturn(false);
        when(productsMapperMock.createProductDTO(any())).thenReturn(new ProductRequestDTO());

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setType("ExistingCategory");
        when(categoryRepositoryMock.findByType(any())).thenReturn(Optional.of(categoryModel));

        PlatformsModel platformsModel = new PlatformsModel();
        platformsModel.setType("ExistingPlatform");
        when(platformsRepositoryMock.findByType(any())).thenReturn(Optional.of(platformsModel));

        List<String> genreTypes = Arrays.asList("Genre1", "Genre2", "Genre3");
        when(genreRepositoryMock.findByTypeIn(genreTypes)).thenReturn(Arrays.asList(new GenreModel(), new GenreModel(), new GenreModel()));

        productDTO.setCategory(categoryModel.getType());
        productDTO.setPlatform(platformsModel.getType());
        productDTO.setGenres(genreTypes);

        ProductRequestDTO savedProductDTO = assertDoesNotThrow(() -> productsService.saveProduct(productDTO));

        assertNotNull(savedProductDTO);
    }

    /**
     * Test for saving a new product, checking if it throws ResourceDuplicate when the product already exists.
     */
    @Test
    void saveProduct_ShouldThrowException_ProductAlreadyExists() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setType("ExistingCategory");
        when(categoryRepositoryMock.findByType(any())).thenReturn(Optional.of(categoryModel));

        PlatformsModel platformsModel = new PlatformsModel();
        platformsModel.setType("ExistingPlatform");
        when(platformsRepositoryMock.findByType(any())).thenReturn(Optional.of(platformsModel));

        List<String> genreTypes = Arrays.asList("Genre1", "Genre2", "Genre3");
        when(genreRepositoryMock.findByTypeIn(genreTypes)).thenReturn(Arrays.asList(new GenreModel(), new GenreModel(), new GenreModel()));

        when(productsRepositoryMock.existsByTitleAndPlatformAndCategoryAndIsDigital(any(), any(), any(), any())).thenReturn(true);

        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setTitle("Existing Title");
        productDTO.setPrice(19.99);
        productDTO.setPegi(16);
        productDTO.setStock(50);
        productDTO.setIsDigital(true);
        productDTO.setDescription("Existing Description");
        productDTO.setImage("existing_image.jpg".getBytes());
        productDTO.setIsDeleted(false);

        ResourceDuplicate exception = assertThrows(ResourceDuplicate.class, () ->
                productsService.saveProduct(productDTO));

        assertEquals("Product already exists with that platform, category and format", exception.getMessage());

        verify(productsRepositoryMock, never()).save(any(ProductsModel.class));
    }

}

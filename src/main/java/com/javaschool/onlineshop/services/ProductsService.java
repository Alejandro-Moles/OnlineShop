package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.dto.TotalSaleProductDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ProductsMapper;
import com.javaschool.onlineshop.models.*;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import com.javaschool.onlineshop.repositories.GenreRepository;
import com.javaschool.onlineshop.repositories.PlatformsRepository;
import com.javaschool.onlineshop.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;
    private final CategoryRepository categoryRepository;
    private final PlatformsRepository platformsRepository;
    private final GenreRepository genreRepository;


    public ProductRequestDTO getProductsbyUuid(UUID uuid){
        ProductsModel products = loadProduct(uuid);
        return createProductDTO(products);
    }

    private ProductRequestDTO createProductDTO(ProductsModel products){
        return productsMapper.createProductDTO(products);
    }

    private ProductsModel createProductEntity(ProductRequestDTO productDTO, ProductsModel products){
        products.setTitle(productDTO.getTitle());
        products.setPrice(productDTO.getPrice());
        products.setPEGI(productDTO.getPegi());
        products.setStock(productDTO.getStock());
        products.setIsDigital(productDTO.getIsDigital());
        products.setDescription(productDTO.getDescription());
        products.setImage(productDTO.getImage());
        products.setIsDeleted(productDTO.getIsDeleted());
        products.setCategory(findCategory(productDTO.getCategory()));
        products.setPlatform(findPlatform(productDTO.getPlatform()));
        products.setGenres(getListGenres(productDTO.getGenres()));
        return products;
    }

    @Transactional
    public void updateProduct(UUID uuid, ProductRequestDTO productDTO){
        ProductsModel products = loadProduct(uuid);
        createProductEntity(productDTO, products);
        productsRepository.save(products);
    }

    @Transactional
    public ProductRequestDTO saveProduct(ProductRequestDTO productDTO){
        ProductsModel products = createProductEntity(productDTO, new ProductsModel());
        if (productsRepository.existsByTitleAndPlatform(products.getTitle(), products.getPlatform())) {
            throw new ResourceDuplicate("Product already exists with that platform");
        }
        products.setGenres(getListGenres(productDTO.getGenres()));
        productsRepository.save(products);
        return createProductDTO(products);
    }

    @Transactional
    public void deleteProduct(UUID uuid){
        ProductsModel products = loadProduct(uuid);
        products.setIsDeleted(true);
        productsRepository.save(products);
    }

    @Transactional
    public void assignGenresToProducts(UUID productUuid, List<String> genresTypes) {
        ProductsModel productsModel = loadProduct(productUuid);
        List<GenreModel> genres = genreRepository.findByTypeIn(genresTypes);
        if (genres.isEmpty()) {
            throw new NoExistData("No roles found with the provided types");
        }
        productsModel.setGenres(genres);
        productsRepository.save(productsModel);
        createProductDTO(productsModel);
    }

    @Transactional(readOnly = true)
    private PlatformsModel findPlatform(String type){
        return platformsRepository.findByType(type).orElseThrow(() -> new NoExistData("This platform don't exist"));
    }

    @Transactional(readOnly = true)
    private ProductsModel loadProduct(UUID uuid){
        return productsRepository.findById(uuid).orElseThrow(() -> new NoExistData("Product don't exist"));
    }

    @Transactional(readOnly = true)
    public List<TotalSaleProductDTO> getTopSaleProducts() {
        return productsRepository.findTop10SoldProducts();
    }

    @Transactional(readOnly = true)
    public List<ProductRequestDTO> getAvailableProducts() {
        List<ProductsModel> availableProducts = productsRepository.findByIsDeletedFalseAndStockGreaterThan(0);
        return availableProducts.stream().map(this::createProductDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductRequestDTO> getAvailableTopProducts() {
        List<ProductsModel> availableProducts = productsRepository.findTop10SoldProductsByStock();
        return availableProducts.stream().map(this::createProductDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductRequestDTO> getAllProducts(){
        return productsRepository.findAll().stream().map(this::createProductDTO).toList();
    }

    @Transactional(readOnly = true)
    private CategoryModel findCategory(String type){
        return categoryRepository.findByType(type).orElseThrow(() -> new NoExistData("This category don't exist"));
    }

    @Transactional(readOnly = true)
    private List<GenreModel> getListGenres(List<String> genres){
        return genreRepository.findByTypeIn(genres);
    }
}

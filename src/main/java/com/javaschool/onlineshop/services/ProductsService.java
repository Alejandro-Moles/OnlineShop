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

import java.util.ArrayList;
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

    // Retrieves product details by UUID
    public ProductRequestDTO getProductsbyUuid(UUID uuid) {
        ProductsModel products = loadProduct(uuid);
        return createProductDTO(products);
    }

    // Maps a ProductsModel to a ProductRequestDTO
    private ProductRequestDTO createProductDTO(ProductsModel products) {
        return productsMapper.createProductDTO(products);
    }

    // Creates a ProductsModel entity from a ProductRequestDTO
    private ProductsModel createProductEntity(ProductRequestDTO productDTO, ProductsModel products) {
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

    // Updates product details based on UUID
    @Transactional
    public void updateProduct(UUID uuid, ProductRequestDTO productDTO) {
        ProductsModel products = loadProduct(uuid);
        if(products.getTitle().equals(productDTO.getTitle()) && products.getPlatform().getType().equals(productDTO.getPlatform())
                && products.getCategory().getType().equals(productDTO.getCategory()) && products.getIsDigital().equals(productDTO.getIsDigital())){
            createProductEntity(productDTO, products);
            productsRepository.save(products);
        }else {
            if (productsRepository.existsByTitleAndPlatformAndCategoryAndIsDigital(productDTO.getTitle(), findPlatform(productDTO.getPlatform()), findCategory(productDTO.getCategory()), productDTO.getIsDigital())) {
                throw new ResourceDuplicate("Product already exists with that platform, category and format");
            }
            createProductEntity(productDTO, products);
            productsRepository.save(products);
        }
    }

    // Saves a new product to the database
    @Transactional
    public ProductRequestDTO saveProduct(ProductRequestDTO productDTO) {
        ProductsModel products = createProductEntity(productDTO, new ProductsModel());
        if (productsRepository.existsByTitleAndPlatformAndCategoryAndIsDigital(products.getTitle(), products.getPlatform(), products.getCategory(), products.getIsDigital())) {
            throw new ResourceDuplicate("Product already exists with that platform, category and format");
        }
        products.setGenres(getListGenres(productDTO.getGenres()));
        productsRepository.save(products);
        return createProductDTO(products);
    }

    // Deletes a product based on UUID
    @Transactional
    public void deleteProduct(UUID uuid) {
        ProductsModel products = loadProduct(uuid);
        products.setIsDeleted(true);
        products.setStock(0);
        productsRepository.save(products);
    }

    // Assigns genres to a product based on UUID
    @Transactional
    public void assignGenresToProducts(UUID productUuid, List<String> genresTypes) {
        ProductsModel productsModel = loadProduct(productUuid);
        List<GenreModel> genres = genreRepository.findByTypeIn(genresTypes);
        if (genres.isEmpty()) {
            throw new NoExistData("No Genres found with the provided types");
        }
        productsModel.setGenres(genres);
        productsRepository.save(productsModel);
        createProductDTO(productsModel);
    }

    // Finds a platform based on type
    @Transactional(readOnly = true)
    private PlatformsModel findPlatform(String type) {
        return platformsRepository.findByType(type).orElseThrow(() -> new NoExistData("This platform doesn't exist"));
    }

    // Retrieves a product by UUID
    @Transactional(readOnly = true)
    private ProductsModel loadProduct(UUID uuid) {
        return productsRepository.findById(uuid).orElseThrow(() -> new NoExistData("Product doesn't exist"));
    }

    // Retrieves the top 10 sold products
    @Transactional(readOnly = true)
    public List<TotalSaleProductDTO> getTopSaleProducts() {
        return productsRepository.findTop10SoldProducts();
    }

    // Retrieves all available products with stock greater than 0
    @Transactional(readOnly = true)
    public List<ProductRequestDTO> getAvailableProducts() {
        List<ProductsModel> availableProducts = productsRepository.findByIsDeletedFalseAndStockGreaterThan(0);
        return availableProducts.stream().map(this::createProductDTO).toList();
    }

    // Retrieves the top 10 available products with the highest sales
    @Transactional(readOnly = true)
    public List<ProductRequestDTO> getAvailableTopProducts() {
        List<ProductsModel> availableProducts = productsRepository.findTop10SoldProductsByStock();
        return availableProducts.stream().map(this::createProductDTO).toList();
    }

    // Retrieves all products from the database
    @Transactional(readOnly = true)
    public List<ProductRequestDTO> getAllProducts() {
        return productsRepository.findAll().stream().map(this::createProductDTO).toList();
    }

    // Finds a category based on type
    @Transactional(readOnly = true)
    private CategoryModel findCategory(String type) {
        return categoryRepository.findByType(type).orElseThrow(() -> new NoExistData("This category doesn't exist"));
    }

    // Retrieves a list of genres based on types
    @Transactional(readOnly = true)
    private List<GenreModel> getListGenres(List<String> genres) {
        return genreRepository.findByTypeIn(genres);
    }

    @Transactional
    public void updateProductStock(UUID uuid, ProductRequestDTO productDTO){
        ProductsModel products = loadProduct(uuid);
        products.setStock(productDTO.getStock());
        productsRepository.save(products);
    }

    @Transactional(readOnly = true)
    public List<ProductRequestDTO> getCheapestProducts(){
        List<ProductsModel> cheapestProducts = productsRepository.findCheapestProducts();
        return cheapestProducts.stream().map(this::createProductDTO).toList();
    }
}

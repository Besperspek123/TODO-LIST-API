package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.exception.UnauthorizedShopAccessException;
import spring.rest.shop.springrestshop.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final ShopRepository shopRepository;
    private final KeywordRepository keywordRepository;
    private final CharacteristicRepository characteristicRepository;
    private final CartProductRepository cartProductRepository;


    public List<Product> getAvailableProductsList(){
        return productRepository.findAllByOrganization_ActivityTrue();
    }

    public List<Product> getProductsFromShop(long shopId){
        return productRepository.findByOrganization_Id(shopId);
    }

    public Product getProductDetails(long id){
        return productRepository.getById(id);
    }

    public void addProduct(Product product, long shopId) throws UnauthorizedShopAccessException {
        User currentUser = SecurityContext.getCurrentUser();
        System.out.println(currentUser.getUsername());
        Organization shop = shopRepository.getOrganizationById(shopId);

        if(!shop.getOwner().equals(currentUser)
        && currentUser.getRoles().stream().noneMatch(role -> role.name().equals("ROLE_ADMIN"))){
            throw new UnauthorizedShopAccessException("You are trying add product in not your shop");
        }
//        product.setOrganization(shopService.getShopById(shopId));
        product.setOrganization(shopRepository.getOrganizationById(shopId));

        List<Characteristic> characteristicList = new ArrayList<>();
        String[] splitCharacteristicString = product.getCharacteristicsString().split(";");
        for (int i = 0; i < splitCharacteristicString.length; i++) {
            Characteristic characteristic = new Characteristic(splitCharacteristicString[i]);
            characteristicRepository.save(characteristic);
            characteristicList.add(characteristic);
        }

        product.setCharacteristicList(characteristicList);

        List<Keyword> keywordsList = new ArrayList<>();
        String[] splitKeywordsString = product.getKeywordsString().split(";");
        for (int i = 0; i < splitKeywordsString.length; i++) {
            Keyword keyword = new Keyword(splitKeywordsString[i]);
            keywordRepository.save(keyword);
            keywordsList.add(keyword);
        }

        product.setKeywordsList(keywordsList);
        System.out.println("Размер списка отзывов при создании товара: " +
                        product.getReviewsList().size());

        productRepository.save(product);
        shopRepository.getOrganizationById(shopId).getProductList().add(product);
    }

    public void deleteProductInShop(long productId){
        User currentUser = SecurityContext.getCurrentUser();
        Product product = productRepository.getById(productId);
        if(product.getOrganization().getOwner() == currentUser
                || currentUser.getRoles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN"))){

            product.setOrganization(null);
            productRepository.save(product);
        }
    }
    public List<Product> findProductByName(String name){

        productRepository.findAllByNameContaining(name);
        List<Product> allProductsByNameContaining =  productRepository.findAllByNameContaining(name);
        List<Product> allProductByNameContainingAndWhereShopActivityTrue = new ArrayList<>();
        for (Product product:allProductsByNameContaining
        ) {
            if(product.getOrganization()!=null){
                if(product.getOrganization().isActivity()){
                    allProductByNameContainingAndWhereShopActivityTrue.add(product);
                }
            }
        }
        return allProductByNameContainingAndWhereShopActivityTrue;

    }

    public void save(Product product) {
        productRepository.save(product);
    }
}


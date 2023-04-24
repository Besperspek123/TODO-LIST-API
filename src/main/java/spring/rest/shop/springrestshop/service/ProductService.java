package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.*;
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


    public List<Product> getListProducts(){
        return productRepository.getAllBy();
    }


    public Product getProductDetails(int id){
        return productRepository.getById(id);
    }
    public void saveProduct(Product product,int shopId){

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

        productRepository.save(product);
        shopRepository.getOrganizationById(shopId).getProductList().add(product);
    }

    public void deleteProduct(User currentUser,Product product){
        if(product.getOrganization().getOwner() == currentUser
                || currentUser.getRoles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN"))){
            productRepository.deleteById(product.getId());
        }
    }
    public List<Product> findProductByNameContaining(String name){

        return productRepository.findAllByNameContaining(name);

    }

}


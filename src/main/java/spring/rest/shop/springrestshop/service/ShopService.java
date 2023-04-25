package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.repository.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {
    private final ShopRepository shopRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;



    public List<Organization> getListActivityShopForCurrentUser(User currentUser){
        return shopRepository.getAllByOwnerAndActivityTrue(currentUser);
    }

    public void saveShop(Organization shop,User owner){
        if(shop.getId()== 0){
            shop.setOwner(owner);
        }
        else {
            shop.setOwner(shopRepository.getOrganizationById(shop.getId()).getOwner());
        }


        //need to change in false when be make admin mode
        shop.setActivity(false);
        shopRepository.save(shop);

    }

    public Organization getShopDetails(int id){
        return shopRepository.getOrganizationById(id);
    }

    public List<Organization> getAllShops(){
        return shopRepository.getAllBy();
    }

    public List<Organization> getShopsByNameContaining(String string){
        return shopRepository.getAllByNameContaining(string);
    }

    public void deleteShop(int id, User user) {
        List<Cart> cartsListForAllUser = cartRepository.findAll();
        for (Cart cart : cartsListForAllUser) {
            List<CartProduct> productListForCart = cart.getCartProducts();
            List<Product> listProductForDeleteShop = shopRepository.getOrganizationById(id).getProductList();

            // удаление связей в таблице cart_product
            List<CartProduct> cartProductsToRemove = new ArrayList<>();
            for (CartProduct cartProduct : productListForCart) {
                if (listProductForDeleteShop.contains(cartProduct.getProduct())) {
                    cartProductsToRemove.add(cartProduct);
                }
            }
            cart.getCartProducts().removeAll(cartProductsToRemove);
            cartProductRepository.deleteAll(cartProductsToRemove);

            cartRepository.save(cart);
        }

        // удаление продуктов
        List<Product> productsToRemove = shopRepository.getOrganizationById(id).getProductList();
        for (Product product : productsToRemove) {
            productRepository.delete(product);
        }

        shopRepository.deleteById((long) id);
    }

    public List<Organization> getAllModerationShops(){
        return shopRepository.getAllByActivityIsFalse();
    }


}


package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.repository.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {
    private final ShopRepository shopRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartProductRepository cartProductRepository;



    public List<Organization> getListActivityShopForCurrentUser(User currentUser){
        return shopRepository.getAllByOwnerAndActivityTrue(currentUser);
    }
    public List<Organization> getListModerationShopForCurrentUser(User currentUser){
        return shopRepository.getAllByOwnerAndActivityFalse(currentUser);
    }

    public void saveShop(Organization shop,User owner){
        if (shop.getName()!= null) {
            if (shop.getId() == 0) {
                shop.setOwner(owner);
            } else {
                shop.setOwner(shopRepository.getOrganizationById(shop.getId()).getOwner());
            }


            //need to change in false when be make admin mode

//        if(!owner.getRoles().contains(Role.ROLE_ADMIN)){
//            shop.setActivity(false);
//        }
            shop.setActivity(true);
            shopRepository.save(shop);
        }
    }

    public Organization getShopDetails(int id){
        return shopRepository.getOrganizationById(id);
    }

    public List<Organization> getAllShops(){
        return shopRepository.getAllByActivityIsTrue();
    }

    public List<Organization> getShopsByNameContaining(String string){
        return shopRepository.getAllByNameContainingAndActivityIsTrue(string);
    }

//    public void deleteShop(int id, User user) {
//        List<Cart> cartsListForAllUser = cartRepository.findAll();
//        for (Cart cart : cartsListForAllUser) {
//            List<CartProduct> productListForCart = cart.getCartProducts();
//            List<Product> listProductForDeleteShop = shopRepository.getOrganizationById(id).getProductList();
//
//            // удаление связей в таблице cart_product
//            List<CartProduct> cartProductsToRemove = new ArrayList<>();
//            for (CartProduct cartProduct : productListForCart) {
//                if (listProductForDeleteShop.contains(cartProduct.getProduct())) {
//                    cartProductsToRemove.add(cartProduct);
//                }
//            }
//            cart.getCartProducts().removeAll(cartProductsToRemove);
//            cartProductRepository.deleteAll(cartProductsToRemove);
//
//            cartRepository.save(cart);
//        }
//
//        // удаление продуктов
//        List<Product> productsToRemove = shopRepository.getOrganizationById(id).getProductList();
//        for (Product product : productsToRemove) {
//            productRepository.delete(product);
//        }
//
//        shopRepository.deleteById((long) id);
//    }

    public void deleteShop(int shopId, User user) {
        if(shopRepository.getOrganizationById(shopId).getOwner() == user
                || user.getRoles().contains(Role.ROLE_ADMIN)){
            List<Product> productListForDeletedShop = productRepository.findByOrganization_Id(shopId);
            for (Product product:productListForDeletedShop
            ) {
                product.setOrganization(null);
                productRepository.save(product);
            }
            List<Cart> cartThatContainCartProductWhereOrganizationNull = cartRepository.getAllBy();
            for (Cart cart:cartThatContainCartProductWhereOrganizationNull
                 ) {
                List<CartProduct> currentListCartProductInThisCart = cart.getCartProducts();
                List<CartProduct> newListCartProductInThisCart = new ArrayList<>();
                for (CartProduct cartProduct:currentListCartProductInThisCart
                     ) {
                    if(cartProduct.getProduct().getOrganization() != null){
                        newListCartProductInThisCart.add(cartProduct);
                    }
                    else {
                        cartProduct.setCart(null);
                        cartProductRepository.save(cartProduct);
                    }
                    }
                    cart.setCartProducts(newListCartProductInThisCart);
                    cartRepository.save(cart);
                    cartService.calculateTotalCost(cart);
                }
            shopRepository.deleteById((long)shopId);
            }



        }

    public List<Organization> getAllModerationShops(){
        return shopRepository.getAllByActivityIsFalse();
    }


    public void approveShop(long shopId) {
        Organization shop = shopRepository.getOrganizationById((int)shopId);
        shop.setActivity(true);
        shopRepository.save(shop);
    }

    public List<Organization> getAllModerationShopsByNameContaining(String searchQuery) {
       return shopRepository.getAllByNameContainingAndActivityIsFalse(searchQuery);
    }

    public void approveAllShops(){
        List<Organization> shopList = shopRepository.getAllByActivityIsFalse();
        for (Organization shop:shopList
             ) {
            shop.setActivity(true);
            shopRepository.save(shop);
        }
    }
    public void disapproveAllShops(User currentUser){
        List<Organization> shopList = shopRepository.getAllByActivityIsFalse();
        for (Organization shop:shopList
        ) {
            deleteShop(shop.getId(),currentUser);

        }
    }
}


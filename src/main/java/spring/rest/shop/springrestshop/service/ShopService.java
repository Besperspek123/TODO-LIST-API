package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.repository.*;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {
    private final ShopRepository shopRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;



    public List<Organization> getListActivityShopForCurrentUser(User currentUser){
        return shopRepository.getAllByOwnerAndActivityTrue(currentUser);
    }

    public void saveShop(Organization shop,User owner){
        shop.setOwner(owner);
        //need to change in false when be make admin mode
        shop.setActivity(true);
        shopRepository.save(shop);

    }

    public Organization getShopDetails(int id){
        return shopRepository.getOrganizationById(id);
    }

    public void deleteShop(int id, User user){
        List<Cart> cartsListForAllUser = cartRepository.findAll();
        for (Cart cart : cartsListForAllUser) {
            List<Product> productListForCart = cart.getProductsListInCart();
            List<Product> listProductForDeleteShop = shopRepository.getOrganizationById(id).getProductList();

            Iterator<Product> productIterator = productListForCart.iterator();
            while (productIterator.hasNext()) {
                Product product = productIterator.next();
                if (listProductForDeleteShop.contains(product)) {
                    productIterator.remove();
                }
            }

            cart.setProductsListInCart(productListForCart);
            cartRepository.save(cart);
        }




        shopRepository.deleteById((long)id);
    }
}


package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.ProductAlreadyHaveInCartException;
import spring.rest.shop.springrestshop.repository.CartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService  {
    private final UserService userService;
    private final ProductService productService;
    private final CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveCart(Cart cart){
        int pricePurchase = 0;
        for (Product product:cart.getProductsListInCart()) {
            pricePurchase += product.getPrice();
        }
        cart.setCostPurchase(pricePurchase);
        cartRepository.save(cart);
    }

    public void addProductToCart(User buyer, Product product){

        Cart cartCurrentUser = buyer.getCart();
        List<Product> listForPurchase = cartCurrentUser.getProductsListInCart();
        try {
            if(listForPurchase.contains(product)){
                throw new ProductAlreadyHaveInCartException("This product already have in Cart");
            }
            listForPurchase.add(product);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        cartCurrentUser.setProductsListInCart(listForPurchase);
        buyer.setCart(cartCurrentUser);

        saveCart(cartCurrentUser);
    }

    public void deleteProductInCart (User owner,Product product){
        Cart currentCart = owner.getCart();
        List<Product> currentProductsInCart = currentCart.getProductsListInCart();
        if (currentProductsInCart.contains(product)){
            int idForDelete = currentProductsInCart.indexOf(product);
            currentProductsInCart.remove(idForDelete);
        }
        currentCart.setProductsListInCart(currentProductsInCart);
        saveCart(currentCart);
    }

}


package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.CartProduct;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.repository.CartProductRepository;
import spring.rest.shop.springrestshop.repository.CartRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService  {
    private final UserService userService;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveCart(Cart cart){
        int pricePurchase = 0;
        for (CartProduct cartProduct:cart.getCartProducts()) {
            pricePurchase += cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
        }
        cart.setCostPurchase(pricePurchase);
        cartRepository.save(cart);
    }

    public void addProductToCart(User currentUser, CartProduct cartProduct){

        }

        public void calculateTotalCost(Cart cart){
        int totalCost = 0;
            for (CartProduct cartProduct:cart.getCartProducts()
                 ) {
                totalCost += (int) cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
            }
            cart.setCostPurchase(totalCost);
            System.out.println("В корзину сохранятеся сумма " + totalCost);
            cartRepository.save(cart);
        }


    public void deleteProductInCart(User currentUser, int productId) {
        Cart currentCart = currentUser.getCart();
        List<CartProduct> currentProductsInCart = currentCart.getCartProducts();
        List<CartProduct> newProductsListCart = new ArrayList<>();

        for (CartProduct cartProduct: currentProductsInCart
             ) {
            if(cartProduct.getProduct().getId() != productId){
                newProductsListCart.add(cartProduct);
            }
            else cartProduct.setCart(null);
            cartProductRepository.save(cartProduct);
        }

        cartRepository.save(currentCart);

        currentCart.setCartProducts(newProductsListCart);
        calculateTotalCost(currentCart);

    }

    public void updateCartItem(Cart cart, long productId, long quantity) {
        for (CartProduct cartProduct: cart.getCartProducts()
             ) {
            if(cartProduct.getProduct().getId() == productId){
                cartProduct.setQuantity((int)quantity);
                cartProductRepository.save(cartProduct);
                calculateTotalCost(cart);
            }
        }
    }
}


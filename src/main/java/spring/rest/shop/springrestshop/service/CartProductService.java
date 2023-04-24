package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartProductService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final CartProductRepository cartProductRepository;


    private final CartService cartService;
    private final CartRepository cartRepository;

    private final CharacteristicRepository characteristicRepository;

    public void saveCartProduct(User currentUser,Product product){
        Cart currentCart =currentUser.getCart();
        if(containsProduct(currentCart,product)){
            saveCartProductIfAlreadyHaveInCart(currentUser,product);
        }
        else saveCartProductIfHeDontHaveInCart(currentUser,product);
    }

    public void saveCartProductIfHeDontHaveInCart(User currentUser, Product product) {
        Cart cart = currentUser.getCart();
        CartProduct cartProductForSave = new CartProduct();
        cartProductForSave.setProduct(product);
        cartProductForSave.setQuantity(1);
        cartProductForSave.setCart(cart);

        cartProductRepository.save(cartProductForSave);
        cartRepository.save(cart);

        // получаем обновленный объект корзины из базы данных
        Optional<Cart> optionalCart = cartRepository.findById(cart.getId());
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        }

        // присваиваем новый список продуктов в корзину
        List<CartProduct> updatedProductsInCart = cart.getCartProducts();
        updatedProductsInCart.add(cartProductForSave);
        cart.setCartProducts(updatedProductsInCart);
        cartRepository.save(cart);

        // установить обновленный список продуктов в корзине
        cart.setCartProducts(updatedProductsInCart);
        System.out.println(cart.getCartProducts().size());
        cartService.calculateTotalCost(cart);
        System.out.println(cart.getCartProducts().size());
        System.out.println(currentUser.getCart().getCartProducts().size());
        log.info("called method calculate cart for new CartProduct");
    }



    public void saveCartProductIfAlreadyHaveInCart(User currentUser,Product product){
        Cart cart = currentUser.getCart();
        for (CartProduct cartProduct:cart.getCartProducts()
             ) {
            if(cartProduct.getProduct() == product){
                cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                cartProductRepository.save(cartProduct);
            }
        }
        cartService.calculateTotalCost(cart);
        log.info("called method calculate cart for not first CartProduct");

    }

    public boolean containsProduct(Cart cart,Product product){
        boolean isContains = false;
        for (CartProduct cartProduct: cart.getCartProducts()
             ) {
            if(cartProduct.getProduct() == product){
                isContains = true;
            }
        }
        return isContains;
    }

}


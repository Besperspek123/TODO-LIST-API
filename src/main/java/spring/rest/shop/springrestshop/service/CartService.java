package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.CartProduct;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.repository.CartProductRepository;
import spring.rest.shop.springrestshop.repository.CartRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
        cartRepository.save(cart);
    }

    public void addProductToCart(Product product){
        User currentUser = SecurityContext.getCurrentUser();
        Cart currentCart = currentUser.getCart();
        if(containsProduct(currentCart,product)){
            saveCartProductIfAlreadyHaveInCart(product);
        }
        else saveCartProductIfHeDontHaveInCart(product);


        }

    private void saveCartProductIfHeDontHaveInCart(Product product) {
        User currentUser = SecurityContext.getCurrentUser();
        Cart cart = currentUser.getCart();
        CartProduct cartProductForSave = new CartProduct();
        cartProductForSave.setProduct(product);
        if(product.getAmountInStore() >0){
            cartProductForSave.setQuantity(1);
        }
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
        calculateTotalCost(cart);
        System.out.println(cart.getCartProducts().size());
        System.out.println(currentUser.getCart().getCartProducts().size());
        log.info("called method calculate cart for new CartProduct");
    }



    private void saveCartProductIfAlreadyHaveInCart(Product product){
        User currentUser = SecurityContext.getCurrentUser();
        Cart cart = currentUser.getCart();
        for (CartProduct cartProduct:cart.getCartProducts()
        ) {
            if(cartProduct.getProduct() == product){
                if(cartProduct.getQuantity()+1 <= product.getAmountInStore()){
                    cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                    cartProductRepository.save(cartProduct);
                }
                else cartProductRepository.save(cartProduct);


            }
        }
        calculateTotalCost(cart);
        log.info("called method calculate cart for not first CartProduct");

    }

    public boolean checkAvailability(Product product){
        User currentUser = SecurityContext.getCurrentUser();
        Cart cart = currentUser.getCart();
        for (CartProduct cartProduct:cart.getCartProducts()
        ) {
            if(cartProduct.getProduct() == product){
                if(cartProduct.getQuantity()+1 > product.getAmountInStore()){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean containsProduct(Cart cart,Product product){
        boolean isContains = false;
        for (CartProduct cartProduct: cart.getCartProducts()
        ) {
            if(cartProduct.getProduct().equals(product)){
                isContains = true;
            }
        }
        return isContains;
    }

        public void calculateTotalCost(Cart cart){
        int totalCost = 0;
            for (CartProduct cartProduct:cart.getCartProducts()
                 ) {
                totalCost += cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
            }
            cart.setCostPurchase(totalCost);
            System.out.println("В корзину сохранятеся сумма " + totalCost);
            cartRepository.save(cart);
        }


    public void deleteProductInCart(int productId) {
        User currentUser = SecurityContext.getCurrentUser();
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


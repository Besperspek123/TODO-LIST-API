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



    public void save(CartProduct cartProduct){
        cartProductRepository.save(cartProduct);

    }
    public CartProduct getById(long id){
        return cartProductRepository.getById(id);
    }

}


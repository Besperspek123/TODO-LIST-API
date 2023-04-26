package spring.rest.shop.springrestshop.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.PermissionForSaveThisProductDeniedException;
import spring.rest.shop.springrestshop.repository.ProductRepository;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;

@Controller
@Slf4j
public class ProductController {
    @Autowired
    UserService userService;
    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;


        @GetMapping("/addProduct")
        public String addProduct(@RequestParam("shopId") int shopId,Model model, Authentication authentication){
            User currentUser = userService.findUserByUsername(authentication.getName());
            model.addAttribute("currentUser",currentUser);
            model.addAttribute("productForm",new Product());
            model.addAttribute("shopId",shopId);
            return "product/add-product";
        }
        @PostMapping("/addProduct")
        public String addProductForCurrentShop(@ModelAttribute("productForm") @Validated Product productForm,
        @RequestParam ("shopId") int shopId,Authentication authentication){
            User currentUser = userService.findUserByUsername(authentication.getName());
            if(productForm.getId() != 0){
                if(currentUser.getRoles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN"))
                        || currentUser == productService.getProductDetails((int)productForm.getId()).getOrganization().getOwner()){
                    productService.saveProduct(productForm, shopId);
                }
                else try {
                    throw new PermissionForSaveThisProductDeniedException("Denied");
                } catch (PermissionForSaveThisProductDeniedException e) {
                    System.out.println(e.getMessage());
                }
            }
            else productService.saveProduct(productForm, shopId);
        return "redirect:/viewShop?shopId="+ shopId;
        }

        @GetMapping("/editProduct")
        public String editProduct(@RequestParam("shopId") int shopId,@RequestParam("productId") int productId, Model model, Authentication authentication) {
            User currentUser = userService.findUserByUsername(authentication.getName());
            model.addAttribute("currentUser",currentUser);
            model.addAttribute("productForm", productService.getProductDetails(productId));
            model.addAttribute("shopId",shopId);
            return "product/add-product";
        }

        @PostMapping("/deleteProduct")
        public String deleteProduct(@RequestParam("productId") int productId, Model model,Authentication authentication){
            Product product = productService.getProductDetails(productId);
            int shopId = product.getOrganization().getId();
            User currentUser = userService.findUserByUsername(authentication.getName());
                productService.deleteProduct(currentUser,product);
            return "redirect:/viewShop?shopId=" + shopId;
        }


        @GetMapping("/viewProduct")
        public String viewProduct(@RequestParam("productId") int productId, Authentication authentication, Model model){
            User currentUser = userService.findUserByUsername(authentication.getName());
            model.addAttribute("currentUser",currentUser);
        model.addAttribute(productService.getProductDetails(productId));
        return "product/details";
        }
        @GetMapping("/searchProducts")
        public String searchProducts(@RequestParam("searchQuery") String searchQuery,Model model,Authentication authentication){
            List<Product> productList = productService.findProductByNameContainingAndShopActivityTrue(searchQuery);
            User currentUser = userService.findUserByUsername(authentication.getName());
            model.addAttribute("productList",productList);
            model.addAttribute("currentUser",currentUser);
            return "main";
        }
}

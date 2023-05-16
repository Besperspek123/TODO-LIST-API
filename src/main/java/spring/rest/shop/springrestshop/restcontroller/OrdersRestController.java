package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.order.OrderDTO;
import spring.rest.shop.springrestshop.exception.CartEmptyException;
import spring.rest.shop.springrestshop.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrdersRestController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<OrderDTO> getListOrders(){
        return orderService.getOrdersForCurrentUser().stream().map(OrderDTO::new).collect(Collectors.toList());

    }
    @GetMapping("/orders/{orderId}")
    public OrderDTO getOrderDetails(@PathVariable long orderId){
        return new OrderDTO(orderService.getOrderDetails(orderId));

    }
    @PostMapping("/orders")
    public ResponseEntity<String> createOrder() throws CartEmptyException {
        orderService.createOrder();
        return new ResponseEntity<>("Order created", HttpStatus.ACCEPTED);
    }



}

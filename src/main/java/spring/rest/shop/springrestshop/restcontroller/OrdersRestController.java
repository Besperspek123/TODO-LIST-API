package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Orders",description = "The orders API")

public class OrdersRestController {

    private final OrderService orderService;

    @Operation(summary = "Get orders for current User")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getListOrders(){
        List<OrderDTO> ordersList = orderService.getOrdersForCurrentUser().stream().map(OrderDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(ordersList,HttpStatus.OK);
    }
    @Operation(summary = "Get info about user order")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable long orderId){
        return new ResponseEntity<>(new OrderDTO(orderService.getOrderDetails(orderId)),HttpStatus.OK);

    }
    @PostMapping("/orders")
    @Operation(summary = "Create an order")
    public ResponseEntity<String> createOrder() throws CartEmptyException {
        orderService.createOrder();
        return new ResponseEntity<>("Order created", HttpStatus.OK);
    }



}

package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.rest.shop.springrestshop.dto.order.OrderDTO;
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


}

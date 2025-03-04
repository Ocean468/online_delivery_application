package com.delivery.app.online_delivery_application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.app.online_delivery_application.dao.CartDao;
import com.delivery.app.online_delivery_application.dao.UserDao;
import com.delivery.app.online_delivery_application.dto.CustomerDTO;
import com.delivery.app.online_delivery_application.dto.FeedbackDTO;
import com.delivery.app.online_delivery_application.dto.FeedbackResponseDTO;
import com.delivery.app.online_delivery_application.model.Cart;
import com.delivery.app.online_delivery_application.model.Product;
import com.delivery.app.online_delivery_application.model.User;
import com.delivery.app.online_delivery_application.service.CartService;
import com.delivery.app.online_delivery_application.service.CustomerService;
import com.delivery.app.online_delivery_application.service.FeedbackService;
import com.delivery.app.online_delivery_application.service.OrderService;
import com.delivery.app.online_delivery_application.service.ProductService;
import com.delivery.app.online_delivery_application.service.UserService;



@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    CartService cartService;

    @Autowired
    CartDao cartDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;


    
     // 1. Customer Registration
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.ok("Customer registered successfully");
    }



    // 2. Browse Products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    
    // 3. Add Product to Cart
    @PostMapping("/cart/add/{productId}")
    public ResponseEntity<String> addToCart(
            @PathVariable Long productId, 
            @RequestParam(defaultValue = "1") int quantity,  // Default quantity = 1
            Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInEmail = userDetails.getUsername();

        // Find the user by email
        User user = userService.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Add the product to the user's cart
        cartService.addToCart(user.getId(), productId, quantity);  // Pass quantity

        return ResponseEntity.ok("Product added to cart successfully");
    }

    
    // 4. Get cart...
    @GetMapping("/cart")
    public ResponseEntity<?> getUserCart(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInEmail = userDetails.getUsername();

        // Get the logged-in user
        User user = userService.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the cart of the logged-in user
        Optional<Cart> cart = cartService.getCartByUser(user.getId());

        if (cart.isEmpty()) {
            return ResponseEntity.status(404).body("No cart found for the user");
        }

        // Return only cart ID and products
        return ResponseEntity.ok(new CartResponse(cart.get().getId(), cart.get().getProducts()));
    }

     // DTO class for filtering user details
     static class CartResponse {
        private Long cartId;
        private List<Product> products;

        public CartResponse(Long cartId, List<Product> products) {
            this.cartId = cartId;
            this.products = products;
        }

        public Long getCartId() { return cartId; }
        public List<Product> getProducts() { return products; }
    }

    // 5. Place Order
    @PostMapping("/orders/place")
    public ResponseEntity<String> placeOrder(@RequestParam Long customerId) {
        orderService.placeOrder(customerId);
        return ResponseEntity.ok("Order placed successfully");
    }

    // 6. Track Order Status
    @GetMapping("/orders/status/{orderId}")
    public ResponseEntity<Map<String, String>> trackOrderStatus(@PathVariable Long orderId) {
        String status = orderService.trackOrderStatus(orderId);
        Map<String, String> response = new HashMap<>();
        response.put("orderId", orderId.toString());
        response.put("status", status);
        return ResponseEntity.ok(response);
    }


    // 7. Provide Feedback
    @PostMapping("/feedback")
    public ResponseEntity<String> provideFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        feedbackService.saveFeedback(
                feedbackDTO.getCustomerId(),
                feedbackDTO.getProductId(),
                feedbackDTO.getRating(),
                feedbackDTO.getReview()
        );
        return ResponseEntity.ok("Feedback submitted successfully");
    }
    
    //8.Fetching Feedback for a Product
    @GetMapping("/feedback/{productId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbackForProduct(@PathVariable Long productId) {
        List<FeedbackResponseDTO> feedbackList = feedbackService.getFeedbackByProduct(productId);
        System.out.println("Feedback Response DTOs: " + feedbackList);
        return ResponseEntity.ok(feedbackList);
    }
    




}

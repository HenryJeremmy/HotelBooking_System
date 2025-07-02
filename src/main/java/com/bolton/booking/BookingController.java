package com.bolton.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Controller
public class BookingController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            userService.registerUser(username, email, password);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/book")
    public String showBookingForm(Model model) {
        model.addAttribute("rooms", bookingService.getAllRooms());
        return "book";
    }
    @PostMapping("/book")
    public String createBooking(@RequestParam Long roomId, @RequestParam String startTime, @RequestParam String endTime, Model model) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(username);
            LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            bookingService.createBooking(user, roomId, start, end);
            return "redirect:/bookings";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("rooms", bookingService.getAllRooms());
            return "book";
        }
    }
    @GetMapping("/bookings")
    public String showBookings(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("bookings", bookingService.getUserBookings(user.getId()));
        return "bookings";
    }
}

package com.tourism.tourconnect_backend.controller;

import com.tourism.tourconnect_backend.model.Booking;
import com.tourism.tourconnect_backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService service;

    // ✅ CREATE BOOKING
    @PostMapping
    public Booking create(@RequestBody Booking b) {
        return service.create(b);
    }

    // ✅ GET ALL BOOKINGS
    @GetMapping
    public List<Booking> getAll() {
        return service.getAll();
    }

    // ✅ GET BOOKINGS FOR USER
    @GetMapping("/user/{email}")
    public List<Booking> getUserBookings(@PathVariable String email) {
        return service.getAll().stream()
            .filter(b -> email.equalsIgnoreCase(b.getUserEmail()))
            .collect(java.util.stream.Collectors.toList());
    }

    // ✅ GET BOOKINGS FOR HOST
    @GetMapping("/host/{hostId}")
    public List<Booking> getHostBookings(@PathVariable Long hostId) {
        return service.getByHost(hostId);
    }

    // ✅ UPDATE STATUS
    @PutMapping("/{id}/status")
    public Booking updateStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        String status = body.getOrDefault("status", body.getOrDefault("homestayStatus", body.getOrDefault("guideStatus", "pending")));
        if (body.containsKey("homestayStatus")) status = "homestay_" + body.get("homestayStatus");
        else if (body.containsKey("guideStatus")) status = "guide_" + body.get("guideStatus");
        return service.updateStatus(id, status);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
}
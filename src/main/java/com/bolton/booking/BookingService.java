package com.bolton.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    public Booking createBooking(User user, Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        if (endTime.isBefore(startTime) || startTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid booking time");
        }
        Booking booking = new Booking(user, room, startTime, endTime);
        return bookingRepository.save(booking);
    }
    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}

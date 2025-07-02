package com.bolton.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    void tearDown() {
        roomRepository.deleteAll();
    }

    @Test
    void testSaveRoom() {
        Room room = new Room();
        room.setName("Premium");
        Room savedRoom = roomRepository.save(room);
        assertThat(savedRoom.getId()).isNotNull();
        assertThat(savedRoom.getName()).isEqualTo("Premium");
    }

    @Test
    void testFindAllRooms() {
        Room room1 = new Room();
        room1.setName("Premium");
        Room room2 = new Room();
        room2.setName("Suite");
        roomRepository.save(room1);
        roomRepository.save(room2);
        assertThat(roomRepository.findAll()).hasSize(2);
    }
}
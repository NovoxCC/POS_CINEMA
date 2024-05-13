package com.poscinema.pos_cinema.models;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCreateCard() {
        Card card = Card.createCard("owner123", 100);

        assertNotNull(card);
        assertNotNull(card.getCardNumber());
        assertNotNull(card.getCvv());
        assertEquals("owner123", card.getOwnerId());
        assertEquals(100, card.getBalance());
    }

    @Test
    public void testGetCardNumber() {
        Card card = new Card("1234567890123456", "123", "owner123", 100);

        assertEquals("1234567890123456", card.getCardNumber());
    }

    @Test
    public void testGetCvv() {
        Card card = new Card("1234567890123456", "123", "owner123", 100);

        assertEquals("123", card.getCvv());
    }

    @Test
    public void testSetCvv() {
        Card card = new Card("1234567890123456", "123", "owner123", 100);

        card.setCvv("456");
        assertEquals("456", card.getCvv());
    }

    @Test
    public void testGetOwnerId() {
        Card card = new Card("1234567890123456", "123", "owner123", 100);

        assertEquals("owner123", card.getOwnerId());
    }

    @Test
    public void testSetOwnerId() {
        Card card = new Card("1234567890123456", "123", "owner123", 100);

        card.setOwnerId("newOwner");
        assertEquals("newOwner", card.getOwnerId());
    }

    @Test
    public void testGetBalance() {
        Card card = new Card("1234567890123456", "123", "owner123", 100);

        assertEquals(100, card.getBalance());
    }

    @Test
    public void testSetBalance() {
        Card card = new Card("1234567890123456", "123", "owner123", 100);

        card.setBalance(200);
        assertEquals(200, card.getBalance());
    }
}

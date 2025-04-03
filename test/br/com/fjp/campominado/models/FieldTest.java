package br.com.fjp.campominado.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.fjp.campominado.exceptions.ExplosionException;

import org.junit.jupiter.api.BeforeEach;

public class FieldTest {
    private Field field;

    @BeforeEach
    void inicializeFields() {
        field = new Field(3, 3);
    }

    @Test
    void TestNeighborUp() {
        Field neighbor = new Field(2, 3);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborDown() {
        Field neighbor = new Field(4, 3);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborLeft() {
        Field neighbor = new Field(3, 2);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborRight() {
        Field neighbor = new Field(3, 4);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighbordiagonal() {
        Field neighbor = new Field(2, 4);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestWrongNeighbor() {
        Field neighbor = new Field(1, 1);
        boolean result = field.addNeighbor(neighbor);

        assertFalse( result);
    }

    @Test
    void TestDefaultValueMarkup() {
        assertFalse(field.isMarked());
    }

    @Test
    void TestSwitchValueMarkup() {
        field.switchMarkup();
        assertTrue(field.isMarked());
    }

    @Test
    void TestSwitchValueMarkupTwice() {
        field.switchMarkup();
        field.switchMarkup();
        assertFalse(field.isMarked());
    }

    @Test
    void TestOpenFieldUnmarkedNoMined() {
        assertTrue(field.open());
    }

    @Test
    void TestOpenFieldMarkedNoMined() {
        field.switchMarkup();
        assertFalse(field.open());
    }

    @Test
    void TestOpenFieldUnmarkedMined() {
        field.setMine();
        assertThrows(ExplosionException.class, 
            () -> field.open()
        );
    }

    @Test
    void TestOpenFieldMarkedMined() {
        field.switchMarkup();
        field.setMine();
        assertFalse(field.open());
    }

    @Test
    void TestOpenNeighborsNoMined() {
        Field neighbor11 = new Field(1, 1);
        Field neighbor22 = new Field(2, 2);

        neighbor22.addNeighbor(neighbor11);
        field.addNeighbor(neighbor22);

        field.open();

        assertTrue(neighbor11.isOpen() && neighbor22.isOpen());
    }

    @Test
    void TestOpenNeighborsMined() {
        Field neighbor11 = new Field(1, 1);
        Field neighbor22 = new Field(2, 2);

        neighbor11.setMine();
        neighbor22.addNeighbor(neighbor11);
        field.addNeighbor(neighbor22);

        field.open();

        assertTrue(!neighbor11.isOpen() && neighbor22.isOpen());
    }
}

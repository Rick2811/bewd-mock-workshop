package nl.han.se.bewd.mockworkshop.resultaat;

import nl.han.se.bewd.mockworkshop.student.FoutiefStudentException;
import nl.han.se.bewd.mockworkshop.student.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToetsResultaatRegistratieDBTest {

    @Test
    void voegResultaatToeResultaatIsOokWeerOpTeVragen() {
        // Arrange
        Student student1 = new Student();
        ToetsResultaatRegistratieDB sut = new ToetsResultaatRegistratieDB();
        String resultaat = String.valueOf(10);

        // Act
        sut.voegResultaatToe(student1, resultaat);
        List<String> resultaten = sut.vraagResultatenOp(student1);

        // Assert
        assertEquals(1, resultaten.size());
        assertEquals(resultaat, resultaten.get(0));
    }

    @Test
    void verwijderResultaatVanStudentVerwijdertResultaatVanJuisteStudent() {
        // Arrange
        Student student1 = new Student();
        Student student2 = new Student();
        ToetsResultaatRegistratieDB sut = new ToetsResultaatRegistratieDB();
        String resultaat1 = String.valueOf(10);
        String resultaat2 = String.valueOf(5);

        // Act
        sut.voegResultaatToe(student1, resultaat1);
        sut.voegResultaatToe(student2, resultaat2);
        sut.verwijderResultaatVanStudent(student2);
        List<String> resultaten1 = sut.vraagResultatenOp(student1);
        List<String> resultaten2 = sut.vraagResultatenOp(student2);

        // Assert
        assertEquals(resultaat1, resultaten1.get(0));
        assertTrue(resultaten2.isEmpty()); // student2 zou geen resultaten moeten hebben
    }

    @Test
    void verwijderResultaatGooitFoutBijNullStudent() {
        // Arrange
        ToetsResultaatRegistratieDB sut = new ToetsResultaatRegistratieDB();

        // Act and Assert
        assertThrows(FoutiefStudentException.class, () -> sut.verwijderResultaatVanStudent(null));
    }

    @Test
    void vraagResultatenOpGeeftLegeLijstBijGeenResultaten() {
        // Arrange
        Student student1 = new Student();
        ToetsResultaatRegistratieDB sut = new ToetsResultaatRegistratieDB();

        // Act
        List<String> resultaten = sut.vraagResultatenOp(student1);

        // Assert
        assertTrue(resultaten.isEmpty());
    }

    @Test
    void meerdereResultatenVoorZelfdeStudent() {
        // Arrange
        Student student1 = new Student();
        ToetsResultaatRegistratieDB sut = new ToetsResultaatRegistratieDB();
        String resultaat1 = String.valueOf(10);
        String resultaat2 = String.valueOf(5);

        // Act
        sut.voegResultaatToe(student1, resultaat1);
        sut.voegResultaatToe(student1, resultaat2);
        List<String> resultaten = sut.vraagResultatenOp(student1);

        // Assert
        assertEquals(2, resultaten.size());
        assertTrue(resultaten.contains(resultaat1));
        assertTrue(resultaten.contains(resultaat2));
    }
}

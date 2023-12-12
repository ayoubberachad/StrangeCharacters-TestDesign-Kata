package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CharactersTest {

    CharacterFinder characterFinder;

    @BeforeEach
    void setUp() {
        characterFinder = new CharacterFinder(allTestData());
    }

    public List<Character> allTestData() {
        var joyce = new Character("Joyce", "Byers");
        var jim = new Character("Jim", "Hopper");
        var mike = new Character("Mike", "Wheeler");
        var eleven = new Character("Eleven");
        var dustin = new Character("Dustin", "Henderson");
        var lucas = new Character("Lucas", "Sinclair");
        var nancy = new Character("Nancy", "Wheeler");
        var jonathan = new Character("Jonathan", "Byers");
        var will = new Character("Will", "Byers");
        var karen = new Character("Karen", "Wheeler");
        var steve = new Character("Steve", "Harrington");
        var mindflayer = new Character("Mindflayer", true);
        var demagorgon = new Character("Demagorgon", true);
        var demadog = new Character("Demadog", true);

        joyce.addChild(jonathan);
        joyce.addChild(will);
        jim.addChild(eleven);
        karen.addChild(nancy);
        karen.addChild(mike);

        eleven.setNemesis(demagorgon);
        will.setNemesis(mindflayer);
        dustin.setNemesis(demadog);

        return Arrays.asList(
                joyce,
                jim,
                mike,
                will,
                eleven,
                dustin,
                lucas,
                nancy,
                jonathan,
                mindflayer,
                demagorgon,
                demadog,
                karen,
                steve);
    }

    @Test
    void shouldFindCharacterByFirstName() {
        var character = characterFinder.findByFirstName("Will");
        assertEquals("Will", character.firstName);
    } //ok

    @Test
    void shouldFindParentByFirstName() {
        var character = characterFinder.findParent("Eleven");
        assertEquals("Jim", character.firstName);
    }//ok

    // Test Bug Should include the Nemesis
    @Test
    void shouldFindFamilyByFirstName() {
        assertThat(characterFinder.findFamilyByCharacter("Eleven"))
                .containsExactly(new Character("Demagorgon", true), new Character("Jim", "Hopper"));
    } // ok

    @Test
    void shouldFindFamilyWithSiblings() {
        assertThat(characterFinder.findFamilyByCharacter("Jonathan"))
                .containsExactlyInAnyOrder(new Character("Will", "Byers"), new Character("Joyce", "Byers"));
    } // ok

    @Test
    void shouldFindFamilyWithoutMonsters() {
        assertThat(characterFinder.findFamilyByLastName(null)).containsExactlyInAnyOrder(
                new Character("Eleven", "Byers", false));

    } //ok


    @Test
    void shouldFindFamilyWithNemeses() {
        assertThat(characterFinder.findFamilyByLastName("Byers"))
                .containsExactlyInAnyOrder(
                        new Character("Joyce", "Byers"),
                        new Character("Will", "Byers"),
                        new Character("Jonathan", "Byers")
                );
    }
    // add by ayoub
    // Test Bug : Incorrect Monster Filtering
    // The filter condition for monsters in findMonsters method is not accurate.
    @Test
    void shouldFindMonsters() {
        assertThat(characterFinder.findMonsters())
                .containsExactlyInAnyOrder(
                        new Character("Mindflayer", true),
                        new Character("Demagorgon", true),
                        new Character("Demadog", true)
                );
    }
    // add by ayoub
    @Test
    void shouldFindNemesisByFirstName() {
        Character nemesis = characterFinder.findByFirstName("Will").getNemesis();
        assertEquals("Mindflayer", nemesis.firstName);
    }

    // add by ayoub
    // Test Bug : should return Parent Not Monster
    @Test
    void shouldFindParent() {
        Character parent = characterFinder.findParent("Eleven");
        assertEquals(new Character("Jim", "Hopper", false), parent);
    }

}






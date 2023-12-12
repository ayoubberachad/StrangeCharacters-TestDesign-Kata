package org.example;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class CharacterFinder {
    private final List<Character> allCharacters;

    public CharacterFinder(List<Character> allCharacters) {
        this.allCharacters = Collections.unmodifiableList(allCharacters);
    }

    public Character findByFirstName(String name) {
        return allCharacters.stream()
                .filter(c -> c.firstName.equals(name))
                .findFirst()
                .orElse(null);
    }

    //edit by ayoub
    public Character findParent(String firstName) {
        var child = findByFirstName(firstName);
        if (child == null || child.parents.isEmpty()) {
            System.out.println("Child character not found or has no parents.");
            return null;
        }

        return child.parents.stream().findFirst().orElse(null);
    }

    //edit by ayoub
    // Bug Should add c.isMonster because we have a Character without the lastName and is not a Monster
    public List<Character> findMonsters() {
        return allCharacters.stream()
                .filter(c -> c.lastName == null && c.isMonster)
                .collect(toList());
    }

    //edit by ayoub
    public List<Character> findFamilyByCharacter(String firstName) {
        var person = findByFirstName(firstName);
        if (person == null) {
            return Collections.emptyList();
        }

        var family = new HashSet<Character>();
        family.addAll(person.parents);
        family.addAll(person.children);
        family.addAll(person.siblings);
        // Bug Should include the Nemesis
        if (person.getNemesis() != null && !family.contains(person.getNemesis())) {
            family.add(person.getNemesis());
        }
        return new ArrayList<>(family);
    }

    public List<Character> findFamilyByLastName(String lastName) {
        var family = allCharacters.stream().filter(c -> Objects.equals(lastName, c.lastName)).collect(toList());

        if (lastName == null) {
            var familyWithoutMonsters = family.stream().filter(c -> !c.isMonster);
            return familyWithoutMonsters.toList();
        }


        return family;
    }
}
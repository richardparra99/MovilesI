package com.example.practico_1.ui.Repositories

import com.example.practico_1.ui.Models.Person

object PersonRepository {
    val people = arrayListOf(
        Person(1,"Lavadora","modelo LG","10/10"),
        Person(2,"limpieza viernes","obligatorio","cumplido"),
        Person(3,"Lavadora","modelo LG","10/10"),
        Person(4,"limpieza viernes","obligatorio","cumplido"),
        Person(5,"Lavadora","modelo LG","10/10"),
        Person(6,"limpieza viernes","obligatorio","cumplido"),
        Person(7,"Lavadora","modelo LG","10/10"),
        Person(8,"limpieza viernes","obligatorio","cumplido")
    )

    fun getPeaple(): ArrayList<Person>{
        return people.clone() as ArrayList<Person>
    }

    fun savePerson(person: Person) {
        val index = people.indexOfFirst { it.id == person.id }
        if (index != -1) {
            people[index] = person
        } else {
            person.id = (people.maxOfOrNull { it.id } ?: 0) + 1
            people.add(person)
        }
    }

    fun deletePerson(person: Person){
        val toRemove = people.find { it.id == person.id }
        if (toRemove != null) {
            people.remove(toRemove)
        }
    }
}
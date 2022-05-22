package ru.tinkoff.fintech.homework.lesson10.db

import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.lesson10.model.Event
import ru.tinkoff.fintech.homework.lesson10.model.EventStatus

@Profile("mq")
@Repository
interface EventRepository : PagingAndSortingRepository<Event, Int>, CrudRepository<Event, Int> {
    fun findByStatus(status : EventStatus) : List <Event>
}
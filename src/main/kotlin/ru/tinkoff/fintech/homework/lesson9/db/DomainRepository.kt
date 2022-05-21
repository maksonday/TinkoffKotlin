package ru.tinkoff.fintech.homework.lesson9.db

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.lesson9.domain.model.Domain

@Repository
interface DomainRepository : PagingAndSortingRepository<Domain, Int>, CrudRepository<Domain, Int> {
    fun getDomainById(id: Int): Domain
}
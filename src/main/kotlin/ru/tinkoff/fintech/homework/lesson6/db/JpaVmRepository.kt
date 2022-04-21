package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm

@Profile("jpa")
@Repository
interface JpaVmRepository : PagingAndSortingRepository<Kvm, Int>, CrudRepository<Kvm, Int> {
    fun getKvmByOsType(osType: String, pageable: Pageable): Page<Kvm>

    fun getKvmById(id: Int): Kvm
}
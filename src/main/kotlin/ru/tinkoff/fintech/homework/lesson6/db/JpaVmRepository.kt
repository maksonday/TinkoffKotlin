package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.context.annotation.Profile
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm
@Profile("jpa")
@Repository
interface JpaVmRepository : PagingAndSortingRepository<Vm, Long> {
}
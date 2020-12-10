package org.hasenbalg.landon.data.repository;

import org.hasenbalg.landon.data.entity.Guest;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {

}
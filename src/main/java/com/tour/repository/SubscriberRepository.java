package com.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

}

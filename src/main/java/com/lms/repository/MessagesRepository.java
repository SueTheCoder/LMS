package com.lms.repository;

import com.lms.domain.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {


}

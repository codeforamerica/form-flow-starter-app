package org.formflowstartertemplate.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolNameRepository extends JpaRepository<SchoolName, UUID> {

}

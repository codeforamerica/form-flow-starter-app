package org.formflowstartertemplate.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the SubmissionRepository.
 */
@Repository
public interface UbiSubmissionRepository extends JpaRepository<UbiSubmission, Long> {

}

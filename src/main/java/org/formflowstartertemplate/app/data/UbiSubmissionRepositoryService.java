package org.formflowstartertemplate.app.data;

import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service to retrieve and store Submission objects in the database.
 */
@Service
@Transactional
public class UbiSubmissionRepositoryService {

  protected UbiSubmissionRepository repository;

  public UbiSubmissionRepositoryService(UbiSubmissionRepository repository) {
    this.repository = repository;
  }


  // users can add more ways to access the data via this.
  public Optional<UbiSubmission> findById(Long id) {
    return repository.findById(id);
  }

  /**
   * Saves the Submission in the database.
   *
   * @param submission the submission to save, not null
   */
  public void save(UbiSubmission submission) {
    repository.save(submission);
  }


  /**
   * Removes the CSRF from the Submission's input data, if found.
   *
   * @param submission submission to remove the CSRF from, not null
   */
  public void removeFlowCSRF(UbiSubmission submission) {
    submission.getInputData().remove("_csrf");
  }

  /**
   * Removes the CSRF from a particular Submission's Subflow's iteration data, if found.
   * <p>
   * This will remove the CSRF from all the iterations in the subflow.
   * </p>
   *
   * @param submission  submission to look for subflows in, not null
   * @param subflowName the subflow to remove the CSRF from, not null
   */
  public void removeSubflowCSRF(UbiSubmission submission, String subflowName) {
    var subflowArr = (ArrayList<Map<String, Object>>) submission.getInputData().get(subflowName);

    if (subflowArr != null) {
      for (var entry : subflowArr) {
        entry.remove("_csrf");
      }
    }
  }

  /**
   * If the submission exists in the session, find it in the db. If not or can't be found, create a new one.
   *
   * @param httpSession submission
   * @return Submission
   */
  public Submission findOrCreate(HttpSession httpSession) {
    var id = (Long) httpSession.getAttribute("id");
    if (id != null) {
      Optional<UbiSubmission> submissionOptional = findById(id);
      return submissionOptional.orElseGet(UbiSubmission::new);
    } else {
      return new UbiSubmission();
    }
  }
}

package org.formflowstartertemplate.app.data;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SchoolNameRepositoryService {
    public String[] listValidSchoolNames() {
        return new String[]{
                "San Francisco USD"
        };
    }
}

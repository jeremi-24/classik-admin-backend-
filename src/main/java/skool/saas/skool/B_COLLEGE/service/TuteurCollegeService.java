package skool.saas.skool.B_COLLEGE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.B_COLLEGE.Dto.TuteurCollegeSimpleDto;
import skool.saas.skool.B_COLLEGE.Entity.TuteurCollege;
import skool.saas.skool.B_COLLEGE.repository.TuteurCollegeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TuteurCollegeService {


    @Autowired
    private TuteurCollegeRepository tuteurCollegeRepository;


    public List<TuteurCollegeSimpleDto> getAllTuteurs() {
        return tuteurCollegeRepository.findAll().stream()
                .map(t -> new TuteurCollegeSimpleDto(t.getId(), t.getNom(), t.getPrenom()))
                .collect(Collectors.toList());
    }

}

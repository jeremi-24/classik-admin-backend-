package skool.saas.skool.GLOBALE.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.GLOBALE.Entity.Configuration;
import skool.saas.skool.GLOBALE.repository.ConfigurationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public Configuration saveConfiguration(Configuration config) {
        return configurationRepository.save(config);
    }

    @Transactional
    public Configuration updateConfiguration(Long id, Configuration config) {
        Configuration existing = configurationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Configuration non trouvée avec l’ID: " + id));

        existing.setNom(config.getNom());
        existing.setAdresse(config.getAdresse());
        existing.setDevise(config.getDevise());
        existing.setTel(config.getTel());
        existing.setCel(config.getCel());
        existing.setBp(config.getBp());
        existing.setImage(config.getImage());


        return configurationRepository.save(existing);
    }

    // Récupérer toutes les configurations
    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    // Récupérer une configuration par ID
    public Optional<Configuration> getConfigurationById(Long id) {
        return configurationRepository.findById(id);
    }

    // Supprimer une configuration
    public void deleteConfiguration(Long id) {
        configurationRepository.deleteById(id);
    }

    public byte[] getImage() {
        return configurationRepository.findImage();
    }





}

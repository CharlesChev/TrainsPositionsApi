package com.projet.geopulserex.service;

import com.projet.geopulserex.exception.GeomobileException;
import com.projet.geopulserex.repository.ParentRepository;
import com.projet.geopulserex.repository.clouddenis.CloudDenisRepository;
import com.projet.geopulserex.repository.recette.RecetteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    @Autowired(required = true)
    private CloudDenisRepository cloudDenisRepository;

    @Autowired
    private RecetteRepository recetteRepository;

    public ParentRepository getRepo(String env) {
        ParentRepository repo;
        if ("cloudDenis".equals(env)) {
            repo = cloudDenisRepository;
        } else if ("recette".equals(env)) {
            repo = recetteRepository;
        } else {
            throw new GeomobileException(
                    "L'environnement selectionné n'existe pas ou aucun environnement n'a été selectionné");
        }

        return repo;
    }
}

package pl.michal.olszewski.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.entity.EntityWithRevision;
import pl.michal.olszewski.entity.Product;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.repository.RevisionRepository;

import java.util.List;


@RestController
@RequestMapping("/api/v1/")
public class RevisionController {

    @Autowired
    private RevisionRepository repository;

    @RequestMapping("products/history/revisions/{id}")
    public List<EntityWithRevision> getProductRevisions(@PathVariable Long id) {
        return repository.getEntityRevision(id, Product.class);
    }

    @RequestMapping("productDefinitions/history/revisions/{id}")
    public List<EntityWithRevision> getProductDefRevisions(@PathVariable Long id) {
        return repository.getEntityRevision(id, ProductDefinition.class);
    }

}

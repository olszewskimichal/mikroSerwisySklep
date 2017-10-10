package pl.michal.olszewski.repository;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Repository;
import pl.michal.olszewski.entity.EntityWithRevision;
import pl.michal.olszewski.entity.RevisionsEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class RevisionRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public RevisionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<EntityWithRevision> getEntityRevision(Long id, Class aClass) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = auditReader.getRevisions(aClass, id);

        List<EntityWithRevision> entityRevision = new ArrayList<>();
        for (Number revision : revisions) {
            Date revisionDate = auditReader.getRevisionDate(revision);
            entityRevision.add(new EntityWithRevision(new RevisionsEntity(revision.longValue(), revisionDate), auditReader.find(aClass, id, revision)));
        }
        return entityRevision;
    }
}

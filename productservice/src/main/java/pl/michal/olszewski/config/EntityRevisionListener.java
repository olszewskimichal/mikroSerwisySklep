package pl.michal.olszewski.config;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;

@Slf4j
public class EntityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object o) {
        log.debug("New revision is created: {}", o);
    }
}

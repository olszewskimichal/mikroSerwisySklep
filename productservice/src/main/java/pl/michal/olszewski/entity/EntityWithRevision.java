package pl.michal.olszewski.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityWithRevision<T> {
    private final RevisionsEntity revision;
    private final T entity;
}

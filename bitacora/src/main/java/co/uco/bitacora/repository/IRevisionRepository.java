package co.uco.bitacora.repository;

import co.uco.bitacora.domains.bitacora.Revision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRevisionRepository extends JpaRepository<Revision, Long> {
}
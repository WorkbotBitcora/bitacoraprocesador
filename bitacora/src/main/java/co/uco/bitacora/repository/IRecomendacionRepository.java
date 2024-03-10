package co.uco.bitacora.repository;

import co.uco.bitacora.domains.recomendacion.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecomendacionRepository extends JpaRepository<Recomendacion,Long> {
}

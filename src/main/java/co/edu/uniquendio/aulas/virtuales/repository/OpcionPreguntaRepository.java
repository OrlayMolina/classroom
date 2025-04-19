package co.edu.uniquendio.aulas.virtuales.repository;

import co.edu.uniquendio.aulas.virtuales.model.OpcionPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionPreguntaRepository extends JpaRepository<OpcionPregunta, Long> {

    List<OpcionPregunta> findByPreguntaPreguntaId(Long preguntaId);

    void deleteByPreguntaPreguntaId(Long preguntaId);
}
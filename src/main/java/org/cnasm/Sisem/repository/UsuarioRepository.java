package org.cnasm.Sisem.repository;

import org.cnasm.Sisem.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Puedes agregar métodos personalizados aquí si lo necesitas, por ejemplo:
    Usuario findByUsername(String nombre);
}
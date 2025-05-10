package org.cnasm.Sisem.repository;

import org.cnasm.Sisem.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Aquí también puedes agregar métodos personalizados si lo necesitas
    Rol findByNombre(String nombre);
}

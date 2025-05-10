package org.cnasm.Sisem.domain.Registros;

import jakarta.persistence.*;

import java.time.LocalDate;

public class RegistroFormularioIngreso extends Registro {

//region Variables del Formulario Ingreso

    // --- 1. Fechas del formulario ---
    @Column(nullable = false)
    private LocalDate fechaFormulario;

    @Column(nullable = false)
    private LocalDate fechaEntrega;

    // --- 2. Datos Personales ---
    @Column(nullable = false, length = 8)
    private String cedula;

    @Lob
    private byte[] foto; // opcional

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String localidad;

    // --- 3. Contacto ---
    @Column(nullable = false)
    private String telefono;

    private String celular; // opcional

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String referente;

    @Column(nullable = false)
    private String telefonoReferente;

    // --- 4. Diagnóstico ---
    @Column(nullable = false)
    private String diagnosticoCIE11;

    private String diagnosticoCIE11Secundario; // opcional

    // --- 5. Historia Clínica ---
    @Column(nullable = false, columnDefinition = "TEXT")
    private String resumenHistoriaClinica;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String tratamiento;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String recomendaciones;

    // --- 6. Médico y derivante ---
    @Column(nullable = false)
    private String medicoTratante;

    @Column(nullable = false)
    private String nroCJPPU;

    @Column(nullable = false)
    private String telMedico;

    @Column(nullable = false)
    private String emailMedico;

    @Column(nullable = false)
    private String prestadorSalud;

    @Column(nullable = false)
    private String equipoDerivante;

    @Column(nullable = false)
    private String institucionDerivante;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivoDerivacion;

    // --- 7. Información adicional ---
    @Column(nullable = false)
    private Boolean poseeEmergenciaMovil;

    private String cualEmergenciaMovil; // condicional

    @Column(nullable = false)
    private Boolean asisteOtroCentro;

    private String cualCentro; // condicional

    @Column(nullable = false)
    private Boolean poseePensionBPS;

    @Column(nullable = false)
    private Boolean poseeJubilacionIncapacidad;

    @Column(nullable = false)
    private String direccionInstitucion;

    @Column(nullable = false)
    private String telefonoInstitucion;

    @Column(nullable = false)
    private String emailInstitucion;

//endregion



    @Override
    public void procesar() {
        // 1. Buscar si la persona ya existe
        // 2. Si está en “Egreso” o “No Ingreso”: mostrar confirmación
        // 3. Si se confirma: actualizar datos
        // 4. Si no existe: crear persona
        // 5. Crear este registro en el histórico
        // 6. Establecer estado "PreIngreso"
    }

    @Override
    public void validar() {

        validarBase();
        // Validación de campos obligatorios (los no opcionales)
        if (fechaFormulario == null || fechaEntrega == null) {
            throw new ValidacionException("Las fechas del formulario y entrega son obligatorias.");
        }

        if (cedula == null || !cedula.matches("\\d{7,8}")) {
            throw new ValidacionException("Cédula inválida. Debe tener 7 u 8 dígitos sin puntos ni guiones.");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre es obligatorio.");
        }

        if (apellido == null || apellido.isBlank()) {
            throw new ValidacionException("El apellido es obligatorio.");
        }

        if (fechaNacimiento == null) {
            throw new ValidacionException("La fecha de nacimiento es obligatoria.");
        }

        if (direccion == null || direccion.isBlank()) {
            throw new ValidacionException("La dirección es obligatoria.");
        }

        if (localidad == null || localidad.isBlank()) {
            throw new ValidacionException("La localidad es obligatoria.");
        }

        if (telefono == null || !telefono.matches("\\+?\\d{9,15}")) {
            throw new ValidacionException("Teléfono inválido. Debe tener entre 9 y 15 dígitos.");
        }

        if (email != null && !email.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            throw new ValidacionException("El email debe contener '@' y un dominio válido.");
        }

        if (referente == null || referente.isBlank()) {
            throw new ValidacionException("El referente de contacto es obligatorio.");
        }

        if (telefonoReferente == null || !telefonoReferente.matches("\\+?\\d{9,15}")) {
            throw new ValidacionException("Teléfono del referente inválido.");
        }

        if (diagnosticoCIE11 == null || diagnosticoCIE11.isBlank()) {
            throw new ValidacionException("El diagnóstico CIE11 principal es obligatorio.");
        }

        // No se valida diagnóstico secundario si está vacío, es opcional

        if (resumenHistoriaClinica == null || resumenHistoriaClinica.isBlank()) {
            throw new ValidacionException("Debe ingresar un resumen de la historia clínica.");
        }

        if (tratamiento == null || tratamiento.isBlank()) {
            throw new ValidacionException("Debe ingresar tratamiento.");
        }

        if (recomendaciones == null || recomendaciones.isBlank()) {
            throw new ValidacionException("Debe ingresar recomendaciones.");
        }

        if (medicoTratante == null || medicoTratante.isBlank()) {
            throw new ValidacionException("Debe especificar el nombre del médico tratante.");
        }

        if (nroCJPPU == null || !nroCJPPU.matches("\\d+")) {
            throw new ValidacionException("Debe ingresar un número CJPPU válido.");
        }

        if (telMedico == null || !telMedico.matches("\\+?\\d{9,15}")) {
            throw new ValidacionException("Teléfono del médico inválido.");
        }

        if (emailMedico != null && !emailMedico.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            throw new ValidacionException("Email del médico inválido.");
        }

        if (prestadorSalud == null || prestadorSalud.isBlank()) {
            throw new ValidacionException("Prestador de salud obligatorio.");
        }

        if (equipoDerivante == null || equipoDerivante.isBlank()) {
            throw new ValidacionException("Equipo derivante obligatorio.");
        }

        if (institucionDerivante == null || institucionDerivante.isBlank()) {
            throw new ValidacionException("Institución derivante obligatoria.");
        }

        if (motivoDerivacion == null || motivoDerivacion.isBlank()) {
            throw new ValidacionException("Debe especificar los motivos de derivación.");
        }

        // Validaciones condicionales
        if (Boolean.TRUE.equals(poseeEmergenciaMovil) && (cualEmergenciaMovil == null || cualEmergenciaMovil.isBlank())) {
            throw new ValidacionException("Debe especificar cuál emergencia móvil posee.");
        }

        if (Boolean.TRUE.equals(asisteOtroCentro) && (cualCentro == null || cualCentro.isBlank())) {
            throw new ValidacionException("Debe especificar a qué otro centro asiste.");
        }

        if (direccionInstitucion == null || direccionInstitucion.isBlank()) {
            throw new ValidacionException("Dirección de la institución obligatoria.");
        }

        if (telefonoInstitucion == null || !telefonoInstitucion.matches("\\+?\\d{9,15}")) {
            throw new ValidacionException("Teléfono de la institución inválido.");
        }

        if (emailInstitucion != null && !emailInstitucion.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            throw new ValidacionException("Email de la institución inválido.");
        }

        // No se requiere validar foto, celular, ni diagnóstico secundario
    }

}

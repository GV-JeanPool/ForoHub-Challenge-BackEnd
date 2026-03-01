package com.gvjeanpool.forohub.domain.dto;

import com.gvjeanpool.forohub.domain.model.Topico;
import com.gvjeanpool.forohub.domain.model.TopicoStatus;

import java.time.LocalDateTime;

public class TopicoResponseDTO {

    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private TopicoStatus status;
    private String autorNombre;
    private String autorCorreo;
    private String cursoNombre;
    private String cursoCategoria;

    public TopicoResponseDTO() {
    }

    public TopicoResponseDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensaje = topico.getMensaje();
        this.fechaCreacion = topico.getFechaCreacion();
        this.status = topico.getStatus();
        this.autorNombre = topico.getAutor().getNombre();
        this.autorCorreo = topico.getAutor().getCorreoElectronico();
        this.cursoNombre = topico.getCurso().getNombre();
        this.cursoCategoria = topico.getCurso().getCategoria();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public TopicoStatus getStatus() {
        return status;
    }

    public void setStatus(TopicoStatus status) {
        this.status = status;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    public String getAutorCorreo() {
        return autorCorreo;
    }

    public void setAutorCorreo(String autorCorreo) {
        this.autorCorreo = autorCorreo;
    }

    public String getCursoNombre() {
        return cursoNombre;
    }

    public void setCursoNombre(String cursoNombre) {
        this.cursoNombre = cursoNombre;
    }

    public String getCursoCategoria() {
        return cursoCategoria;
    }

    public void setCursoCategoria(String cursoCategoria) {
        this.cursoCategoria = cursoCategoria;
    }
}

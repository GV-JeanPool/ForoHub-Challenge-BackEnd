package com.gvjeanpool.forohub.domain.dto;

import com.gvjeanpool.forohub.domain.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String correoElectronico;
    private List<String> perfiles;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.correoElectronico = usuario.getCorreoElectronico();
        this.perfiles = usuario.getPerfiles().stream()
                .map(p -> p.getNombre())
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public List<String> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<String> perfiles) {
        this.perfiles = perfiles;
    }
}

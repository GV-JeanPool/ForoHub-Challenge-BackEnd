package com.gvjeanpool.forohub.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationDTO {

    @NotBlank(message = "El correo electrónico es obligatorio")
    private String correoElectronico;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    public AuthenticationDTO() {
    }

    public AuthenticationDTO(String correoElectronico, String contrasena) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

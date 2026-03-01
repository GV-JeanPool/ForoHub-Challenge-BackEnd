package com.gvjeanpool.forohub.domain.exception;

public class DuplicateTopicException extends RuntimeException {
    public DuplicateTopicException(String titulo, String mensaje) {
        super("Ya existe un tópico con el título: " + titulo + " y mensaje: " + mensaje);
    }
    
    public DuplicateTopicException(String message) {
        super(message);
    }
}

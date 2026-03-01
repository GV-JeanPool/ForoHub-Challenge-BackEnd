package com.gvjeanpool.forohub.domain.exception;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(Long id) {
        super("Tópico no encontrado con ID: " + id);
    }
}

package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

    public AcessoResponse login(AcessoRequest request) {
        if ("admin".equals(request.login()) && "password".equals(request.senha())) {
            String token = "dummy-jwt-token-for-" + request.login();
            return new AcessoResponse(token);
        } else {
            throw new RuntimeException("Credenciais inválidas");
        }
    }
}

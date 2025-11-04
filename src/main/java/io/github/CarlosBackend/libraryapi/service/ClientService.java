package io.github.CarlosBackend.libraryapi.service;

import io.github.CarlosBackend.libraryapi.model.Client;
import io.github.CarlosBackend.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return clientRepository.save(client);
    }

    public Client obterClientPorId(String clientId){
        return clientRepository.findByClientId(clientId);
    }
}

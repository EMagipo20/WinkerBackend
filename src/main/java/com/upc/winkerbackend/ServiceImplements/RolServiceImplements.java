package com.upc.winkerbackend.ServiceImplements;

import com.upc.winkerbackend.Entities.Rol;
import com.upc.winkerbackend.Repositories.RolRepository;
import com.upc.winkerbackend.ServiceInterface.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImplements implements RolService {
    @Autowired
    private RolRepository rR;

    @Override
    public Rol insert(Rol rol) {
        return rR.save(rol);
    }
}

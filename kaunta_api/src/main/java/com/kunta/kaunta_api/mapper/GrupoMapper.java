package com.kunta.kaunta_api.mapper;

import com.kunta.kaunta_api.dto.GrupoCreateDTO;
import com.kunta.kaunta_api.model.Grupo;
import com.kunta.kaunta_api.reporitory.UserRepository;

public class GrupoMapper {
    
    private static GrupoMapper instance = null;
    private GrupoMapper(){}

    public static GrupoMapper getInstance(){
        if(instance == null){
            instance = new GrupoMapper();
        }
        return instance;
    }

    public Grupo grupoFromDTO(GrupoCreateDTO dto){
        Grupo ret = new Grupo();
        ret.setNombre(dto.getNombre());
        ret.setActivo(true);
        return ret;

    }
}

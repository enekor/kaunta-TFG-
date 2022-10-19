package com.kunta.kaunta_api.mapper;

import com.kunta.kaunta_api.dto.ContadorCreateDTO;
import com.kunta.kaunta_api.model.Contador;

public class ContadorMapper {
    
    private static ContadorMapper instance = null;
    private ContadorMapper(){}

    public static ContadorMapper getInstance(){
        if(instance == null){
            instance = new ContadorMapper();
        }
        return instance;
    }

    public Contador contadorFromDTO(ContadorCreateDTO dto){
        
        Contador ret = new Contador();
        ret.setActive(true);
        ret.setCount(dto.getCount());
        ret.setDescrition(dto.getDescription());
        ret.setName(dto.getName());

        return ret;
    }
}

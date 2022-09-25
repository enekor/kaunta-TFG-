package com.eneko.kauntaApi.mapper;

import com.eneko.kauntaApi.dto.CounterDTO;
import com.eneko.kauntaApi.dto.GroupDTO;
import com.eneko.kauntaApi.model.Counter;
import com.eneko.kauntaApi.model.Group;
import com.eneko.kauntaApi.repository.CounterRepository;
import com.eneko.kauntaApi.repository.GroupRepository;
import com.eneko.kauntaApi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private static Mapper mapper = null;

    private Mapper(){};

    public static Mapper getInstance(){
        if(mapper==null){
            mapper = new Mapper();
        }
        return mapper;
    }

    public Group groupDtoToGroup(GroupDTO dto, UserRepository userRepo, CounterRepository counterRepo){

        Group ret = new Group();

        ret.setActive(true);
        if(dto.getCounters().isEmpty()) {
            ret.setCounters(new ArrayList<>());
        }else{
            List<Counter> counters = new ArrayList<>();
            dto.getCounters().forEach(c -> {
                if(counterRepo.existsById(c)){
                    counters.add(counterRepo.findById(c).get());
                }
            });
            ret.setCounters(counters);
        }
        ret.setUser(userRepo.findById(dto.getUser()).get());
        ret.setName(dto.getName());

        return ret;
    }

    public Counter counterDtoToCounter(CounterDTO dto,GroupRepository groupRepo){
        Counter ret = new Counter();

        ret.setName(dto.getName());
        ret.setDescription(dto.getDescription());
        ret.setActive(true);
        ret.setGroup(groupRepo.findById(dto.getGroup()).get());
        if(!dto.getImg().equals("")){
            ret.setImg(dto.getImg());
        }
        ret.setCount(dto.getCount());

        return ret;
    }
}

package com.manitas.domain.service.impl;

import com.manitas.domain.data.entity.RoleEntity;
import com.manitas.domain.data.entity.TopicEntity;
import com.manitas.domain.data.entity.UserStatusEntity;
import com.manitas.domain.data.repository.RoleRepository;
import com.manitas.domain.data.repository.TopicRepository;
import com.manitas.domain.data.repository.UserStatusRepository;
import com.manitas.domain.service.CatalogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public RoleEntity getRoleById(Integer idRole){
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(idRole);
        return optionalRoleEntity.orElse(null);
    }

    @Override
    public UserStatusEntity getUserStatusById(Integer idUserStatus){
        Optional<UserStatusEntity> optionalUserStatusEntity = userStatusRepository.findById(idUserStatus);
        return optionalUserStatusEntity.orElse(null);
    }

    @Override
    public TopicEntity getTopicById(Integer idTopic){
        Optional<TopicEntity> optionalTopicEntity = topicRepository.findById(idTopic);
        return optionalTopicEntity.orElse(null);
    }

}

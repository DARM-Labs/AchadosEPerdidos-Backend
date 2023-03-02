package com.example.course.services;

import com.example.course.entities.LostObject;
import com.example.course.repositories.LostObjectsRepository;
import com.example.course.resources.dtos.LostObjectRequestDTO;
import com.example.course.resources.dtos.UpdateDeliveredRequestDTO;
import com.example.course.services.exceptions.DatabaseException;
import com.example.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LostObjectsService {

    @Autowired
    private LostObjectsRepository repository;

    public Page<LostObject> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public LostObject findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id:" + id));
    }

    @Transactional
    public LostObject insert(LostObject obj) {
        return repository.save(obj);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No lost object with this id: " + id);
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public LostObject updateDeliveredOwner(Long idLostObject, UpdateDeliveredRequestDTO statusDeliveredOwner) {

        LostObject entity = repository.findById(idLostObject)
                .orElseThrow(() -> new ResourceNotFoundException("No lost object with this idLostObject: " + idLostObject));

        updateDeliveredOwner(entity, statusDeliveredOwner);
        return repository.save(entity);
    }

    public LostObject updateDeliveredReception(Long idLostObject, UpdateDeliveredRequestDTO statusDeliveredReception) {

        LostObject entity = repository.findById(idLostObject)
                .orElseThrow(() -> new ResourceNotFoundException("No lost object with this id: " + idLostObject));

        updateDeliveredReception(entity, statusDeliveredReception);
        return repository.save(entity);
    }

    @Transactional
    public LostObject update(Long id, LostObjectRequestDTO lostObjectToUpdate) {

        LostObject entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No lost object with this id: " + id));
        update(entity, lostObjectToUpdate);

        return repository.save(entity);
    }

    private void update(LostObject entity, LostObjectRequestDTO obj) {
        entity.setName(obj.getName());
        entity.setDeliveredOwner(obj.isDeliveredOwner());
        entity.setPlaceIfce(obj.getPlaceIfce());
        entity.setDeliverReception(obj.isDeliverReception());
    }

    private void updateDeliveredOwner(LostObject entity, UpdateDeliveredRequestDTO obj) {
        entity.setDeliveredOwner(obj.isDelivered());
    }

    private void updateDeliveredReception(LostObject entity, UpdateDeliveredRequestDTO obj) {
        entity.setDeliverReception(obj.isDelivered());
    }
}

package com.example.course.services;

import com.example.course.entities.FoundObject;
import com.example.course.repositories.FoundObjectsRepository;
import com.example.course.resources.dtos.FoundObjectRequestDTO;
import com.example.course.services.exceptions.DatabaseException;
import com.example.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class FoundObjectsService {

    @Autowired
    private FoundObjectsRepository repository;

    public Page<FoundObject> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public FoundObject findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id:" + id));
    }

    @Transactional
    public FoundObject insert(FoundObject obj) {
        return repository.save(obj);
    }

    @Transactional
    public void delete(Long id) {

        if (!repository.existsById(id))
            throw new ResourceNotFoundException("Object not found with id:" + id);

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public FoundObject updateDeliveredOwner(Long id, FoundObject obj) {

        FoundObject entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id:" + id));

        updateDeliveredOwner(entity, obj);
        return repository.save(entity);

    }

    public FoundObject updateDeliveredReception(Long id, FoundObject obj) {

        FoundObject entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id:" + id));

        updateDeliveredReception(entity, obj);
        return repository.save(entity);

    }

    @Transactional
    public FoundObject update(Long id, FoundObjectRequestDTO obj) {

            FoundObject entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Object not found with id:" + id));
            update(entity, obj);
            return repository.save(entity);
    }

    private void update(FoundObject entity, FoundObjectRequestDTO obj) {
        entity.setName(obj.getName());
        entity.setDeliveredOwner(obj.isDeliveredOwner());
        entity.setPlaceIfce(obj.getPlaceIfce());
        entity.setUrlImage(obj.getUrlImage());
        entity.setDeliverReception(obj.isDeliverReception());
    }

    private void updateDeliveredOwner(FoundObject entity, FoundObject obj) {
        entity.setDeliveredOwner(obj.isDeliveredOwner());
    }

    private void updateDeliveredReception(FoundObject entity, FoundObject obj) {
        entity.setDeliverReception(obj.isDeliverReception());
    }
}

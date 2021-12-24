package com.example.api;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.transaction.*;
import com.example.entity.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

    @Inject
    EntityManager em;

    @GET
    public List<User> readAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root root = criteriaQuery.from(User.class);

        criteriaQuery.select(root);

        Query query = em.createQuery(criteriaQuery);

        return query.getResultList();

    }

    @POST
    @Transactional(Transactional.TxType.REQUIRED)
    public User post(User user) throws Exception {
        if (user.getId() == null)
            throw new Exception("Id can not be null");
        em.persist(user);
        return user;
    }

}

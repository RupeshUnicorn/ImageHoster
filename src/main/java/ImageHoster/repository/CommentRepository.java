package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentRepository {

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Comment addComment(Comment newComment) {

        //The method receives the comment object to be persisted in the database
        //Creates an instance of EntityManager
        //Starts a transaction
        //The transaction is committed if it is successful
        //The transaction is rolled back in case of unsuccessful transaction
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return newComment;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch all the comment of an image from the database
    //Returns the list of all the comment fetched from the database
    public List<Comment> getAllComments(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Comment> typedQuery = em.createQuery("SELECT c from Comment c where c.image.id =:imageId", Comment.class).setParameter("imageId", imageId);
            List<Comment> resultList = typedQuery.getResultList();
            return resultList;
        } catch (NoResultException nre) {
            return null;
        }
    }
}

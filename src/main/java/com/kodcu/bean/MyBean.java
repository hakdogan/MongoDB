/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import com.kodcu.entity.Article;
import com.kodcu.entity.Categories;
import com.kodcu.entity.Tags;
import com.kodcu.service.PersistenceManager;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author hakdogan
 */

@ManagedBean
@ViewScoped
public class MyBean {
    
    private static EntityManager em;
    private String categories;
    private String tags;
    
    private Article selectArticle;
    private Article article                 = new Article();
    private List<Article> articleList       = new ArrayList<Article>();
    
    public MyBean() {
        
        em = PersistenceManager.instance().getFactory().createEntityManager();
        articleList = em.createQuery("select a from Article a order by a.id desc").getResultList();
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Article getSelectArticle() {
        return selectArticle;
    }

    public void setSelectArticle(Article selectArticle) {
        this.selectArticle = selectArticle;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
    
    public void articleSelect(){
        
        categories = "";
        if(!selectArticle.getCategoryLists().isEmpty()){
            for(int i = 0; i < selectArticle.getCategoryLists().size(); i++){
                categories += selectArticle.getCategoryLists().get(i).getCategory() + ", ";
            }
            categories = categories.substring(0, (categories.length() -2));
        }
        
        tags = "";
        if(!selectArticle.getTagLists().isEmpty()){
            for(int i = 0; i < selectArticle.getTagLists().size(); i++){
                tags += selectArticle.getTagLists().get(i).getTag() + ", ";
            }
            tags = tags.substring(0, (tags.length() -2));
        }
            
        article = selectArticle;
    }
    
    public void saveArticle(){
        
        try {
            em.getTransaction().begin();

            article.getCategoryLists().clear();
            article.getTagLists().clear();

            String[] arrayCategory = categories.replace(" ", "").split(",");
            for(int i = 0; i < arrayCategory.length; i++){
                article.getCategoryLists().add(new Categories(arrayCategory[i]));
            }

            String[] arrayTags = tags.replace(" ", "").split(",");
            for(int i = 0; i < arrayTags.length; i++){
                article.getTagLists().add(new Tags(arrayTags[i]));
            }

            article.setDate(new Date());


            if(null == article.getId())
                em.persist(article);
            else
                em.merge(article);

            em.getTransaction().commit();
            int g = 2 / 0;
            initArticle();
        } catch (Exception ex){
            System.out.println("Exception: " + ex.toString());
            em.getTransaction().rollback();
        }
    }
    
    public void removeArticle(){
        
        em.getTransaction().begin();
        em.remove(selectArticle);
        em.getTransaction().commit();
        
        initArticle();
        
    }
    
    public void initArticle(){
        
        categories    = "";
        tags          = "";
        articleList   = em.createQuery("select a from Article a order by a.id desc").getResultList();
        article       = new Article();
        selectArticle = null;
    }
}
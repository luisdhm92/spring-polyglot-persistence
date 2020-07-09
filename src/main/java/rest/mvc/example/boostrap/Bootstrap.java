package rest.mvc.example.boostrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.domain.Article;
import rest.mvc.example.domain.Topic;
import rest.mvc.example.repository.AppUserRepository;
import rest.mvc.example.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    private ArticleRepository articleRepository;
    private AppUserRepository appUserRepository;

    public Bootstrap(ArticleRepository articleRepository, AppUserRepository appUserRepository) {
        this.articleRepository = articleRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSampleData();
    }

    private void loadSampleData() {

        AppUser user = getAppUser();
        Article article1 = getArticle();

        appUserRepository.save(user);
        articleRepository.save(article1);

    }

    private AppUser getAppUser() {
        AppUser user1 = new AppUser();
        user1.setFirstName("John");
        user1.setLastName("Smith");
        user1.setPassword("123456");

        return user1;
    }

    private Article getArticle() {
        Article firstArticle = new Article();
        firstArticle.setId(1l);
        firstArticle.setRating(8.4d);
        firstArticle.setName("RabbitMQ in Action");

        AppUser author = new AppUser();
        author.setFirstName("Alvaro");
        author.setLastName("Videla");
        firstArticle.setAuthor(author);

        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic("RabbitMQ"));
        topics.add(new Topic("Distributed Apps"));


        firstArticle.setTopics(topics);

        return firstArticle;
    }

}

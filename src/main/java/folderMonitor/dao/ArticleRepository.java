package folderMonitor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import folderMonitor.domain.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {

	Article findByCode(String code);

}

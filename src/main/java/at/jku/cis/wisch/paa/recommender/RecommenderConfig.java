package at.jku.cis.wisch.paa.recommender;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;

public class RecommenderConfig {
	
	private static final Logger logger = LogManager.getLogger(RecommenderConfig.class);
	
	private static LenskitConfiguration config = null;
	
	
	public static void init(){
		logger.info("Initializing Lenskit recommendation subsystem...");
		
		config = new LenskitConfiguration();
		// Use item-item CF to score items
		config.bind(ItemScorer.class)
		      .to(ItemItemScorer.class);
		// let's use personalized mean rating as the baseline/fallback predictor.
		// 2-step process:
		// First, use the user mean rating as the baseline scorer
		config.bind(BaselineScorer.class, ItemScorer.class)
		      .to(UserMeanItemScorer.class);
		// Second, use the item mean rating as the base for user means
		config.bind(UserMeanBaseline.class, ItemScorer.class)
		      .to(ItemMeanRatingItemScorer.class);
		// and normalize ratings by baseline prior to computing similarities
		config.bind(UserVectorNormalizer.class)
		      .to(BaselineSubtractingUserVectorNormalizer.class);
		
		
	}
	
	public static LenskitConfiguration getLenskitConfig() {
		
		if (config == null) {
			logger.info("Creating and configuring new LenskitConfiguration...");
			init();
		}
		return config;
		
	}
	
	
	

}

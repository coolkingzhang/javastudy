package org.javastudy.automation.elastic;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;

public class MyElasticJob extends AbstractSimpleElasticJob {

	@Override
	public void process(JobExecutionMultipleShardingContext context) {
		// do something by sharding items
		System.out.println("#######################################################");
		System.out.println("elasticJob startting");
		System.out.println("#######################################################");
	}
	
}

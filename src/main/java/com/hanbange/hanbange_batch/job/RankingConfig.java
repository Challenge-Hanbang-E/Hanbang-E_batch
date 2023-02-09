package com.hanbange.hanbange_batch.job;

import com.hanbange.hanbange_batch.domain.orders.repository.OrdersRepository;
import com.hanbange.hanbange_batch.domain.ranking.dto.RankingDto;
import com.hanbange.hanbange_batch.domain.ranking.entity.Ranking;

import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;


@Configuration
@RequiredArgsConstructor
public class RankingConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final OrdersRepository ordersRepository;

    @Bean
    public Job rankingUpdateJob(Step rankingUpdateStep) {
        return jobBuilderFactory.get("rankingUpdateJob")
                .incrementer(new RunIdIncrementer())
                .start(rankingUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step rankingUpdateStep() {
        return stepBuilderFactory.get("rankingUpdateStep")
                .<RankingDto, Ranking>chunk(100)
                .reader(rankingUpdateReader())
                .processor(rankingUpdateProcess())
                .writer(rankingUpdateWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<Ranking> rankingUpdateWriter() {
        JpaItemWriter<Ranking> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(false);
        return writer;
    }

    @Bean
    @StepScope
    public ItemProcessor<RankingDto, Ranking> rankingUpdateProcess() {
        return new ItemProcessor<RankingDto, Ranking>() {
            @Override
            public Ranking process(RankingDto item) throws Exception {
                return new Ranking(item);
            }
        };
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<RankingDto> rankingUpdateReader() {

        JpaPagingItemReader<RankingDto> reader = new JpaPagingItemReader<RankingDto>() {
            @Override
            public int getPage() {
                return 0;
            }
        };
        reader.setQueryString("SELECT new com.hanbange.hanbange_batch.domain.ranking.dto.RankingDto(o.product, SUM(o.quantity)) FROM Orders o GROUP BY o.product ORDER BY o.quantity DESC");
        reader.setPageSize(100);
        reader.setMaxItemCount(10);
        reader.setSaveState(false);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("reader");
        return reader;
    }
}
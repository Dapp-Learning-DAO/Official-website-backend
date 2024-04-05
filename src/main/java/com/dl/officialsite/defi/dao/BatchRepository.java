package com.dl.officialsite.defi.dao;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName BatchRepository
 * @Author jackchen
 * @Date 2024/4/4 15:35
 * @Description BatchRepository
 **/
@Slf4j
@Repository
public class BatchRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Spring Data JPA调用的是Hibernate底层的实现。每次批量保存时，攒够 batchSize 条记录再集中em.flush()，
     *
     * @see org.hibernate.cfg.BatchSettings#STATEMENT_BATCH_SIZE
     */
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private Integer batchSize;

    /**
     * @see org.hibernate.cfg.BatchSettings#BATCH_VERSIONED_DATA
     */
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_versioned_data}")
    private String batchVersionedData;

    /**
     * @see org.hibernate.cfg.BatchSettings#ORDER_INSERTS
     */
    @Value("${spring.jpa.properties.hibernate.order_inserts}")
    private String orderInserts;

    /**
     * @see org.hibernate.cfg.BatchSettings#ORDER_UPDATES
     */
    @Value("${spring.jpa.properties.hibernate.order_updates}")
    private String orderUpdates;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PostConstruct
    public void init() {
        log.info("BaseDao初始化加载。batchSize：{}，batchVersionedData：{}，orderInserts：{}，orderUpdates：{}",
            batchSize, batchVersionedData, orderInserts, orderUpdates);
    }

    /**
     * 批量 insert，实现了性能呈倍提升。注意： <br>
     * 1. 需要配置 {@link #batchSize}，且jdbc.url开启rewriteBatchedStatements为true <br>
     * 2. 关于rewriteBatchedStatements，可参考MySQL官网解释：{@linkplain 'https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-connp-props-performance-extensions.html#cj-conn-prop_rewriteBatchedStatements'}
     * 3. 主键不能用生成策略，否则会报：{@link org.springframework.dao.InvalidDataAccessApiUsageException}: detached entity passed to persist
     *
     * @param entities 实体对象列表
     * @param <T>      实体类型（必须有@Entity注解）
     * @see #batchSize
     */
    @Transactional(rollbackFor = Exception.class)
    public <T> void batchInsert(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        for (T entity : entities) {
            entityManager.persist(entity);
        }
    }

    /**
     * 批量update ( 通过Hibernate进行实现 )
     *
     * @param entities 实体对象列表
     * @param <T>      实体类型（必须有@Entity注解）
     * @see #batchSize
     */
    @Transactional(rollbackFor = Exception.class)
    public <T> void batchUpdate(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        Session session = this.entityManager.unwrap(Session.class);
        session.setJdbcBatchSize(batchSize);
        for (T t : entities) {
            session.update(t);
        }
    }
}

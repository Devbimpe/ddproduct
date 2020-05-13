package com.lbt.icon.demanddraft.domain.util;

import com.lbt.icon.core.util.DatasourceUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * @author devbimpe
 */
@Service
@Getter
public class DDEntityManagerUtil {

    private EntityManager entityManager;

    public DDEntityManagerUtil(DatasourceUtil datasourceUtil) {
        this.entityManager = datasourceUtil.getEntityManager(DatasourceUtil.EM_BEAN_NAME_DEFAULT);

    }




}

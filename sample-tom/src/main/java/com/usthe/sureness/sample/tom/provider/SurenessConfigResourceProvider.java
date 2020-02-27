package com.usthe.sureness.sample.tom.provider;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.sample.tom.dao.AuthResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * 从数据库加载资源配置信息提供者
 * @author tomsun28
 * @date 16:00 2019-08-04
 */
@Component
public class SurenessConfigResourceProvider implements PathTreeProvider, SurenessAccountProvider {

    @Autowired
    private AuthResourceDao authResourceDao;

    @Override
    public Set<String> providePathData() throws SurenessLoadDataException {

//        Optional<Set<String>> pathOptional = authResourceDao.getPathRoleData();
//        if (pathOptional.isPresent()) {
//            return pathOptional.get();
//        } else {
//            throw new SurenessLoadDataException("can load pathRoleData from database");
//        }
        return null;
    }

    @Override
    public SurenessAccount loadAccount(String appId) {
        return null;
    }
}

package indi.nonoas.crm.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Nonoas
 * @time : 2020-09-02 15:43
 */
@Transactional
public interface OrderService {

    /**
     * 删除一年前的记录
     */
    void delete365dAgo();
}

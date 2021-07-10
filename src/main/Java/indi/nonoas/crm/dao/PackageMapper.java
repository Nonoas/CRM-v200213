package indi.nonoas.crm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.nonoas.crm.pojo.PackageDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:16
 */
@Repository
public interface PackageMapper extends BaseMapper<PackageDto> {

    /**
     * ͨ�����˲�����Ŀ��Ϣ
     *
     * @param id     ���
     * @param name   ����
     * @param money1 ��Ǯ����
     * @param money2 ��Ǯ����
     * @return 进价进价���Ŀ��Ϣ, ����Ϊnull
     */
    List<PackageDto> findByFilter(@Param("id") String id,
                                  @Param("name") String name,
                                  @Param("money1") double money1,
                                  @Param("money2") double money2);

}

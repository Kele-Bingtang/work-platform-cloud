package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.ag.system.model.vo.DictDataVO;
import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Kele-Bingtang
 * @date 2023-12-12 00:21:40
 * @note 字典数据
*/
@TableName("t_dict_data")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = DictDataVO.class, reverseConvertGenerate = false)
public class DictData extends BaseDO {
    /**
     * 字典数据 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String dataId;

    /**
     * 父级字典数据 ID
     */
    private String parentId;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * tag 标签：el-tag | el-check-tag
     */
    private String tagEl;

    /**
     * tag 类型：primary | success | info | warning | danger
     */
    private String tagType;

    /**
     * tag 主题：dark | light | plain
     */
    private String tagEffect;

    /**
     * tag 其他属性
     */
    private String tagAttributes;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

    /**
     * 字典编码
     */
    private String dictCode;

}
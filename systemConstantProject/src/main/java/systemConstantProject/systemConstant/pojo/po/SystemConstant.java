package systemConstantProject.systemConstant.pojo.po;

import java.time.LocalDateTime;

public class SystemConstant {
    private Integer id;

    private String constantName;

    private String constantValue;

    private LocalDateTime createtime;

    private Boolean isdelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConstantName() {
        return constantName;
    }

    public void setConstantName(String constantname) {
        this.constantName = constantname == null ? null : constantname.trim();
    }

    public String getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(String constantvalue) {
        this.constantValue = constantvalue == null ? null : constantvalue.trim();
    }

    public LocalDateTime getCreateTime() {
        return createtime;
    }

    public void setCreateTime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public Boolean getIsDelete() {
        return isdelete;
    }

    public void setIsDelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }
}